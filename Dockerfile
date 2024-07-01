FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} crm-application.jar
ENV PROFILE=container
ENTRYPOINT ["java", "-jar", "crm-application.jar", "--spring.profiles.active=${PROFILE}"]