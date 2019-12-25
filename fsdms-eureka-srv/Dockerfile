FROM openjdk:8u232-jre-slim
LABEL maintainer Jon Q
WORKDIR /fsdms
VOLUME /fsdms-data
ENV PROJECT_ARTIFACTID="fsdms-eureka-srv" PROJECT_VERSION="0.0.1-SNAPSHOT"
EXPOSE 8761
COPY target/$PROJECT_ARTIFACTID-$PROJECT_VERSION.jar /fsdms/app.jar
ENTRYPOINT ["java", "-jar", "/fsdms/app.jar"]