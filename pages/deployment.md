---
id: deployment
---

# Deployment [Lachenicht]

## Installation using a terminal

1. Open your terminal
3. Clone the `financetracker` project
4. Navigate to folder `financetracker`
5. Run the following commands

```
mvn clean package --file implementation/bankaccount/pom.xml
mvn clean package --file implementation/transaction/pom.xml
mvn clean package --file implementation/product/pom.xml
mvn clean package --file implementation/savingsgoal/pom.xml
mvn clean package --file implementation/analytics/pom.xml
docker-compose up -d --build
```

5. Open your browser
6. Open `localhost:4200` to access the UI

## Installation using a installer Script

run our [installer script](../../installer.bat)
