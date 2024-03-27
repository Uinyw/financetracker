---
id: deployment
---

# Deployment [Lachenicht]


Die FinanceTracker application lässt sich mithilfe von Docker lokal deployen.
Hierzu existiert für jeden microservice ein Dockerfile, zur isolierten containiriersung.

Der Zusammenschluss der einzelnen Microserservice-Container in einen applications Container erfolgt mithilfe einer docker-compose.yml Datei. Hier werden konkrete Ports und Abhängigkeiten zwischen den microservice definiert.


How to deploy and access the FinanceTracker application locally:

1. Navigate to folder `financetracker`
2. Run the following commands

```
mvn clean package --file implementation/bankaccount/pom.xml
mvn clean package --file implementation/transaction/pom.xml
mvn clean package --file implementation/product/pom.xml
mvn clean package --file implementation/savingsgoal/pom.xml
mvn clean package --file implementation/analytics/pom.xml
docker-compose up -d --build
```

3. Open your browser
4. Open `localhost:4200` to access the UI
