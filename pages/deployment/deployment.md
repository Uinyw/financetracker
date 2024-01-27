# Deployment

1. Open your terminal
2. Clone the `financetracker` project
3. Navigate to folder `financetracker`
4. Run the following commands
```
mvn clean package --file implementation/bankaccount/pom.xml
mvn clean package --file implementation/transaction/pom.xml
docker-compose up -d
```
