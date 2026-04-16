# Project structure

This document describes the repository layout, Maven modules, and where generated and test artifacts live.

## Top-level layout

| Path | Role |
|------|------|
| `pom.xml` | Parent POM for `org.sqlcomponents:sqlcomponents`; dependency versions, Checkstyle, Surefire/Failsafe, JaCoCo, distribution management. |
| `core/` | Library: JDBC metadata crawling and shared domain model. |
| `compiler/` | Library: Java code generation from the model (FreeMarker templates). Depends on `core`. |
| `datastore/` | Separate Maven project (example/integration): tests against PostgreSQL; expects generated Java under `src/main/java` when you run the compiler. |
| `init.db/` | SQL seed scripts per engine (`postgres/`, `h2db/`, `sqlserver/`, etc.) used by Docker or local DB setup. |
| `database.properties` | Local JDBC URLs and credentials (used by compiler tests via `CompilerTestUtil`). |
| `docker-compose.yml` | Local PostgreSQL (`moviedb` on port 5432) with `init.db/postgres` mounted as init scripts. |
| `.mvn/` | Maven wrapper support. |
| `hooks-directory/` | Git hooks path referenced from the parent POM (project-specific). |
| `checkstyle-suppressions.xml` | Root Checkstyle suppressions. |
| `.github/` | Issue templates and CI workflows (`ci-develop.yml`, `ci-publish.yml`). |
| `LICENSE` | Project license. |
| `docs/` | Human-oriented documentation (this folder). |

## Maven reactor (important)

The parent [`pom.xml`](../pom.xml) lists only these modules:

- `core`
- `compiler`

The [`datastore`](../datastore/pom.xml) module **inherits** the same parent but is **not** listed in `<modules>`. Running `mvn clean install` from the repository root builds `core` and `compiler` only. To build or test `datastore`, run Maven against that POM explicitly, for example:

```bash
mvn -f datastore/pom.xml clean test
```

Alternatively, add `<module>datastore</module>` to the parent `pom.xml` if you want a single reactor build (that would be a code change outside this doc).

## Module dependency direction

```text
compiler  -->  core
datastore -->  (no compile dependency on core/compiler in POM; uses generated code and JDBC/Spring Data Commons at test time)
```

- **`core`**: Standalone; no dependency on `compiler`.
- **`compiler`**: Declares a compile dependency on `${project.groupId}:core:${project.version}`.
- **`datastore`**: Test code imports generated types such as `org.example.DataManager` and `*Store`; those sources are produced by running the compiler with `rootPackage` / output folder aligned to `org.example` and `datastore/src/main/java`.

## `core` layout (summary)

| Path | Contents |
|------|----------|
| `core/src/main/java/org/sqlcomponents/core/crawler/` | `Crawler`, dialect crawlers (`PostgresCrawler`, `MysqlCrawler`, …), `util` (e.g. `DataSourceUtil`). |
| `core/src/main/java/org/sqlcomponents/core/model/` | `Application`, `Entity`, `ORM`, `Service`, … |
| `core/src/main/java/org/sqlcomponents/core/model/relational/` | `Database`, `Table`, `Column`, `Key`, `Index`, enums, … |
| `core/src/main/java/org/sqlcomponents/core/compiler/` | `Compiler` interface (implemented by `JavaCompiler` in the compiler module). |
| `core/src/main/java/org/sqlcomponents/core/mapper/` | Base `Mapper` type extended by language-specific mappers. |
| `core/src/main/java/org/sqlcomponents/core/utils/` | `CoreConsts` (YAML → `Application`), constants. |
| `core/src/test/java/` | Unit/integration tests for core behavior. |

## `compiler` layout (summary)

| Path | Contents |
|------|----------|
| `compiler/src/main/java/org/sqlcomponents/compiler/java/` | `JavaCompiler` (main entry). |
| `compiler/src/main/java/org/sqlcomponents/compiler/mapper/` | `JavaMapper` (SQL/Java type mapping). |
| `compiler/src/main/java/org/sqlcomponents/compiler/template/` | `FTLTemplate`, FreeMarker directives. |
| `compiler/src/main/resources/template/java/` | `.ftl` templates: `Manager.ftl`, `Store.ftl`, `Record.ftl`, `Enum.ftl`, plus `column/`, `method/`, `query/`, … |
| `compiler/src/test/java/` | Compiler and mapper tests; utilities such as `CompilerTestUtil`. |

## `datastore` layout (summary)

| Path | Contents |
|------|----------|
| `datastore/src/main/java/` | **Usually empty in a fresh clone.** Populated by running code generation (compiler tests or your own `Application.compile` run) into this folder. |
| `datastore/src/test/java/org/example/` | Hand-written tests: `store/`, `repository/`, `storedprocedure/`, `util/` (e.g. `DataSourceProvider`). |

## Generated output

When you run `Application.compile(new JavaCompiler())` (or equivalent), the compiler writes Java sources under the configured `srcFolder` (for example `datastore/src/main/java`). Typical packages include:

- A root `DataManager` class.
- Per-entity **Store** and **Record** (or enum) types under the configured `rootPackage`.

The exact package names match your `Application` configuration (`rootPackage`, module maps, etc.).

## Build and quality tooling (parent POM)

- **Java version**: Parent properties use `java.version` **17** (`maven.compiler.source` / `target`).
- **Checkstyle**: Runs in the `validate` phase via `maven-checkstyle-plugin`.
- **Tests**: `maven-surefire-plugin` (unit), `maven-failsafe-plugin` (integration-oriented configuration).
- **JaCoCo**: Configured at parent level with includes pointing at `org/example/store/*` (aligned with generated store packages in examples).

For day-to-day commands (Docker, `mvn package`, skipping tests), see the root [README.md](../README.md).

## Related documentation

- [Core module](core.md)
- [Compiler module](compiler.md)
- [Datastore module](datastore.md)
- [Documentation index](README.md)
