FROM adoptopenjdk/openjdk11 AS confirmation-service
# copy JAR into image
COPY build/libs/confirmation-*-SNAPSHOT.jar /confirmation.jar
# expose port
EXPOSE 8081
# run application with this command line
CMD ["java", "-jar", "/confirmation.jar"]
