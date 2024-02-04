FROM openjdk:17
COPY target/gharaana.jar gharaana.jar
CMD java -jar gharaana.jar