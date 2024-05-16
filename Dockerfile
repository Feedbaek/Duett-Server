FROM openjdk:17

COPY ./build/libs/Duett-0.0.1-SNAPSHOT.jar /Duett-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/Duett-0.0.1-SNAPSHOT.jar"]
