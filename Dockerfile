FROM openjdk:11
ARG JAR_FILE=target/*.jar
ARG JSON_FILE=target/mockedForecasts.json
COPY ${JAR_FILE} app.jar
COPY ${JSON_FILE} mockedForecasts.json
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080:8080