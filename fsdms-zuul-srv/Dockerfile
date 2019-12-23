FROM openjdk:8u232-jre-slim
LABEL maintainer Jon Q
WORKDIR /fsdms
VOLUME /fsdms-data
ENV PROJECT_ARTIFACTID="fsdms-zuul-srv" PROJECT_VERSION="0.0.1-SNAPSHOT"
COPY target/$PROJECT_ARTIFACTID-$PROJECT_VERSION.jar /fsdms/app.jar
# config in dockerfile-maven-plugin
#ARG JAR_FILE
#COPY $JAR_FILE /fsdms/app.jar
EXPOSE 9049
ENTRYPOINT ["java", "-jar", "/fsdms/app.jar"]
