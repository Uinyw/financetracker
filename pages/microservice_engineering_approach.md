---
id: microservice-engineering-approach
---

# Microservice Engineering Approach [Lachenicht, Kässmann]

The development of the microservice-based application FinanceTracker included 5 steps:

**Requirement Analysis**

In the first step, the application requirements are analyzed and specified.
To achieve this, a [story](../pages/analysis/story.md) is written that describes the general domain of the application using an exemplary scenario.
The story helps to identify the goals to be achieved and problems to be solved.
Individual **capabilities** are then derived from the story, defining basic functionalities the system should offer.
From each of the capabilities, corresponding **use cases** are derived. Use cases specify the functionalities offered to a user by defining user/system interaction flows. They define the specific application requirements.

**Domain/Architecture Design**

In the second step, the system is designed.
In this context, **domain models** are created for each application domain (derived from capabilities) according to the principles of domain-driven design.
Moreover, the [software architecture](../pages/design/software_architecture.md) is modeled, which divides the application to develop into logical components.
Each of these components represents a microservice.

**API Specification**

In the third step, the provided **API** is specified for each of the microservices modeled in the software architecture.
This is done using `OpenAPI`, a language for specifying REST APIs.

**Implementation & Test**

In the fourth step, the microservices are **implemented** based on their domain model and API specifications.
For each microservice, unit **tests** and integration tests are written.
End-to-end tests are written to test the interaction between the microservices.

**Deployment**

In the fifth step, the microservices are **deployed** locally using Docker.