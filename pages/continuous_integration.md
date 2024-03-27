---
id: ci
---

# Continuous Integration [Lachenicht]

On every pull request and merge into the main branch, the unit & integration tests of all microservices are executed automatically.
This is done via a continuous integration (CI) pipeline, implemented with Github Workflows.

Pipeline Steps:
1. Load JDK 17
2. Package and test all microservices
3. Determine test coverage using the JaCoCo plugin
4. Generate [test coverage badges](../pages/testing.md).