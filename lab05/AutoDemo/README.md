# Lab05 - Web UI Testing (VVSS)

This project implements Lab05 using:
- Serenity BDD
- Selenium WebDriver
- JUnit
- Maven

Tested application: `https://www.saucedemo.com/`

## Functionalities Covered

1. Login
2. Logout
3. Add product to cart
4. Checkout (open checkout step one)

## Required Tests Implemented

1. Function testing - parametrized valid data (CSV):
   - `ValidLoginDataDrivenStory`
2. Function testing - parametrized invalid data (CSV):
   - `InvalidLoginDataDrivenStory`
3. Scenario-based testing - valid scenario:
   - `SearchByKeywordStory`
   - Flow: login -> add to cart -> open cart -> start checkout -> logout

## Test Data Files

- `src/test/resources/testdata/login_valid.csv`
- `src/test/resources/testdata/login_invalid.csv`

## SDK and Environment

Use Java 8 for this project:
- Project SDK: `corretto-1.8.0_492`
- Maven runner JDK: `corretto-1.8.0_492`

`serenity.properties` uses Chrome with local driver:
- `webdriver.driver=chrome`
- `webdriver.autodownload=false`
- `webdriver.chrome.driver=src/test/resources/windows/chromedriver.exe`

## Run Commands (PowerShell)

From project folder `lab05/AutoDemo`:

```powershell
$env:JAVA_HOME='C:\Users\sorn\.jdks\corretto-1.8.0_492'
$env:Path="$env:JAVA_HOME\bin;$env:Path"
mvn clean verify
```

Generate Serenity report explicitly (optional):

```powershell
mvn serenity:aggregate
```

Open report:
- `target/site/serenity/index.html`

## Lab05 Requirement Check

- Existing web app tested (not eMAG): YES
- 4 functionalities identified: YES
- Serenity + Selenium + JUnit Maven project: YES
- 1 parametrized valid test (CSV/JSON): YES (CSV)
- 1 parametrized invalid test (CSV/JSON): YES (CSV)
- 1 valid scenario-based test: YES
- Assertions after scenario steps: YES
- Tests passed and present in Serenity report: YES
