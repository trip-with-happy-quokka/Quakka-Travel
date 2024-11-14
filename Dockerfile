FROM openjdk:17-oracle
WORKDIR /app
# Database configuration
ARG AWS_DB_URL
ARG AWS_DB_NAME
ARG AWS_DB_USERNAME
ARG AWS_DB_PW
ARG AWS_DB_PW_S
# Elasticsearch configuration
ARG AWS_ELASTIC_SEARCH_ID
ARG AWS_ELK_URL
ARG AWS_ELASTIC_SEARCH_PW
# Redis configuration
ARG AWS_REDIS_URL
# Mail configuration
ARG MAIL_USERNAME
ARG APP_PASSWORD
# JWT and Slack configurations
ARG JWT_SECRET_KEY
ARG SLACK_WEBHOOK_URL
# Payment configuration
ARG TOSS_PAYMENT_CLIENT_KEY
ARG TOSS_PAYMENT_SECRET_KEY
ARG TOSS_PAYMENT_SUCCESS_URL
ARG TOSS_PAYMENT_FAIL_URL
# AWS S3 configuration
ARG AWS_S3_BUCKET
# Set environment variables for the application
ENV AWS_DB_URL=$AWS_DB_URL \
    AWS_DB_NAME=$AWS_DB_NAME \
    AWS_DB_USERNAME=$AWS_DB_USERNAME \
    AWS_DB_PW=$AWS_DB_PW \
    AWS_DB_PW_S=$AWS_DB_PW_S \
    AWS_ELASTIC_SEARCH_ID=$AWS_ELASTIC_SEARCH_ID \
    AWS_ELK_URL=$AWS_ELK_URL \
    AWS_ELASTIC_SEARCH_PW=$AWS_ELASTIC_SEARCH_PW \
    AWS_REDIS_URL=$AWS_REDIS_URL \
    MAIL_USERNAME=$MAIL_USERNAME \
    APP_PASSWORD=$APP_PASSWORD \
    JWT_SECRET_KEY=$JWT_SECRET_KEY \
    SLACK_WEBHOOK_URL=$SLACK_WEBHOOK_URL \
    TOSS_PAYMENT_CLIENT_KEY=$TOSS_PAYMENT_CLIENT_KEY \
    TOSS_PAYMENT_SECRET_KEY=$TOSS_PAYMENT_SECRET_KEY \
    TOSS_PAYMENT_SUCCESS_URL=$TOSS_PAYMENT_SUCCESS_URL \
    TOSS_PAYMENT_FAIL_URL=$TOSS_PAYMENT_FAIL_URL \
    AWS_S3_BUCKET=$AWS_S3_BUCKET
# Copy the application JAR file into the container
COPY build/libs/Quokka-travel-0.0.1-SNAPSHOT.jar app.jar
# Expose the application's port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]