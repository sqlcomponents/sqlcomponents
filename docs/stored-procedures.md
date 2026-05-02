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
| **Qualified routine names** | [`Procedure.getSqlInvocationName()`](../core/src/main/java/org/sqlcomponents/core/model/relational/Procedure.java) emits **`schema.functionName`** when `functionSchema` is set (so routines outside the default **`search_path`** resolve). Templates use **`method.sqlInvocationName`** in [`Procedures.ftl`](../compiler/src/main/resources/template/java/Procedures.ftl). |
| **Overload disambiguation** | When several routines share the same JDBC **`functionName`**, [`Mapper.buildMethods`](../core/src/main/java/org/sqlcomponents/core/mapper/Mapper.java) sets the generated Java **`Method.name`** from **`Procedure.specificName`** (via [`getPropertyName`](../core/src/main/java/org/sqlcomponents/core/mapper/Mapper.java)). |
| **INOUT + extra OUT naming** | If an output parameter’s Java name would collide with an input name (typical for INOUT listed on both sides), the **output** [`Property`](../core/src/main/java/org/sqlcomponents/core/model/Property.java) is renamed (e.g. **`valueOut`**) in [`Mapper.getMethod`](../core/src/main/java/org/sqlcomponents/core/mapper/Mapper.java). |
| **JDBC layout** | `Procedures.ftl` builds placeholders from **`ordinalPosition`** (≥ 1), so **IN**, **OUT**, **INOUT** on the same index, and **multiple OUT** align with `CallableStatement` indices. |
| **Resource handling** | Generated callables use **`try (Connection; CallableStatement)`** so connections return to the pool (see template in [Procedures.ftl](../compiler/src/main/resources/template/java/Procedures.ftl)). |
| **PostgreSQL syntax** | **`CALL`** for `CREATE PROCEDURE`; **`{? = call …}`** for scalar **`CREATE FUNCTION`** return (ordinal **0**); IN-only procedures use **`SqlBuilder.prepareCall`** with `CALL` / `call` as appropriate. |
| **MySQL / MariaDB `CALL`** | When **`catalogProcedure`** is true, **`CALL …`** is emitted for **`MYSQL`** and **`MARIADB`** as well as **`POSTGRES`** (same `useSqlCallKeyword` branch in `Procedures.ftl`). |
| **ARRAY / STRUCT** | [`JavaMapper`](../compiler/src/main/java/org/sqlcomponents/compiler/mapper/JavaMapper.java): known PostgreSQL element prefixes (**`_int4`**, **`_int8`**, …) map to **`Integer[]`**, **`Long[]`**, **`String[]`**, … (canonical names); otherwise **`java.sql.Array`**. **`STRUCT`** → **`java.sql.Struct`**. [`Procedures.ftl`](../compiler/src/main/resources/template/java/Procedures.ftl) binds **`java.sql.Array`**, primitive/boxed SQL arrays, and **`Struct`**; rebinding for **`Array`** via **`createArrayOf`** as before. |
| **REF CURSOR OUT** | **`REF_CURSOR`** / **`refcursor`** → **`java.sql.ResultSet`**; **`registerOutParameter(..., Types.REF_CURSOR)`**; [`Procedure.detachRefCursorResultSet`](../compiler/src/main/resources/template/java/Procedures.ftl) copies into a **`CachedRowSet`** before the connection closes so callers can read safely. |
| **Rich OUT scalars** | [`base.ftl`](../compiler/src/main/resources/template/java/base.ftl) **`callableOutScalarExpression`**: common scalars, **`Array`**, **`Struct`**, **`ResultSet`** (detached), **`Clob`**. |
| **Seed + examples** | [`init.db/postgres/procedures.sql`](../init.db/postgres/procedures.sql): routines above plus **`proc_num_pair`** / **`fn_struct_pair_sum`**, **`sp_inout_plus_extra`**, **`sp_account_ids_cursor`** (OUT **`refcursor`**). |
| **Integration tests** | [`StoredProcedureTest`](../datastore/src/test/java/org/example/storedprocedure/StoredProcedureTest.java): existing groups plus **`StructAndRefCursor`**, **`spInoutPlusExtra`**; **`Integer[]`** for array sums. Compiler: [`JavaMapperArrayTest`](../compiler/src/test/java/org/sqlcomponents/compiler/mapper/JavaMapperArrayTest.java). |

### Not covered (or only partially) in source

| Gap | Notes |
|-----|--------|
| **`RETURNS TABLE` / set-returning functions** | No first-class generated API (e.g. `List<Row>`); only scalar return and OUT/`CallableStatement` paths. |
| **PostgreSQL `VARIADIC` + JDBC** | A true **`VARIADIC`** parameter requires SQL like **`fn(VARIADIC $1::type[])`**; generated **`{? = call fn(?)}`** does not emit that. Seed uses plain **`integer[]`** where JDBC passes one array. |
| **Named JDBC parameters** | Positional **`?`** only; no `CallableStatement` / `SqlBuilder` named-parameter API. |
| **Full multi-database matrix** | **`CALL`** branching covers **Postgres / MySQL / MariaDB**; **H2**, **Oracle** (not in [`DBType`](../core/src/main/java/org/sqlcomponents/core/model/relational/enums/DBType.java)), etc. are not exhaustively verified in templates. |
| **Procedure `COMMIT` / autonomous transactions** | Behaviour is defined in SQL bodies, not in codegen. `transfer` avoids an internal **`COMMIT`**. |

