FROM eclipse-temurin:17-alpine
ENV TZ=Europe/Warsaw
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]