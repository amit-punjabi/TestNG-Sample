
# TestNG Selenium UI tests

This project is created using TestNG and Selenium


## Prerequisites

To run this project, you will need `maven` build automation tool, OpenJDK v11 and Chrome driver. Exact versions used for execution

- Maven version
```bash
Apache Maven 3.8.6
```

- JDK version
```bash
java --version
openjdk 11.0.20 2023-07-18 LTS
```

- According to the chrome version installed locally you need to download the same version of chrome driver from [here](https://chromedriver.chromium.org/downloads). Once installed give the path to the driver inside `src/main/java/com/zooplus/utils/DriverManager` class

```bash
System.setProperty("webdriver.chrome.driver", "<path-to-chromedriver>");
```
## Running the tests

The below command will run all the tests



```bash
  mvn test
```
Once done you will see results in your command line similar to below
```bash
  Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 144.87 sec

Results :

Tests run: 10, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:25 min
[INFO] Finished at: 2023-09-22T22:12:27+02:00
[INFO] ------------------------------------------------------------------------

```