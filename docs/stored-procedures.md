# Stored procedures and functions (generated `DataManager.Procedure`)

This document describes how **SQL routines** (JDBC `DatabaseMetaData` procedures and functions) become **`DataManager.call()`** methods, how **PostgreSQL** differs between **`CREATE PROCEDURE`** and **`CREATE FUNCTION`**, and where **tests** and **seed SQL** live.

For the example module that runs these tests, see [Datastore module](datastore.md). For template placement, see [Compiler module](compiler.md). For metadata collection, see [Core module](core.md).

---

## What is covered vs not covered

This section tracks **behaviour that exists in source today** (core + compiler + seed SQL + datastore tests) versus **gaps** that are not implemented, not in seed SQL, or not exercised.

### Covered in source (today)

| Area | What exists |
|------|----------------|
| **Metadata** | `getProcedures` / `getFunctions` merged into one list; [`Procedure.catalogProcedure`](../core/src/main/java/org/sqlcomponents/core/model/relational/Procedure.java) tells PostgreSQL **`CALL`** apart from function-style calls. |
| **JDBC layout** | `Procedures.ftl` builds placeholders from **`ordinalPosition`** (≥ 1), so **IN**, **OUT**, **INOUT** on the same index, and **multiple OUT** align with `CallableStatement` indices. |
| **Resource handling** | Generated callables use **`try (Connection; CallableStatement)`** so connections return to the pool (see template in [Procedures.ftl](../compiler/src/main/resources/template/java/Procedures.ftl)). |
| **PostgreSQL syntax** | **`CALL`** for `CREATE PROCEDURE`; **`{? = call …}`** for scalar **`CREATE FUNCTION`** return (ordinal **0**); IN-only procedures use **`SqlBuilder.prepareCall`** with `CALL` / `call` as appropriate. |
| **Seed + examples** | [`init.db/postgres/procedures.sql`](../init.db/postgres/procedures.sql): `transfer`, `create_cache`, `add`, `sp_echo_len`, `sp_divmod`, `sp_sum_product`, `sp_double_inout`, `sp_fixed_pair`, `fn_sum_three`. |
| **Integration tests** | [`StoredProcedureTest`](../datastore/src/test/java/org/example/storedprocedure/StoredProcedureTest.java): IN-only (`create_cache` variants), scalar functions (`add`, `fn_sum_three`), IN + single OUT, IN + multi OUT, OUT-only, INOUT-only, `transfer` side effects; shared `DataSource`, scoped `@BeforeEach`, `@Execution(SAME_THREAD)` to reduce pool pressure. |

### Not covered (or only partially) in source

| Gap | Notes |
|-----|--------|
| **VARIADIC / SQL `ARRAY` parameters** | PostgreSQL often exposes **one array-typed** parameter; **`JavaMapper`** / procedure templates do not define a first-class story for **`ARRAY` / composite** routine parameters in this repo. No VARIADIC routine in [`procedures.sql`](../init.db/postgres/procedures.sql). |
| **`RETURNS TABLE` / set-returning functions** | No crawler + template path here for **result-set-shaped** function returns; only **scalar** return (`{? = call …}`) and **OUT/INOUT** via `CallableStatement`. |
| **`REF CURSOR` / OUT refcursor** | Not modelled in `Procedures.ftl`; no seed routines. |
| **INOUT + extra OUT in one routine** | JDBC metadata lists INOUT on both input and output sides; the **multi-OUT Java API** uses **arrays per OUT name**. A routine that mixes **INOUT** with **another OUT** can produce **duplicate Java parameter names** or awkward signatures—**not** in current seed SQL by design. |
| **Non-PostgreSQL procedure calling** | **`CALL`** vs **`{call …}`** branching is tied to **`DBType.POSTGRES`** and **`catalogProcedure`**. Other engines may need their own rules (e.g. SQL dialects, `EXEC`, packages). |
| **Qualified names in generated SQL** | Emitted SQL uses **`${method.functionName}`** (typically **unqualified**). Routines outside the default **`search_path`** are not handled explicitly. |
| **Overloaded routine names** | One `Method` per `Procedure` name from the crawler list; **same SQL name, different arity** is not addressed as a separate feature. |
| **Named JDBC parameters** | Only **positional** `?` / `registerOutParameter` generation; no `SqlBuilder` / `CallableStatement` named-parameter API. |
| **Rich OUT types** | `callableOutScalarExpression` in [`base.ftl`](../compiler/src/main/resources/template/java/base.ftl) covers common scalars; **exotic or driver-specific OUT types** may fall through to **`getObject`** or need template work. |
| **Procedure `COMMIT` / autonomous transactions** | Not part of codegen; behaviour depends on the SQL body. `transfer` in seed SQL avoids an internal **`COMMIT`**. |

---

## Catalog sources (`core`)

[`Crawler.getProcedures`](../core/src/main/java/org/sqlcomponents/core/crawler/Crawler.java) merges two JDBC catalogs into one list on `Database.setFunctions` (historical name; entries are not all “functions”):

