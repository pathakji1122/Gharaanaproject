# Use the OpenJDK 8 image for the final stage
FROM openjdk:17
EXPOSE 8081
ADD target/gharaana.jar gharaana.jar

ENTRYPOINT ["java","-jar","gharaana.jar"]