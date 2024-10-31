FROM openjdk:17-oracle

WORKDIR /app

COPY target/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]