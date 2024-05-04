# Start with a base image containing Java runtime
FROM eclipse-temurin:21

# Add Maintainer Info
LABEL maintainer="dkfkskgks@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8084

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build


# The application's jar file
ARG JAR_FILE=build/libs/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
