FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN mvn clean package

CMD ["java", "-jar", "target/*.jar"]