---

## Catalog sources (`core`)

[`Crawler.getProcedures`](../core/src/main/java/org/sqlcomponents/core/crawler/Crawler.java) merges two JDBC catalogs into one list on `Database.setFunctions` (historical name; entries are not all “functions”):

| JDBC API | SQL objects | `Procedure` flag |
|----------|-------------|-------------------|
| [`DatabaseMetaData.getProcedures`](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/DatabaseMetaData.html#getProcedures(java.lang.String,java.lang.String,java.lang.String)) | e.g. PostgreSQL **`CREATE PROCEDURE`** | **`catalogProcedure == true`** |
| [`DatabaseMetaData.getFunctions`](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/DatabaseMetaData.html#getFunctions(java.lang.String,java.lang.String,java.lang.String)) | e.g. PostgreSQL **`CREATE FUNCTION`** | **`catalogProcedure == false`** |

The relational type [`Procedure`](../core/src/main/java/org/sqlcomponents/core/model/relational/Procedure.java) carries:

- `functionName`, schema, remarks, JDBC `functionType`, parameters (`Column` lists for IN/OUT, including INOUT duplicated across input and output lists as returned by the driver).
- **`boolean catalogProcedure`** — distinguishes procedure-catalog rows from function-catalog rows so the **compiler** can emit correct SQL (**`CALL`**, JDBC escapes, …).

Parameter **ordinal positions** from metadata (`Column.ordinalPosition`) drive JDBC **`?`** indices in generated `CallableStatement` code (one placeholder per ordinal ≥ 1; INOUT uses the same index for `set*` and `registerOutParameter`).

---

## Code generation (`compiler`)

Routines become `public static final class DataManager.Procedure` methods via [`Procedures.ftl`](../compiler/src/main/resources/template/java/Procedures.ftl), included from [`Manager.ftl`](../compiler/src/main/resources/template/java/Manager.ftl).

### PostgreSQL: `CALL` vs `{call …}`

- PostgreSQL **`CREATE FUNCTION`** with a scalar return is invoked with JDBC **`{? = call name(?, …)}`** when metadata exposes a **single return column at ordinal 0** (see `usePgFunctionReturnSyntax` in the template).
- Routines that are **`CREATE PROCEDURE`** must be executed with SQL **`CALL name(?, …)`** where the dialect expects it. The legacy JDBC escape **`{call name(?, …)}`** is wrong for PostgreSQL procedures (*“is a procedure … use CALL”*).

When **`catalogProcedure`** is true and **`orm.database.dbType`** is **`POSTGRES`**, **`MYSQL`**, or **`MARIADB`**, the template emits **`prepareCall("CALL …")`** for the applicable paths. Other databases keep **`{call …}`** unless extended similarly.

Callable paths use **`try (Connection c = …; CallableStatement cs = c.prepareCall(…))`** so both resources close; chaining **`try (CallableStatement cs = ds.getConnection().prepareCall(…))`** would only close the statement and **leak** the connection back to the pool.

### Shapes of generated Java API

| Metadata shape | Generated pattern |
|----------------|-------------------|
| IN only, no OUT | `void name(DataSource, …)` using `SqlBuilder.prepareCall`. |
| Single scalar **function** return (ordinal 0) + IN list | Return type + `{? = call …}` on PostgreSQL. |
| Scalar return + IN includes **`ARRAY`** / **`Integer[]`** (known element types) | Same as scalar function path; see **`emitCallableInBind`** in [`Procedures.ftl`](../compiler/src/main/resources/template/java/Procedures.ftl). |
| Single OUT/INOUT at JDBC ordinals, not PG return row | Return type + `CallableStatement`; placeholders **`1 … maxOrdinal`** from metadata. |
| **`REF CURSOR`** OUT (single) | Return **`ResultSet`** (detached **`CachedRowSet`**). |
| Multiple OUT/INOUT | `void name(DataSource, …, T[] out1, …)`; each holder is a **single-element array** filled after `execute` (or **`ResultSet`** holder where applicable). |

[`base.ftl`](../compiler/src/main/resources/template/java/base.ftl) supplies **`callableOutScalarExpression`** for reading OUT values.

---

## Seed SQL and Docker

Example DDL and routines for PostgreSQL live under [`init.db/postgres/`](../init.db/postgres/), including [`procedures.sql`](../init.db/postgres/procedures.sql) (accounts, caches, transfer, scalar/array functions, composite type, INOUT+OUT, refcursor procedure).

[`docker-compose.yml`](../docker-compose.yml) mounts **`init.db/postgres`** on **`/docker-entrypoint-initdb.d`**. Scripts run **only on first database initialization** for that volume; see the root [README.md](../README.md) for resetting volumes when DDL changes.

---

## Datastore tests

[`StoredProcedureTest`](../datastore/src/test/java/org/example/storedprocedure/StoredProcedureTest.java) documents the **generated API** in class-level Javadoc and groups tests by **IN-only**, **scalar functions**, **array IN**, **STRUCT / REF_CURSOR**, **IN/OUT**, **INOUT** (including **INOUT + extra OUT**), and **`transfer`**. Tests call **`DataManager.Procedure`** methods directly (they must exist after codegen).

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
