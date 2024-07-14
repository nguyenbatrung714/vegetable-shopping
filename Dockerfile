# Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY /target/vegetable-shopping-0.0.1-SNAPSHOT.jar ./vegetable-shopping-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "vegetable-shopping-0.0.1-SNAPSHOT.jar"]