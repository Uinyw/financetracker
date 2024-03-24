---
id: microservice-engineering-approach
---

# Microservice Engineering Approach [Lachenicht, Kässmann]

The microservice-based application _financetracker_ was developed in 5 steps:

1. **Requirement Analysis**
   > In the first step, the application requirements are analyzed and specified.
   To achieve this, a **story** is written that describes the general domain of the application using an exemplary scenario.
   The story helps to identify the goals to be achieved and problems to be solved.
   Individual **capabilities** are then derived from the story, defining basic functionalities the system should offer.
   For each of the capabilities, corresponding **use cases** are derived. Use cases specify the functionalities offered to a user by defining user/system interaction flows. They define the specific application requirements.

2. **Domain/Architecture Design**
   > Our goal in this step was to model the application domains and software architecture. The Artifacts of this step are the [Domain Models](./pages/design/) for each domain as well as our [Software Architecture](./pages/design/software_architecture.md).

3. **API Specification**
   > Our APIs for the microservice domains have been specified using OpenAPI specifications and can be found in their respective folders: [Analytics API](./implementation/Analytics/src/main/resources/api/Analytics.yaml), [BankAccount API](./implementation/bankaccount/src/main/resources/api/bankAccount.yaml), [Product API](./implementation/product/src/main/resources/api/product.yaml), [Savings Goal API](./implementation/savingsgoal/src/main/resources/api/savingsGoal.yaml), and the [Transaction API](./implementation/transaction/src/main/resources/api/transaction.yaml).

4. **Implementation & Test**
   > Our Microservices were then implemented based on their domain model and API specifications. Also, tests for each of them are present in their respective test folders. We used Unit, Integration, and E2E Tests to test our microservices.

5. **Deployment**
   > Lastly, our microservices were deployed using Docker Compose using the Docker images created using the Docker files from our services.