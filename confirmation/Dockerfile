FROM gradle:jdk11 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle -x test build --no-daemon

FROM adoptopenjdk/openjdk11 AS confirmation-service
# copy JAR into image
COPY --from=build /home/gradle/src/build/libs/confirmation-*-SNAPSHOT.jar /confirmation.jar
# expose port
EXPOSE 8081
# run application with this command line
CMD ["java", "-jar", "/confirmation.jar"]
