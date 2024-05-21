# Repul-GLO-4003
## Français
Application de gestion de boîte-repas style HelloFresh réalisée dans le cours GLO-4003, Architecture logicielle à l'université Laval.

Pour des raisons de propriété intellectuelle, le code ne peut être public, mais vous pouvez me contacter pour plus d'information.

## English
Meal box management application similar to HelloFresh developed for the GLO-4003 course, Software Architecture at Laval University.

Due to intellectual property reasons, the code cannot be made public, but feel free to contact me for more information.


## Prerequisites

* Java 17+ (OpenJDK only)
* Maven
* JUnit 5+
* Mockito 3+
* Jersey
* Jetty
* Jackson

## How to Use

* You can manually install Java 17 and update the JAVA_HOME variable. However, we recommend using
  [SDKMAN](https://sdkman.io/) to manage Java and several tools and frameworks related to the JVM.
  This tool will greatly simplify the management of Java versions and related tools, especially if
  you need multiple versions on your machine.

* Once you have Java 17 and Maven installed and configured, to run the application:

    * In a terminal, execute start.sh if you are on Linux / OSX.
    * In a terminal, execute start.bat if you are on Windows.
    * In an IDE, run the `Main` class as a "Java Application."
    * To validate your project, you can:
        * Run unit tests with `mvn test`.
        * Run all tests (unit and integration) with `mvn integration-test`.
        * Run all checks (tests, dependency-check, etc...) and produce an artifact for your
          application (located in the target/application.jar) with `mvn verify`, which you can
          invoke directly with `java -jar
          target/application.jar`.
        * Run Prettier with `mvn prettier:write`.
