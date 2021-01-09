FROM openjdk:15
ADD target/bayztracker.jar bayztracker-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bayztracker-app.jar"]