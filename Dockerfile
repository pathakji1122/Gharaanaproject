

# Use the OpenJDK 8 image for the final stage
FROM openjdk:17
EXPOSE 8080
ADD target/gharaana-release.jar gharaana-release.jar

ENTRYPOINT ["java","-jar","gharaana-release.jar"]
# Set the working directory in the container

