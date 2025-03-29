# automation-exercise-test

## Description

**automation-exercise-test** is a test automation project using selenium and Rest Assured for web application and API testing. The project is designed to be modular and customizable, allowing easy adaptation to different project requirements. It includes features such as parallel execution, data-driven testing, cross-browser testing, CI/CD integration, and custom reporting.

## Repository Information

- **Owner:** [Ashraaf7](https://github.com/Ashraaf7)
- **Repository URL:** [automation-exercise-test](https://github.com/Ashraaf7/automation-exercise-test.git)
- **Primary Language:** Java

## 🚀 Features  

- **Web Application Testing:** Utilize Selenium for robust and reliable browser automation.  
- **API Testing:** Leverage Rest Assured for seamless API testing with detailed assertions.   
- **Customizable Framework:** Modular design allows easy adaptation to different project requirements.  
- **Parallel Execution:** Speed up test execution with multi-threading support.  
- **Capture screenshots and video recordings** of test executions for better debugging.
- **Page Object Model (POM):** Implement the POM design pattern for better maintainability and readability.
- **Design Patterns:** Utilize design patterns like Singleton, Factory, and Builder for better code organization.
- **Data-Driven Testing:** Support for data-driven testing using JSON and Excel files.
- **Cross-Browser Testing:** Test across multiple browsers and platforms with ease.
- **CI/CD Integration:** Seamless integration with CI/CD tools like GitHub Actions for automated testing and deployment.
- **Custom Assertions:** Implement custom assertions for specific validation needs.
- **Soft Assertions:** Support for soft assertions to continue test execution even when some assertions fail.
- **Custom Waits:** Implement custom wait strategies for better synchronization.
- **Custom Listeners:** Implement custom listeners for enhanced reporting and logging.
- **Custom Test Environment:** Support for multiple test environments (e.g., dev, staging, production) with environment-specific configurations.
- **Custom Test Reporting:** Generate custom test reports with detailed execution insights.
- **Custom Test Logging:** Implement custom logging strategies for better debugging and analysis.
- **Custom Test Utilities:** Implement custom utility classes for common tasks (e.g., file handling, JSON parsing, etc.).
- **Custom Test Framework:** Build a custom test framework with reusable components and utilities.


## 🛠️ Tools & Technologies  

- **Selenium:** Browser automation for web application testing.  
- **JUnit:** Test case structuring and execution.  
- **Rest Assured:** API testing with simple and powerful HTTP request validation.  
- **Maven/Gradle:** Dependency management and build automation.  
- **Log4j:** Centralized logging for better debugging and analysis.  
- **Allure Reports:** Rich HTML reports with execution insights.
- **Faker:** Generate fake data for testing purposes.
- **Apache POI:** Read and write Excel files for data-driven testing.
- **JSON:** Data interchange format for API testing and configuration.
- **GitHub Actions:** CI/CD integration for automated testing and deployment.


  

### Prerequisites

- Java Development Kit (JDK) installed
- IDE (eg: IntelliJ IDEA, Eclipse)
- Maven or Gradle installed


### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Ashraaf7/automation-exercise-test.git
   ```
2. Navigate to the project directory:
   ```sh
   cd automation-exercise-test
   ```
3. Install dependencies:
  **If using Maven:**
  ```bash
  mvn clean install  
  ```
  **If using Gradle:**
  ```bash
  gradle build  
  ```

### Run the tests:
  **Execute all tests:**
   ```bash
  mvn test
  ```
  **Run specific test classes or methods:**
  ```bash
  mvn -Dtest=TestClassName test 
  ```
   
```bash
## 📄 Project Structure
automation-exercise-test/  
├── .github
    ├── dependabot.yml
    └── workflows
    │   └── E2E Regression Testing.yml
├── .gitignore
├── README.md
├── pom.xml
└── src
    ├── main
        ├── java
        │   └── com
        │   │   └── automationexercise
        │   │       ├── drivers
        │   │           ├── AbstractDriver.java
        │   │           ├── ChromeFactory.java
        │   │           ├── EdgeFactory.java
        │   │           ├── FirefoxFactory.java
        │   │           ├── GUIDriver.java
        │   │           ├── SafariFactory.java
        │   │           └── WebDriverOptionsAbstract.java
        │   │       ├── listeners
        │   │           └── TestNGListeners.java
        │   │       └── utils
        │   │           ├── AlertUtils.java
        │   │           ├── AllureUtils.java
        │   │           ├── BrowserActions.java
        │   │           ├── ConfigUtils.java
        │   │           ├── ElementActions.java
        │   │           ├── ExcelUtils.java
        │   │           ├── FakerUtils.java
        │   │           ├── FilesUtils.java
        │   │           ├── FrameActions.java
        │   │           ├── JsonUtils.java
        │   │           ├── LogUtils.java
        │   │           ├── PropertiesUtils.java
        │   │           ├── RandomDataGenerator.java
        │   │           ├── ScreenRecorderUtils.java
        │   │           ├── ScreenshotUtils.java
        │   │           ├── SoftAssertionUtils.java
        │   │           ├── TerminalUtils.java
        │   │           ├── TimeUtils.java
        │   │           ├── Validations.java
        │   │           └── Waits.java
        └── resources
        │   ├── allure.properties
        │   ├── allure
        │       └── .gitkeep
        │   ├── database.properties
        │   ├── environment.properties
        │   ├── log4j2.properties
        │   ├── seleniumGrid.properties
        │   ├── video.properties
        │   ├── waits.properties
        │   └── webApp.properties
    └── test
        ├── java
            └── com
            │   └── automationexercise
            │       └── tests
            │           ├── BaseTest.java
            │           └── E2e.java
        ├── resources
            └── test-data
            │   ├── .gitkeep
            │   └── test-data.json
        └── suites
            ├── .gitkeep
            └── RegressionSuite.xml
```


## Contributing

Contributions are welcome! Please fork the repository and create a pull request.

## License

This project is licensed under the MIT License.

## Contact

For questions or support, feel free to reach out to Ahmed Ashraf:
Email: [ahmedashraaf09@gmail.com]
GitHub: [Ashraaf7](https://github.com/Ashraaf7)



