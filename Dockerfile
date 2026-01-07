# -------- BUILD STAGE --------
# Uses Maven to compile the code and create the JAR file
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
# Download dependencies first to speed up future builds
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# -------- RUN STAGE --------
# Uses a lightweight Java Runtime to run the application
FROM eclipse-temurin:17-jdk

WORKDIR /app
# Copies the JAR file from the build stage to the run stage
COPY --from=build /app/target/*.jar app.jar

# Port 8080 is the default for Spring Boot
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]