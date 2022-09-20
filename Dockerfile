FROM adoptopenjdk/openjdk11:alpine

WORKDIR /app

COPY target/clustered-data-warehouse-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/clustered-data-warehouse-0.0.1-SNAPSHOT.jar" ]


