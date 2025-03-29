# ASHRAFINIUM

## Description

**ASHRAFINIUM** is a powerful, flexible, and scalable **testing framework** designed to simplify and enhance software testing. It integrates a set of industry-leading tools, including **Selenium**, **JUnit**, and **Rest Assured**, to provide comprehensive support for web, API, and end-to-end testing.  

## Repository Information

- **Owner:** [Ashraaf7](https://github.com/Ashraaf7)
- **Repository URL:** [ASHRAFINIUM](https://github.com/Ashraaf7/ASHRAFINIUM)
- **Primary Language:** Java

## 🚀 Features  

- **Web Application Testing:** Utilize Selenium for robust and reliable browser automation.  
- **API Testing:** Leverage Rest Assured for seamless API testing with detailed assertions.   
- **Customizable Framework:** Modular design allows easy adaptation to different project requirements.  
- **Parallel Execution:** Speed up test execution with multi-threading support.  
- **Comprehensive Reporting:** Generate detailed HTML and console-based test reports.
- **Logging:**
- **Screenshots &Recordings:**

## 🛠️ Tools & Technologies  

- **Selenium:** Browser automation for web application testing.  
- **JUnit:** Test case structuring and execution.  
- **Rest Assured:** API testing with simple and powerful HTTP request validation.  
- **Maven/Gradle:** Dependency management and build automation.  
- **Log4j:** Centralized logging for better debugging and analysis.  
- **Allure Reports:** Rich HTML reports with execution insights.

  
## Getting Started

Instructions on how to get a copy of the project up and running on your local machine.

### Prerequisites

- Java Development Kit (JDK) installed

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Ashraaf7/ASHRAFINIUM.git
   ```
2. Navigate to the project directory:
   ```sh
   cd ASHRAFINIUM
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

### Configure the framework:
Update all properties files under src/main/resources with your project-specific settings (e.g., browser type, base URL, API endpoints,...).

### Run the tests:
  **Execute all tests:**
   ```bash
  mvn test
  ```
  **Run specific test classes or methods:**
  ```bash
  mvn -Dtest=TestClassName test 
  ```
## 📄 Project Structure
ASHRAFINIUM/  
│  
├── src/  
│   ├── main/  
│   │   ├── java/  
│   │   │   ├── apis/          # Base classes and utilities  
│   │   │   ├── drivers/       # driver configuration  
│   │   │   ├── pages/         # Page Object Model implementation  
│   │   │   ├── listeners/     # Test Listeners  
│   │   │   └── utils/         # Helper utilities (e.g., logging, reporting,...)  
│   │   └── resources/         # configs and properties  
│   └── test/                  # Test-specific resources and classes  
│   │   │   java/  
│   │   │   ├── tests/         # Test Cases
│   │   └── resources/         # Test data
│  
├── pom.xml/                   # Maven dependencies  
└── README.md                  # Documentation  

## Contributing

Contributions are welcome! Please fork the repository and create a pull request.

## License

This project is licensed under the MIT License.

## Contact

For questions or support, feel free to reach out to Ahmed Ashraf:
Email: [ahmedashraaf09@gmail.com]
GitHub: [Ashraaf7](https://github.com/Ashraaf7)



