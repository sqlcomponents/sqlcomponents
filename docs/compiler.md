# Compiler module

The **compiler** module (`org.sqlcomponents:compiler`) turns a populated `Application` (from **core**) into Java source files: a **DataManager**, per-entity **Store** classes, **Record** (or similar) model types, and **enum**-style artifacts for certain database types. It implements `org.sqlcomponents.core.compiler.Compiler` via [`JavaCompiler`](../compiler/src/main/java/org/sqlcomponents/compiler/java/JavaCompiler.java).

For how metadata reaches `Application`, see [Core module](core.md). For where output is written in this repo’s workflow, see [Datastore module](datastore.md).

## End-to-end pipeline (`JavaCompiler.compile`)

At a high level, `JavaCompiler`:

1. **Optional Flyway migrations** — If Flyway is on the classpath and `src/main/resources/db/migration` exists relative to the process working directory, runs Flyway against a datasource built from `Application`, then closes the datasource. This is best-effort and environment-specific; see source for details.
2. **Map relational model to ORM view** — Constructs [`JavaMapper`](../compiler/src/main/java/org/sqlcomponents/compiler/mapper/JavaMapper.java), which extends core’s [`Mapper`](../core/src/main/java/org/sqlcomponents/core/mapper/Mapper.java), and assigns the resulting `ORM` on `Application`.
3. **Emit Java** — Uses FreeMarker via [`FTLTemplate`](../compiler/src/main/java/org/sqlcomponents/compiler/template/FTLTemplate.java) to render:
   - **Manager** — [`Manager.ftl`](../compiler/src/main/resources/template/java/Manager.ftl) → `DataManager.java` under the root package folder.
   - **Store** — [`Store.ftl`](../compiler/src/main/resources/template/java/Store.ftl) → `{Entity}Store.java` under each entity’s DAO package.
   - **Model / record** — [`Record.ftl`](../compiler/src/main/resources/template/java/Record.ftl) → `{Entity}.java` under the bean package.
   - **Enum / type** — For entities whose type is an enumerated DB type, [`Enum.ftl`](../compiler/src/main/resources/template/java/Enum.ftl) → `{Entity}Type.java`.

Entity processing uses a **parallel stream** over `orm.getEntities()` for throughput.

`Application.compile(Compiler)` (in core) **deletes the entire `srcFolder` tree** before calling `Compiler.compile`, so every run is a clean generation.

## `JavaMapper`

[`JavaMapper`](../compiler/src/main/java/org/sqlcomponents/compiler/mapper/JavaMapper.java) is responsible for mapping SQL column semantics (via core’s `Column` / `ColumnType`) to Java types—for example primitives, `String`, `UUID`, `BigDecimal`, JTS geometry types, JSON node types, and time API classes. The mapper feeds the templates so generated fields and method signatures match the database.

## FreeMarker templates

Templates live under [`compiler/src/main/resources/template/java/`](../compiler/src/main/resources/template/java/). `FTLTemplate` loads them from the classpath relative to `JavaCompiler`.

Notable groups:

| Subdirectory / file | Purpose |
|---------------------|-----------|
| `Manager.ftl`, `Store.ftl`, `Record.ftl`, `Enum.ftl` | Top-level artifacts per entity or application. |
| `column/*.ftl` | Fragments for SQL/expression handling per Java column kind (e.g. `BooleanColumn.ftl`, `UUIDColumn.ftl`, geometry columns). |
| `method/*.ftl` | Statement builders: `SelectStatement`, `InsertStatement`, `UpdateStatement`, `DeleteStatement`, `MViewRefresh`. |
| `query/*.ftl`, `clause/*.ftl` | Query and WHERE composition. |
| `base.ftl`, `jdbcbase.ftl`, `SqlBuilder.ftl`, … | Shared includes. |
| `template/directive/` | Custom FreeMarker directives (e.g. column selection). |

Generated `package-info.java` files are also written with a minimal `package …;` declaration for each output package.

## Dependencies (`compiler/pom.xml`)

- **FreeMarker** — Template engine.
- **JTS Core** — Geometry types referenced by `JavaMapper` / templates for spatial columns.
- **Flyway** (`provided` / optional usage) — Migration hook described above.
- **core** — Same-version `org.sqlcomponents:core`.

Drivers and pooling come from the **parent** POM when running in tests or integrated apps.

## How to run code generation in practice

### Properties-based setup (tests / local dev)

[`CompilerTestUtil.getApplication()`](../compiler/src/test/java/org/sqlcomponents/compiler/java/util/CompilerTestUtil.java) builds an `Application` when `SQLCOMPONENTS_CONFIG` is **not** set:

- Reads [`database.properties`](../database.properties) from the current working directory or parent (`../database.properties`).
- Uses `DATABASE_TYPE` env var or defaults to `postgres` for property key prefix (`postgres.datasource.url`, …).
- Sets `rootPackage`, encryption lists, insert/update maps, and **`srcFolder`** to `datastore/src/main/java` (or `../datastore/src/main/java` depending on cwd).

Then:

```java
Application application = CompilerTestUtil.getApplication();
application.compile(new JavaCompiler());
```

Run from Maven with the working directory that matches your paths (often `compiler/` for tests).

### YAML-based setup (CI / explicit config)

When **`SQLCOMPONENTS_CONFIG`** points to a YAML file:

- `Application` is built with [`CoreConsts.buildApplication(File)`](../core/src/main/java/org/sqlcomponents/core/utils/CoreConsts.java).
- **`SOURCE_FOLDER`** must be set to the output directory for generated sources (throws if missing).

## SPI: `Compiler` interface

The compiler module is the reference implementation of:

```java
void compile(Application application) throws SQLException;
```

Other implementations could target different languages or layouts as long as they accept `Application`.

## Related documentation

- [Core module](core.md)
- [Datastore module](datastore.md)
- [Project structure](project-structure.md)
- [Documentation index](README.md)
