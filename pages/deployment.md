---
id: deployment
---

# Deployment [Lachenicht]

The FinanceTracker application can be deployed locally using Docker.
For each microservice, a Dockerfile exists for isolated containerization.
The individual microservice containers are merged into an application container using a [docker-compose.yml](../docker-compose.yml) file.
Here, specific ports and dependencies between the microservices are defined.

How to deploy and access the FinanceTracker application locally:

1. Navigate to folder `financetracker`
2. Run the commands below
3. Open your browser
4. Open `localhost:4200` to access the UI

```
mvn clean package --file implementation/bankaccount/pom.xml
mvn clean package --file implementation/transaction/pom.xml
mvn clean package --file implementation/product/pom.xml
mvn clean package --file implementation/savingsgoal/pom.xml
mvn clean package --file implementation/analytics/pom.xml
docker-compose up -d --build
```
