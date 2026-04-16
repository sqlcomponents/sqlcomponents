# Datastore module

The **datastore** Maven project ([`datastore/pom.xml`](../datastore/pom.xml)) is an **integration and example** layer: it contains hand-written tests that exercise **generated** persistence code (typically `DataManager`, `*Store`, and model types). It is **not** the same artifact as `core` or `compiler`; it does not implement the crawler or templates itself.

For Maven reactor notes (this module is **not** in the parent `<modules>` list), see [Project structure](project-structure.md).

## Artifact coordinates

- `groupId`: `org.example`
- `artifactId`: `datastore`
- `version`: aligned with parent (`1.0-SNAPSHOT`)

The generated code used in tests is configured to live under the `org.example` Java package (see compiler tests’ `rootPackage` in [`CompilerTestUtil`](../compiler/src/test/java/org/sqlcomponents/compiler/java/util/CompilerTestUtil.java)).

## Source layout

| Path | Role |
|------|------|
| `datastore/src/main/java/` | **Target for generated sources.** Often empty until you run `Application.compile(new JavaCompiler())` with `srcFolder` pointing here. |
| `datastore/src/test/java/org/example/` | Hand-written tests: `store/*StoreTest.java`, `repository/`, `storedprocedure/`, `util/` (`DataSourceProvider`, `EncryptionUtil`, …). |

Tests import generated types such as `DataManager`, `AccountsStore`, and `Accounts` from `org.example` after generation; see for example [`AccountsStoreTest`](../datastore/src/test/java/org/example/store/AccountsStoreTest.java).

## Typical workflow

1. Start a database (for example `docker compose up -d` using root [`docker-compose.yml`](../docker-compose.yml) — PostgreSQL on port **5432** with `moviedb` / scripts under `init.db/postgres`).
2. Ensure [`database.properties`](../database.properties) matches your JDBC URL, user, password, and schema.
3. Run code generation so `datastore/src/main/java` contains `org.example` sources (for example run compiler module tests, or a small `main` that calls `application.compile(new JavaCompiler())`).
4. Run datastore tests:

   ```bash
   mvn -f datastore/pom.xml clean test
   ```

Because `datastore` is outside the default reactor, `mvn clean install` from the **repository root** does not compile or test this module unless you add it to the parent POM or invoke it with `-f` as above.

## Dependencies (`datastore/pom.xml`)

- **PostgreSQL JDBC driver** — Runtime database access for tests.
- **Spring Data Commons** — Used by tests (for example paging/specification-style helpers where applicable).

There is **no** direct Maven dependency from `datastore` onto `core` or `compiler`; the link is **conceptual**: generated code is expected to be compatible with the JDBC stack you use at test time.

## JDK note

- The parent and datastore POMs declare **Java 17** for compilation.
- The root [README.md](../README.md) mentions broader JDK support historically (minimum 11, tested up to 18). Treat the **POM** as the authoritative build level for this tree; use README for informal compatibility notes.

## Related documentation

- [Compiler module](compiler.md) — How `srcFolder` and `rootPackage` are set for `org.example` output.
- [Core module](core.md) — What `Application` carries before compile.
- [Project structure](project-structure.md)
- [Documentation index](README.md)
