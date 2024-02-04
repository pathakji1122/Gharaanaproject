FROM openjdk:11
COPY target/gharaana.jar gharaana.jar
CMD java -jar gharaana.jar