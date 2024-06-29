FROM openjdk:11.0.11-jre-slim

COPY target/greeting-service-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]

OTEL_LOGS_EXPORTER=otlp OTEL_EXPORTER_OTLP_ENDPOINT="" OTEL_RESOURCE_ATTRIBUTES=service.name=GreetingApp java -javaagent:/path/opentelemetry-javaagent.jar -jar  <myapp>.jar

