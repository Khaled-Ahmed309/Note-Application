#Use a base image with java jdk 22
FROM openjdk:22-jdk

# Copy the JAR file from your local machine to the container
COPY target/note-app.jar note-app.jar

# Expose port the app runs on inside the container

EXPOSE 8080
# Command to run the application

ENTRYPOINT ["java","-jar", "note-app.jar"]