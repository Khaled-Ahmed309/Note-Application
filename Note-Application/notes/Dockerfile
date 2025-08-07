FROM maven:3.9.6-eclipse-temurin-22 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


#Use a base image with java jdk 22
FROM openjdk:22-jdk

WORKDIR /app
# Copy the JAR file from your local machine to the container
COPY --from=build /app/target/note-app.jar note-app.jar

# Expose port the app runs on inside the container

EXPOSE 8080
# Command to run the application

ENTRYPOINT ["java","-jar", "note-app.jar"]