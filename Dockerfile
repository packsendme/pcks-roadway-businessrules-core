
FROM openjdk:8-jdk-alpine
EXPOSE 9018
COPY /target/pcks-roadbrewa-roadway-core-0.0.1-SNAPSHOT.jar pcks-roadbrewa-roadway-core-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/pcks-roadbrewa-roadway-core-0.0.1-SNAPSHOT.jar"]