# Contributing to SQL Components

Thank you for helping improve this project. This document explains how to work with the repository in a way that keeps changes reviewable and CI-friendly.

## Code of conduct

Be respectful and assume good intent in issues and pull requests. Follow the tone set in existing discussions and GitHub’s community guidelines.

## Before you start

1. **Read the docs** — [docs/README.md](docs/README.md) and the module pages under [docs/](docs/) explain architecture, Maven layout, and the datastore workflow.
2. **Check the license** — [LICENSE](LICENSE) (Apache 2.0). Contributions are expected to be licensed under the same terms unless explicitly stated otherwise.

## Development setup

- **JDK**: The build uses **Java 17** in the Maven POMs. A JDK that matches `maven.compiler.source` / `target` is required.
- **Maven**: Use the Maven Wrapper if present, or a recent Maven 3.x aligned with the project.
- **Database (optional)**: For integration-style tests, PostgreSQL can be started with root `docker-compose.yml` (port **5432**, database `moviedb`). Align [database.properties](database.properties) with your environment.

## Building and testing

From the repository root (builds **core** and **compiler** only):

```bash
mvn clean verify
```

To skip tests when you only need a compile:

```bash
mvn clean package -Dmaven.test.skip=true
```

To build and test the **datastore** example module (not part of the default reactor):

```bash
mvn -f datastore/pom.xml clean test
```

Generate sources into `datastore/src/main/java` before datastore tests if that directory is empty (for example by running the compiler module’s generation tests or your own small driver that calls `Application.compile(new org.sqlcomponents.compiler.java.JavaCompiler())`).

## Style and quality

- **Checkstyle** runs in the `validate` phase. Fix violations rather than disabling rules unless there is a strong reason and a suppression entry is agreed with maintainers.
- Match **existing code style**: naming, formatting, and Javadoc level in the files you touch.
- Prefer **small, focused PRs** with a clear description of behavior change.

## How to contribute changes

1. **Fork** the repository (if contributing from outside the main org) and create a **feature branch** from the appropriate default branch.
2. **Implement** your change with tests where behavior is non-trivial or regression-prone.
3. **Run** `mvn clean verify` (and datastore tests if you changed generation or example code paths).
4. **Open a pull request** that:
   - Summarizes the problem and the solution in complete sentences.
   - Links related issues (e.g. `Fixes #123`).
   - Calls out any breaking changes, migration steps, or follow-up work.

## Issues

Use the GitHub issue templates under `.github/ISSUE_TEMPLATE/` when filing bugs, features, or CI reports. Include:

- Expected vs actual behavior  
- JDK and Maven versions  
- Relevant logs or minimal reproduction steps  

## Security

Do not open public issues for **undisclosed security vulnerabilities**. Contact maintainers through a private channel if one is documented on the repository; otherwise use GitHub’s security advisory feature if enabled.

## Questions

If something in [docs/](docs/) is unclear or wrong, opening an issue or a documentation-only PR is welcome.
