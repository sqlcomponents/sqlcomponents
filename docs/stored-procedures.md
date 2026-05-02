# Stored procedures and functions (generated `DataManager.Procedure`)

This document describes how **SQL routines** (JDBC `DatabaseMetaData` procedures and functions) become **`DataManager.call()`** methods, how **PostgreSQL** differs between **`CREATE PROCEDURE`** and **`CREATE FUNCTION`**, and where **tests** and **seed SQL** live.

For the example module that runs these tests, see [Datastore module](datastore.md). For template placement, see [Compiler module](compiler.md). For metadata collection, see [Core module](core.md).

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

[`StoredProcedureTest`](../datastore/src/test/java/org/example/storedprocedure/StoredProcedureTest.java) documents the **generated API** in class-level Javadoc and groups tests by **IN-only**, **scalar functions**, **IN/OUT combinations**, **INOUT**, and **`transfer`**.

Some tests use **reflection** plus **`Assumptions`** so the module can compile **before** you regenerate `org.example` after adding routines; once **`DataManager.Procedure`** includes the new methods, those tests execute normally.

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
