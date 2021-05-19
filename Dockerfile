
FROM maven:3.8.1-openjdk-11 AS BUILD

WORKDIR /app

COPY pom.xml pom.xml
COPY src src

RUN mvn package assembly:single

FROM openjdk:11

COPY --from=BUILD /app/target/kafkaloader-1.0-SNAPSHOT-jar-with-dependencies.jar /app/kafkaloader.jar

ENV INPUT_FOLDER /data/input

CMD ["java", "-jar", "/app/kafkaloader.jar"]