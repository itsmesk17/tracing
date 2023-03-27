FROM adoptopenjdk/openjdk11:alpine
COPY target/log4j-tracing-1.0-SNAPSHOT.jar log4j-tracing.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/log4j-tracing.jar"]