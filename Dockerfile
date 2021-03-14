FROM openjdk
VOLUME /tmp
ARG JAR_FILE
EXPOSE 8089
COPY target/restaurant.jar restaurant.jar
ENTRYPOINT ["java","-jar","restaurant.jar"]