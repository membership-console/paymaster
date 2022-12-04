FROM openjdk:17-jdk-slim-bullseye as build-stage

WORKDIR /app
COPY . /app/

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim-bullseye

COPY --from=build-stage /app/build/libs/*-all.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
