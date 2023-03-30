FROM openjdk:11.0.11-jre-slim

COPY target/shipment-service-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app.jar"]