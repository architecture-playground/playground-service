FROM adoptopenjdk/openjdk13:x86_64-alpine-jdk-13.0.2_8-slim

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
    echo "Gradle clean build finished"

COPY ./build/libs/*.jar /app/app.jar

EXPOSE 8500
EXPOSE 5432
EXPOSE 7999

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
