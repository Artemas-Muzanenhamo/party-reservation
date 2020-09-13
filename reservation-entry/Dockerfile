FROM gradle:jdk11-slim as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle -x test build --no-daemon

FROM adoptopenjdk/openjdk11 AS reservation-entry-service
# copy JAR into image
COPY --from=build /home/gradle/src/build/libs/reservation-entry-*-SNAPSHOT.jar /reservation-entry.jar
# expose port
EXPOSE 8080
# run application with this command line
CMD ["java", "-jar", "/reservation-entry.jar"]
