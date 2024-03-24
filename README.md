# FinanceTracker

## Code Coverage

| Component | Coverage |
| :--- | :--- |
| BankAccount | ![coverage](../badges/jacoco-bankaccount.svg) |
| Transaction | ![coverage](../badges/jacoco-transaction.svg) |
| Product | ![coverage](../badges/jacoco-product.svg) |
| SavingsGoal | ![coverage](../badges/jacoco-savingsgoal.svg) |
| Analytics | ![coverage](../badges/jacoco-analytics.svg) |


## Analysis

### Development Steps

We developed the software according to the following steps:

1. **Requirement Analysis**
   > Here we defined and expressed all the requirements for our software. By creating a [user story](./story.md) and then creating [Capabilities](./pages/capabilities/) using the User Stories. Using the Capabilities, we created the [Use Case Descriptions and Use Case Diagrams](./pages/use_cases/).

2. **Domain/Architecture Design**
   > Our goal in this step was to model the application domains and software architecture. The Artifacts of this step are the [Domain Models](./pages/design/) for each domain as well as our [Software Architecture](./pages/design/software_architecture.md).

3. **API Specification**
   > Our APIs for the microservice domains have been specified using OpenAPI specifications and can be found in their respective folders: [Analytics API](./implementation/Analytics/src/main/resources/api/Analytics.yaml), [BankAccount API](./implementation/bankaccount/src/main/resources/api/bankAccount.yaml), [Product API](./implementation/product/src/main/resources/api/product.yaml), [Savings Goal API](./implementation/savingsgoal/src/main/resources/api/savingsGoal.yaml), and the [Transaction API](./implementation/transaction/src/main/resources/api/transaction.yaml).

4. **Implementation & Test**
   > Our Microservices were then implemented based on their domain model and API specifications. Also, tests for each of them are present in their respective test folders. We used Unit, Integration, and E2E Tests to test our microservices.

5. **Deployment**
   > Lastly, our microservices were deployed using Docker Compose using the Docker images created using the Docker files from our services.


[Capability Overview](./pages/capabilities/capabilities.md)

[Capability Management of Bank Accounts [Lachenicht]](./pages/capabilities/capability_management_of_bank_accounts.md)

[Capability Management of Transactions [Lachenicht]](./pages/capabilities/capability_management_of_transactions.md)

[Capability Management of Purchases [Lachenicht]](./pages/capabilities/capability_management_of_purchases.md)

[Capability Management of Savings Goals [Kässmann]](./pages/capabilities/capability_management_of_savings_goals.md)

[Capability Analytics [Kässmann]](./pages/capabilities/capability_analytics.md)


### Functional Requirements

#### Management of Bank Accounts [Lachenicht]

[Use Case Diagram Bank Account](./use_cases/use_case_diagram_management_of_bank_accounts.md)

[Use Case Description Bank Account](./use_cases/use_case_descriptions_management_of_bank_accounts.md)

#### Management of Transactions [Lachenicht]

[Use Case Diagram Transaction](./use_cases/use_case_diagram_management_of_transactions.md)

[Use Case Description Transaction](./use_cases/use_case_descriptions_management_of_transactions.md)

#### Management of Products

[Use Case Diagram Product](./use_cases/use_case_diagram_management_of_products.md)

[Use Case Descriptions](./pages/use_cases/use_case_descriptions_management_of_purchases.md)

#### Management of Savings Goals [Kässmann]

[Use Case Diagram Savings Goal](./use_cases/use_case_diagram_management_of_savings_goals.md)

[Use Case Description Savings Goal](./use_cases/use_case_descriptions_management_of_savings_goals.md)

#### Analytics [Kässmann]

[Use Case Diagram Analytics](./pages/use_cases/use_case_diagram_management_of_analytics.md)

[Use Case Description Analytics](./use_cases/use_case_diagram_management_of_analytics.md)

## Architectural Design

[Software Architecture](./pages/design/software_architecture.md)

[Domain Analytics](./design/domain_analytics.md)
[Domain Bank Account](./design/domain_bank_account.md)
[Domain Savings Goal](./design/domain_savings_goal.md)
[Domain Transaction](./design/domain_transaction.md)
[TODO: Domain Product]

## Local Deployment

[Deployment Steps](./pages/deployment/deployment.md)

## General Package Structure

- 📁 **Financetracker**
  - 📁 **figures**
    - 📄 figure.svg
    - 📄 figure.png
    - 📄 figure.pdf
    - 📁 implementation
      - 📁 ___service_name___
        - 📁 src
          - 📁 main
            - 📁 java.com.___service_name___
              - 📁 **api**
                - 📁 **mapping**
                  - 📄 DTO_mapper.java
                  - ...
                - 📄 Resource.java
                - ...
              - 📁 **infrastructure**
                - 📁 client
                  - Folders for the clients
                - 📁 config
                  - 📄 configuration.java
                - 📁 **db**
                  - 📄 Repository_file.java
                - 📁 **kafka**
                  - 📄 Consumer.java
              - 📁 **logic**
                - 📁 **model**
                  - 📄 model_class.java
                  - ...
                - 📁 **operations**
                  - 📄 service_class.java
                  - ...
              - 📄 ____service_name____ application.java
            - 📁 resources
              - 📁 api
                - 📄 **API_specification**.yaml
              - 📄 **Application_specification**.yaml
          - 📁 **test**.java.com.financetracker.___service_name___
            - 📁 **api**
            - 📁 **infrastructure**
            - 📁 **logic**
              - 📁 **model**
              - 📁 **operations**
            - 📄 IntegrationTestBase.java
        - 📁 target
              - ... generated files
          - 📄 Dockerfile
          - 📄 pom.xml
  - 📁 pages
    - 📁 **capabilities**
      - 📄 capabilitiy.md
      - ...
    - 📁 **deployment**
      - 📄 deployment.md
    - 📁 **design**
      - 📄 domain_design.md
      - ...
    - 📁 **use_cases**
      - 📄 use_case_description.md
      - 📄 use_case_diagram.md
    - 📄 readme.md
    - 📄 story.md
  - 📄 README.md
  - 📄 docker-compose.yml
  - 📄 LICENSE
