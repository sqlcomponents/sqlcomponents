# Core module

The **core** module (`org.sqlcomponents:core`) is the foundation of SQL Components. It connects to a database through JDBC, reads catalog metadata, and represents that metadata as Java objects. It also defines the configuration surface (`Application`) and the small **Compiler** SPI that the **compiler** module implements.

For repository layout and Maven coordinates, see [Project structure](project-structure.md).

## Responsibilities at a glance

1. **Introspection**: Walk JDBC `DatabaseMetaData` (and related queries) to build a graph of schemas, tables, columns, keys, indexes, procedures, and user-defined types.
2. **Domain model**: Express that graph as immutable-friendly POJOs under `org.sqlcomponents.core.model` and `org.sqlcomponents.core.model.relational`.
3. **Configuration**: Hold everything needed to open a datasource, filter objects, name Java packages, and tune ORM behavior—primarily via `Application` and nested `ORM`.
4. **Compilation contract**: Declare `org.sqlcomponents.core.compiler.Compiler` so other modules can generate artifacts without core knowing about FreeMarker or Java source layout.

## Package map

| Package | Role |
|---------|------|
| `org.sqlcomponents.core.crawler` | `Crawler` base logic; database-specific subclasses (e.g. PostgreSQL, MySQL) refine behavior. |
| `org.sqlcomponents.core.crawler.util` | Helpers such as `DataSourceUtil` for constructing datasources. |
| `org.sqlcomponents.core.model` | Top-level app concepts: `Application`, `Entity`, `ORM`, `Method`, `Service`, … |
| `org.sqlcomponents.core.model.relational` | Relational catalog types: `Database`, `Table`, `Column`, `Key`, `Index`, `Procedure`, `Type`, constraints, `Package`, enums (`ColumnType`, `DBType`, `TableType`, …). |
| `org.sqlcomponents.core.compiler` | `Compiler` interface: `void compile(Application application) throws SQLException`. |
| `org.sqlcomponents.core.mapper` | Abstract `Mapper` used by language-specific mappers (e.g. `JavaMapper` in the compiler module). |
| `org.sqlcomponents.core.exception` | Shared error types. |
| `org.sqlcomponents.core.utils` | `CoreConsts` and other utilities. |

## Crawler vs dialect crawlers

- **`Crawler`** ([`Crawler.java`](../core/src/main/java/org/sqlcomponents/core/crawler/Crawler.java)) centralizes JDBC metadata access: tables, columns, imported/exported keys, indexes, procedures, and more. It recognizes product names such as PostgreSQL, MySQL/MariaDB, H2, Oracle, SQL Server.
- **Dialect-specific classes** (e.g. [`PostgresCrawler`](../core/src/main/java/org/sqlcomponents/core/crawler/PostgresCrawler.java), [`MysqlCrawler`](../core/src/main/java/org/sqlcomponents/core/crawler/MysqlCrawler.java)) extend or specialize behavior where generic metadata is insufficient or dialect-specific SQL is required.

The crawler layer uses **HikariCP** (provided at parent POM level) and standard JDBC types.

## Key types

### `Application`

[`Application`](../core/src/main/java/org/sqlcomponents/core/model/Application.java) is the root configuration object: human-readable name, JDBC URL, credentials, driver, schema, table/sequence include patterns, target `srcFolder`, `rootPackage`, naming maps (`wordsMap`, `modulesMap`, `pluralMap`), insert/update default maps, encryption column lists, and references to `ORM`.

Important behavior:

- **`compile(Compiler)`**: If `srcFolder` exists, it is **deleted recursively** (newest paths first), then `compiler.compile(this)` is invoked. Any implementation must repopulate that directory.

### `ORM`

Holds schema-level ORM settings and the entity graph produced after mapping (see compiler module’s mapper). `Application` delegates many getters/setters to `ORM`.

### `Entity`

Represents one generated unit (typically aligned with a table, view, or enum type), carrying relational metadata and naming used by templates.

### Relational types

Types under `org.sqlcomponents.core.model.relational` mirror JDBC catalog concepts so the compiler can emit Java without re-querying the database during generation.

## Configuration loading (YAML)

[`CoreConsts.buildApplication(File)`](../core/src/main/java/org/sqlcomponents/core/utils/CoreConsts.java) loads an `Application` from a YAML file using SnakeYAML’s `Constructor(Application.class)`, then sets `methodSpecification` to `Application.METHOD_SPECIFICATION` by default.

Compiler tests can instead use properties-based setup via [`CompilerTestUtil`](../compiler/src/test/java/org/sqlcomponents/compiler/java/util/CompilerTestUtil.java) when `SQLCOMPONENTS_CONFIG` is not set.

## Dependencies (`core/pom.xml`)

- **SnakeYAML**: YAML configuration for `Application`.
- **JetBrains annotations**: Nullable/not-null hints for static analysis.

Other JDBC drivers and connection pooling are inherited from the **parent** POM for the whole project; core code expects a JDBC `DataSource` or equivalent when crawling.

## Relationship to the compiler module

The compiler module depends on **core** only. Core does **not** reference FreeMarker or `JavaCompiler`. Generation is triggered by application code calling `application.compile(new JavaCompiler())` (or another `Compiler` implementation).

## Related documentation

- [Compiler module](compiler.md)
- [Datastore module](datastore.md)
- [Project structure](project-structure.md)
- [Documentation index](README.md)
