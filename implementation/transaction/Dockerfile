FROM eclipse-temurin:17-jdk-focal
ADD target/*.jar transaction.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "transaction.jar"]
