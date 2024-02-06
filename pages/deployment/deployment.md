# Deployment

1. Open your terminal
2. Clone the `financetracker` project
3. Navigate to folder `financetracker`
4. Run the following commands
```
mvn clean package --file implementation/bankaccount/pom.xml
mvn clean package --file implementation/transaction/pom.xml
mvn clean package --file implementation/product/pom.xml
mvn clean package --file implementation/savingsgoal/pom.xml
docker-compose up -d
```
5. Open your browser
6. Open `localhost:4200` to access the UI
