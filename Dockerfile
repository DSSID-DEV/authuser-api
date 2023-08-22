FROM postgres:latest
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD d3m37r10

ENV LANG pt_BR.utf8
ENV LC_COLLATE pt_BR.utf8
ENV LC_TYPE pt_BR.utf8

COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432

CMD ["postgres"]

ENTRYPOINT ["top", "-b"]

FROM openjdk:11-jre-slim

ENV LANG=pt.BR.UTF-8 LANGUAGE=pt_BR:pt LC_ALL=pt.BR.UTF-8

ENV USER=spring
ENV UID=10000
ENV GID=10000

RUN addgroup --system spring
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "$(pwd)" \
    --ingroup "$USER" \
    --no-create-home \
    --uid "$UID" \
    "$USER"


USER spring:spring

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /authuser-api.jar

USER root

RUN mkdir -p /logs

USER spring

ENTRYPOINT ["java", "-jar", "authuser-api.jar", "-Duser.language=pt"]