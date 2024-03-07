@echo off
cd path\to\your\project\root

mvn clean package --file ./implementation/bankaccount/pom.xml
mvn clean package --file ./implementation/transaction/pom.xml
mvn clean package --file ./implementation/product/pom.xml
mvn clean package --file ./implementation/savingsgoal/pom.xml
mvn clean package --file ./implementation/analytics/pom.xml

docker-compose up -d
exit