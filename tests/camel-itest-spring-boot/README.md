# camel-itest-spring-boot

The camel-itest-spring-boot module provides an integration testing framework for camel components, to check their compatibility with spring-boot.

Each test-case defined in `src/test/java/org/apache/camel/itest/springboot` executes the following steps:
- Creates a spring-boot jar by putting the spring-boot loader, test classes and some utility classes in the main jar,
and all other libraries (including the camel component under test) as nested jars;
- Launches a new JVM with the spring-boot jar in the classpath, then starts the spring-boot platform;
- Executes a list of predefined checks in the spring-boot environment to verify that the component has been created correctly:
checks that the camel context has been created, that the camel components can be activated (including data format and languages).

## Additional options

Test options can be changed from the `src/test/resources/spring-boot-itest.properties` file.

Some useful options include:
- **includeTestDependencies (default=false)**: when this option is enabled,
the integration test will locate the module `pom.xml` file and include in the spring-boot jar also the test-scoped dependencies of the module.
  The inclusion of other libraries often activates some hidden behaviour of spring-boot.
  *Note: logging libraries (eg. `log4j`) included in test scope are ignored, to prevent conflict with spring-boot logging system.*
- **unitTestEnabled (default=false)**: when this option is enabled,
the integration test will locate the test-classes of the module and run the unit tests after the execution of the usual checks.
  *Note: a full build of each component is required prior to running the unit tests. Test dependencies are implicitly included.*

