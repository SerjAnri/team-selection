FROM openjdk:17-jdk-alpine
COPY build/libs/team-selection.jar team-selection.jar
ENTRYPOINT ["java","-jar","/team-selection.jar"]
