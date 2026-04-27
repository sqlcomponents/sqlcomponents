# SQL Components Maven plugin

The **`org.sqlcomponents:maven-plugin`** artifact runs code generation during the Maven lifecycle by loading **`sql-component.yml`** from the consuming project (default: `${project.basedir}/sql-component.yml`).

It executes the same path as calling `application.compile(new JavaCompiler())` after loading the YAML with `CoreConsts.buildApplication(File)`.

## Add the plugin to your `pom.xml`

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.sqlcomponents</groupId>
      <artifactId>maven-plugin</artifactId>
      <version>1.0-SNAPSHOT</version>
      <executions>
        <execution>
          <phase>generate-sources</phase>
          <goals>
            <goal>generate</goal>
          </goals>
        </execution>
      </executions>
      <dependencies>
        <!-- JDBC driver for the database you connect to at build time -->
        <dependency>
          <groupId>org.postgresql</groupId>
          <artifactId>postgresql</artifactId>
          <version>42.7.4</version>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```

Optional properties:

| Property | Description |
|----------|-------------|
| `sqlcomponents.configFile` | Path to YAML (default: `${project.basedir}/sql-component.yml`) |
| `sqlcomponents.outputDirectory` | Generated sources directory (overrides `srcFolder` in YAML and the default below) |

If neither `sqlcomponents.outputDirectory` nor `srcFolder` in YAML is set, output goes to **`target/generated-sources/sqlcomponents`**.

## Example `sql-component.yml`

Place this file in the **root of the consumer project** (same directory as that project’s `pom.xml`). The schema matches the `Application` model (see `core` module); connection fields can be set at the top level because `Application` delegates them to `ORM`.

```yaml
name: MyApp
rootPackage: com.example.app
# Optional; else default is target/generated-sources/sqlcomponents
# srcFolder: target/generated-sources/sqlcomponents
url: jdbc:postgresql://localhost:5432/moviedb
userName: postgres
password: postgres
schemaName: public
driverName: org.postgresql.Driver
tablePatterns:
  - ".*"
```

Adjust `url`, credentials, and patterns to your database. The build must be able to reach the database from the machine running Maven.

## Publishing to Maven Central

Maintainers: see the full guide **[Maven Central hosting](maven-central-hosting.md)** (namespace verification, POM metadata, signing, deployment, CI, and checklist).

Until artifacts are on Central, install locally with `mvn clean install` and use `<version>1.0-SNAPSHOT</version>` from your local `~/.m2`, or configure a snapshot repository.
