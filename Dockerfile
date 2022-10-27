FROM openjdk:17-jdk-alpine

ARG EXTRACTED=target/extracted

ENV PROFILE=test
ENV DB_HOST=localhost
ENV DB_NAME=subflow
ENV DB_PORT=3306
ENV DB_USER=root
ENV DB_PASSWORD=root
ENV SUBFLOW_PORT=8081

COPY ${EXTRACTED}/dependencies/ ./
COPY ${EXTRACTED}/spring-boot-loader/ ./
COPY ${EXTRACTED}/snapshot-dependencies/ ./
COPY ${EXTRACTED}/application/ ./
EXPOSE ${SUBFLOW_PORT}

HEALTHCHECK --interval=1m --timeout=5s \
  CMD wget --no-verbose --tries=1 --spider \
           http://localhost:${SUBFLOW_PORT}/health/check \
           || exit 1

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher", "--spring.profiles.active=${PROFILE}"]