FROM eclipse-temurin:17-jdk-focal
ADD target/*.jar analytics.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar", "analytics.jar"]
