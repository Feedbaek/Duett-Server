FROM openjdk:17

COPY ./build/libs/MyMatching-0.0.1-SNAPSHOT.jar /MyMatching-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/MyMatching-0.0.1-SNAPSHOT.jar"]
