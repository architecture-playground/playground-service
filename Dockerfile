FROM adoptopenjdk/openjdk13:x86_64-alpine-jdk-13.0.2_8-slim as build

RUN echo "Install Gradle" && \
    apk add --no-cache wget && \
    wget -nv https://services.gradle.org/distributions/gradle-6.3-bin.zip && \
    mkdir /opt/gradle && \
    unzip -d /opt/gradle gradle-6.3-bin.zip && \
    rm -rf gradle-6.3-bin.zip

ENV GRADLE_HOME=/opt/gradle/gradle-6.3
ENV PATH=$PATH:$GRADLE_HOME/bin

COPY . /playground-service
WORKDIR /playground-service

RUN echo "Gradle clean build started" && \
    # build jar but don't execute tests since "docker in docker" issue
    gradle clean build -x test && \
    echo "Gradle clean build finished" && \
    mkdir /app && \
    mv ./build/libs/*.jar /app/ && \
    echo "Application jar moved to /app directory"

# Postpone executing tests when container started
CMD gradle test

FROM adoptopenjdk/openjdk13:x86_64-alpine-jdk-13.0.2_8-slim as app
COPY --from=build /app/ .
ENTRYPOINT java -jar /app/*.jar