| JDBC API | SQL objects | `Procedure` flag |
|----------|-------------|-------------------|
| [`DatabaseMetaData.getProcedures`](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/DatabaseMetaData.html#getProcedures(java.lang.String,java.lang.String,java.lang.String)) | e.g. PostgreSQL **`CREATE PROCEDURE`** | **`catalogProcedure == true`** |
| [`DatabaseMetaData.getFunctions`](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/DatabaseMetaData.html#getFunctions(java.lang.String,java.lang.String,java.lang.String)) | e.g. PostgreSQL **`CREATE FUNCTION`** | **`catalogProcedure == false`** |

The relational type [`Procedure`](../core/src/main/java/org/sqlcomponents/core/model/relational/Procedure.java) carries:

- `functionName`, schema, remarks, JDBC `functionType`, parameters (`Column` lists for IN/OUT, including INOUT duplicated across input and output lists as returned by the driver).
- **`boolean catalogProcedure`** — distinguishes procedure-catalog rows from function-catalog rows so the **compiler** can emit correct **call syntax** on PostgreSQL (see below).

Parameter **ordinal positions** from metadata (`Column.ordinalPosition`) drive JDBC **`?`** indices in generated `CallableStatement` code (one placeholder per ordinal ≥ 1; INOUT uses the same index for `set*` and `registerOutParameter`).

---

## Code generation (`compiler`)

Routines become `public static final class DataManager.Procedure` methods via [`Procedures.ftl`](../compiler/src/main/resources/template/java/Procedures.ftl), included from [`Manager.ftl`](../compiler/src/main/resources/template/java/Manager.ftl).

### PostgreSQL: `CALL` vs `{call …}`

- PostgreSQL **`CREATE FUNCTION`** with a scalar return is invoked with JDBC **`{? = call name(?, …)}`** when metadata exposes a **single return column at ordinal 0** (see `usePgFunctionReturnSyntax` in the template).
- Routines that are **`CREATE PROCEDURE`** must be executed with SQL **`CALL name(?, …)`**. The legacy JDBC escape **`{call name(?, …)}`** is interpreted like a **function** call; the server rejects it with *“is a procedure … use CALL”*.

When **`orm.database.dbType == 'POSTGRES'`** and **`method.function.catalogProcedure`** is true, the template emits **`prepareCall("CALL …")`** instead of **`prepareCall("{call …}")`**. The IN-only path (no OUT parameters) uses the same distinction for the `SqlBuilder.prepareCall(...)` SQL string (`CALL` vs `call`).

Callable paths use **`try (Connection c = …; CallableStatement cs = c.prepareCall(…))`** so both resources close; chaining **`try (CallableStatement cs = ds.getConnection().prepareCall(…))`** would only close the statement and **leak** the connection back to the pool.

Other databases keep the existing **`{call …}`** behavior unless extended similarly.

### Shapes of generated Java API

| Metadata shape | Generated pattern |
|----------------|-------------------|
| IN only, no OUT | `void name(DataSource, …)` using `SqlBuilder.prepareCall`. |
| Single scalar **function** return (ordinal 0) + IN list | Return type + `{? = call …}` on PostgreSQL. |
| Single OUT/INOUT at JDBC ordinals, not PG return row | Return type + `CallableStatement`; placeholders **`1 … maxOrdinal`** from metadata. |
| Multiple OUT/INOUT | `void name(DataSource, …, T[] out1, …)`; each holder is a **single-element array** filled after `execute`. |

[`base.ftl`](../compiler/src/main/resources/template/java/base.ftl) supplies helpers such as `callableOutScalarExpression` for reading OUT values with the right JDBC getter.

---

## Seed SQL and Docker

Example DDL and routines for PostgreSQL live under [`init.db/postgres/`](../init.db/postgres/), including [`procedures.sql`](../init.db/postgres/procedures.sql) (accounts seed, `create_cache`, `transfer`, and additional routines used by datastore tests).

[`docker-compose.yml`](../docker-compose.yml) mounts **`init.db/postgres`** on **`/docker-entrypoint-initdb.d`**. Scripts run **only on first database initialization** for that volume; see the root [README.md](../README.md) for resetting volumes when DDL changes.

---

## Datastore tests

[`StoredProcedureTest`](../datastore/src/test/java/org/example/storedprocedure/StoredProcedureTest.java) documents the **generated API** in class-level Javadoc and groups tests by **IN-only**, **scalar functions**, **IN/OUT combinations**, **INOUT**, and **`transfer`**. Tests call **`DataManager.Procedure`** methods directly (they must exist after codegen).

After changing **`init.db/postgres/procedures.sql`** or **`Procedures.ftl`**, apply DDL (or recreate the Docker volume), **regenerate** sources into `datastore/src/main/java`, then run:

```bash
mvn -f datastore/pom.xml clean test
```

---

## Related documentation

- [Datastore module](datastore.md)
- [Compiler module](compiler.md)
- [Core module](core.md)
- [Project structure](project-structure.md) — `init.db/` and Docker.
- [Documentation index](README.md)
