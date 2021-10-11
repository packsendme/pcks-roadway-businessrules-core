
FROM openjdk:8-jdk-alpine
EXPOSE 9018
COPY /target/pcks-roadway-businessrules-core-0.0.1-SNAPSHOT.jar pcks-roadway-businessrules-core-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/pcks-roadway-businessrules-core-0.0.1-SNAPSHOT.jar"]