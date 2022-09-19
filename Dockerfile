FROM adoptopenjdk/openjdk11:alpine

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY $JAR_FILE /app

EXPOSE 8080

CMD ["java", "-jar", "/app/*.jar" ]


