FROM maven:3.8.4-openjdk-17-slim AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests=true
FROM openjdk:17 AS runtime
COPY --from=build /target/customer-subscription-service-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "customer-subscription-service-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=TAG"]
