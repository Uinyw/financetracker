---
id: implementation
---

# Implementation [Lachenicht, Kässmann]

The implementation of the microservices can be found in the folder `implementation`.

## General Package Structure
The package structure for each microservice is as follows:

- 📁 **api**
  - 📁 **mapping**
  - 📄 Resource.java
- 📁 **infrastructure**
  - 📁 client
  - 📁 db
  - 📁 kafka
- 📁 **logic**
  - 📁 **model**
  - 📁 **operations**
- 📁 **resources**
  - 📁 api
    - 📄 API-Specification.yaml


## How to Run
How to run a microservice, for example BankAccount:

1. Navigate to folder `financetracker`
2. Run the following command in order to generate the API interface and models in the `target` folder

```
mvn clean package --file implementation/bankaccount/pom.xml
```

3. Run the microservice in your IDE





