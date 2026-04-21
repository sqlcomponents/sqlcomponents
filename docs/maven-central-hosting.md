# Hosting SQL Components on Maven Central

This document is for **maintainers** who want to publish this project so others can depend on it from [Maven Central](https://central.sonatype.org/). It describes what gets published, what must be configured in the POM and tooling, and a practical checklist. Always cross-check with the current [Central Publisher / Sonatype documentation](https://central.sonatype.org/publish/publish-guide/)—requirements and UIs change over time.

---

## What this repository publishes

The root [`pom.xml`](../pom.xml) is a **parent POM** (`packaging` `pom`). It does not ship application code by itself. The artifacts consumers use are:

| Artifact | Role |
|----------|------|
| `org.sqlcomponents:sqlcomponents` | Parent POM (aggregator); may be published so consumers can import it as `<parent>` or BOM-style usage if you add `dependencyManagement`. |
| `org.sqlcomponents:core` | Library: JDBC metadata and domain model. |
| `org.sqlcomponents:compiler` | Library: Java code generation (`JavaCompiler`, templates). Depends on `core`. |
| `org.sqlcomponents:maven-plugin` | Maven plugin: `generate` goal, reads `sql-component.yml`. Depends on `compiler`. |

**Not published for Central:** the **`datastore`** directory is an example/integration project and is **not** listed in the root `<modules>`; keep it that way unless you intentionally release a separate example artifact.

---

## Prerequisites (before you change POMs)

1. **Sonatype Central Portal account**  
   Register at the [Sonatype Central Portal](https://central.sonatype.com/) (or follow the current sign-up flow linked from [central.sonatype.org](https://central.sonatype.org/)).

2. **Namespace verification**  
   You must **verify** that you may publish under the groupId **`org.sqlcomponents`**. Sonatype ties this to domain ownership or an approved GitHub/GitLab namespace flow—follow their wizard for your situation.

3. **GPG signing**  
   Central requires **cryptographic signatures** for released artifacts. Create a **GPG key pair**, distribute the **public** key to a keyserver Sonatype accepts, and keep the **private** key and passphrase in a secure place (and in CI secrets if you automate releases).

4. **Release version**  
   Central **release** repositories do not accept perpetual **`-SNAPSHOT`** versions. Plan a **semver** release (e.g. `1.0.0`) and tag it in Git.

---

## POM requirements (Central metadata)

The **root** `pom.xml` should satisfy Central’s metadata expectations for itself and as the parent for modules. Typical gaps to address:

| Area | Notes |
|------|--------|
| **Coordinates** | `groupId` `org.sqlcomponents` must match your verified namespace. |
| **`<name>` / `<description>` / `<url>`** | Present; prefer **`https`** for `<url>` and license URLs. |
| **`<licenses>`** | Already Apache 2.0; use `https` for the license URL if you update it. |
| **`<scm>`** | Add **`connection`** and **`developerConnection`** (e.g. `scm:git:...`) and a **`tag`** (often `HEAD` or the release tag). Today only `<url>` may be present—expand per Central validation messages. |
| **`<developers>`** | Add at least one developer (`id`, `name`, and usually `email` / `organization`). |

Module POMs inherit much of this from the parent; keep descriptions accurate for `core`, `compiler`, and `maven-plugin`.

---

## Build artifacts Central expects

For each **published module** (`core`, `compiler`, `maven-plugin`, and optionally the parent `sqlcomponents` POM), releases usually include:

- The main artifact (JAR or `pom` for the parent)
- **`-sources.jar`** (via `maven-source-plugin`)
- **`-javadoc.jar`** (via `maven-javadoc-plugin`)
- **`.asc` signatures** for each file above and the POM (via `maven-gpg-plugin`)

A common approach is a **`release` Maven profile** that activates these plugins so day-to-day `mvn verify` stays light, while `mvn deploy -Prelease` performs the full Central payload.

---

## Deployment to Central (high level)

1. **Credentials**  
   In the Central Portal, create a **user token** (or the credential type they document) for uploading. Map these to a `<server>` in your **`~/.m2/settings.xml`** (and CI secrets) with `id` matching what your `distributionManagement` or publishing plugin expects.

2. **Publishing plugin**  
   Use the **current** Sonatype-recommended approach for OSS publishing (for example the **Central Publishing** Maven plugin or the documented successor). The exact coordinates and configuration evolve; follow [central.sonatype.org](https://central.sonatype.org/publish/publish-guide/).

3. **Staging and release**  
   Depending on the flow, you may upload to a staging area and then **release** (or close/promote) the deployment from the portal until artifacts sync to Maven Central.

4. **GitHub Packages vs Central**  
   This repo may still have `<distributionManagement>` pointing at **GitHub Packages**. For Central releases, either **replace** that with Central staging endpoints for the release profile, or **split** into profiles: e.g. `github` vs `central`, so `deploy` targets the right repository.

---

## CI automation (optional but recommended)

1. Trigger on **Git tags** matching releases (e.g. `v1.0.0`) or on manual **workflow_dispatch**.

2. Use **`actions/setup-java`** with **GPG import** from secrets (`GPG_PRIVATE_KEY`, `GPG_PASSPHRASE`) and pass Central credentials.

3. Run something like:  
   `mvn -Prelease clean deploy`  
   (exact flags depend on your profile and publishing plugin.)

4. Do **not** print secrets in logs; mask Central tokens and GPG material.

---

## Local dry run (before the first real release)

1. `mvn clean verify` (and fix failures). Note: some integration tests may require a live database; CI or contributors may use Docker per [`README.md`](../README.md)).

2. With the **release** profile enabled: build and confirm **`target`** contains expected JARs, **`-sources`**, **`-javadoc`**, and that signing runs without errors.

3. Optionally deploy to a **local** or **staging** Nexus to rehearse the upload.

---

## After release

1. On [central.sonatype.com](https://central.sonatype.com/) (or [search.maven.org](https://search.maven.org/)), confirm:

   - `org.sqlcomponents:core`
   - `org.sqlcomponents:compiler`
   - `org.sqlcomponents:maven-plugin`
   - (if published) `org.sqlcomponents:sqlcomponents` parent POM  

2. In a **fresh** sample project, add dependencies or the plugin **without** a custom repository block (once sync is complete).

3. Document the **minimum** consumer version in [`maven-plugin.md`](maven-plugin.md) and the root [`README.md`](../README.md).

---

## Quick reference: maintainer checklist

- [ ] Namespace **`org.sqlcomponents`** verified with Sonatype  
- [ ] Root POM: **`developers`**, full **`scm`**, **`https`** URLs where applicable  
- [ ] Release version (no `-SNAPSHOT` for Central release line)  
- [ ] **`release` profile**: sources, javadoc, GPG signing  
- [ ] **`distributionManagement` / publishing plugin** aimed at Central for release  
- [ ] GPG key published and passphrase stored securely  
- [ ] CI workflow for tagged releases (optional)  
- [ ] Smoke test: resolve artifacts from Central in a blank project  

---

## Related docs

- [Maven plugin usage](maven-plugin.md) — how end users add `org.sqlcomponents:maven-plugin` and `sql-component.yml`  
- [Project structure](project-structure.md) — modules and what is excluded from the reactor  
