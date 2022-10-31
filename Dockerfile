FROM gradle:jdk11-alpine
LABEL maintainer="nkochnev@zuzex.com"

WORKDIR /app

COPY gradle /app/.
COPY src /app/.
COPY build.gradle /app/.
COPY settings.gradle /app/.
COPY gradlew /app/.

RUN ./gradlew installDist \
    && jdeps--print-module-deps  \
    --ignore-missing-deps  \
    --recursive \
    --multi-release 11  \
    --class-path="./spark-edu/build/install/spark-edu/lib/*"  \
    --module-path="./spark-edu/build/install/spark-edu/lib/*"  \
    ./app/build/install/app/lib/app.jar

ENV JAVA_HOME "/jre"
ENV PATH $JAVA_HOME/bin:$PATH

EXPOSE 8080

ENTRYPOINT ["/jre/bin/java", "-jar", "/spark-edu/spark-edu.jar"]