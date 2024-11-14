FROM openjdk:17-oracle

ENV SPRING_PROFILES_ACTIVE=aws

WORKDIR /app

COPY build/libs/Quokka-travel-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]