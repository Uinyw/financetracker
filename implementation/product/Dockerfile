FROM eclipse-temurin:17-jdk-focal
ADD target/*.jar product.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar", "product.jar"]
