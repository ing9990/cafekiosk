FROM openjdk:11
WORKDIR /app
COPY . /app
RUN ./gradlew clean build
CMD ["./gradlew", "bootRun"]