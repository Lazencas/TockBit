# Start with a base image containing Java runtime
FROM adoptopenjdk:21-jdk-hotspot

# Add Maintainer Info
LABEL maintainer="dkfkskgks@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=target/my-application.jar

# Add the application's jar to the container
ADD ${JAR_FILE} my-application.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/my-application.jar"]
