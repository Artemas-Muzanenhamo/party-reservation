FROM adoptopenjdk/openjdk11 AS reservation-entry-service
# copy JAR into image
COPY build/libs/reservation-entry-*-SNAPSHOT.jar /reservation-entry.jar
# expose port
EXPOSE 8080
# run application with this command line
CMD ["java", "-jar", "/reservation-entry.jar"]
