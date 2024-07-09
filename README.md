# Truck Ticket App

## Description
Truck Ticket App is app that records truck’s inbound and outbound weight through weighbridge. <br><br>
(Note: For now, because of the **tight deadline**. The focus of this project is the overall **Technical Design**. So the **UI design** only shows the content, not a beautiful **UI design**.)

## How to run
- Make sure you already set `$JAVA_HOME` with JDK **17.x.x** version to be able run the `./gradlew` command. (you can check by running `echo $JAVA_HOME` in your terminal, if it's empty please search through the internet "How to setup JAVA_HOME in Mac/Windows/Ubuntu")
- You can run `./gradlew test` to **only** run unit tests and generate the coverage report that will generated in the `/build/reports/jacoco/testDebugUnitTestCoverage/` in each module. (except `core` and `app`)
- Or also you can tun `./gradlew build` that will build the project alongside with the `./gradlew test` in the end.

## Technical
- Multi module Clean Architecture
- MVVM Design pattern
- Single Activity
- Jetpack Compose Declarative UI
- Main Library (see detail: [libs.versions.toml](gradle%2Flibs.versions.toml)) :
  - Concurrency: Kotlin Coroutine
  - Network database: Firebase Firestore
  - Local database: Room
  - Dependency injection: Dagger Hilt
  - Unit Testing: JUnit
  - Test Double: Mockito

## Demo
https://github.com/denisyordanp/TruckTicketApp/assets/48426396/4a2afa96-8a73-4c57-8fa8-4095da2d6f29

## Unit Test Coverage
**Note**: If you want to explore the coverage, you can download the folder for each module in the given link below then open the _index.html_ in the browser.

| Module | Screenshot                                                                                                      | Link                               |
|--------|-----------------------------------------------------------------------------------------------------------------|------------------------------------|
| Schema | ![Screenshot 2024-07-09 at 07.10.57.png](unit_test_html%2Fschema%2FScreenshot%202024-07-09%20at%2007.10.57.png) | [schema](unit_test_html%2Fschema)  |
| Domain | ![img.png](unit_test_html%2Fdomain%2Fimg.png)                                                                   | [domain](unit_test_html%2Fdomain)  |
| Data   | ![img.png](unit_test_html%2Fdata%2Fimg.png)                                                                     | [data](unit_test_html%2Fdata)      |
| Common | ![img.png](unit_test_html%2Fcommon%2Fimg.png)                                                                   | [common](unit_test_html%2Fcommon)  |

## Checks
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=denisyordanp_TruckTicketApp&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=denisyordanp_TruckTicketApp)
