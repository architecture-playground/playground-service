FROM adoptopenjdk/openjdk13:x86_64-alpine-jdk-13.0.2_8-slim

RUN echo "Install Gradle and Docker Client" && \
    apk update && \
    apk add --no-cache wget && \
    wget -nv https://services.gradle.org/distributions/gradle-6.3-bin.zip && \
    mkdir /opt/gradle && \
    unzip -d /opt/gradle gradle-6.3-bin.zip && \
    rm -rf gradle-6.3-bin.zip && \
    apk add docker

ENV GRADLE_HOME=/opt/gradle/gradle-6.3
ENV PATH=$PATH:$GRADLE_HOME/bin

COPY . /playground-service
WORKDIR /playground-service

RUN echo "Gradle clean build started" && \
    gradle clean build && \
    echo "Gradle clean build finished"

CMD tail -f /dev/null
