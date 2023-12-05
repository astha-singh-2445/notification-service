FROM amazoncorretto:17 AS builder
ARG SPRING_MONGO_URL
ARG PUBLIC_KEY
ARG KAFKA_BOOTSTRAP_SERVERS
WORKDIR /app
COPY gradle gradle
COPY src src
COPY gradlew gradlew
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
ENV SPRING_MONGO_URL $SPRING_MONGO_URL
ENV PUBLIC_KEY $PUBLIC_KEY
ENV KAFKA_BOOTSTRAP_SERVERS $KAFKA_BOOTSTRAP_SERVERS
RUN ./gradlew clean assemble

FROM amazoncorretto:17
ARG SPRING_MONGO_URL
ARG PUBLIC_KEY
ARG KAFKA_BOOTSTRAP_SERVERS
WORKDIR /app
COPY --from=builder /app/build/libs/notification-service-0.0.1-SNAPSHOT.jar app.jar
COPY firebase.json firebase.json
ENV SPRING_MONGO_URL $SPRING_MONGO_URL
ENV PUBLIC_KEY $PUBLIC_KEY
ENV KAFKA_BOOTSTRAP_SERVERS $KAFKA_BOOTSTRAP_SERVERS
CMD java -jar app.jar