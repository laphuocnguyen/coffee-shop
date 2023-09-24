FROM openjdk:11-jdk-slim-buster

ENV TZ Asia/Bangkok

COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]