FROM openjdk:8-jdk-alpine

WORKDIR /app

ADD target/registration-with-email-1.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/registration-with-email-1.0-SNAPSHOT.jar","--spring.config.location=classpath:/application-docker.yml"]