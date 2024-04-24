FROM openjdk:17-slim as build

LABEL maintainer="chinmay_venkat"

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

#unpackage jar file
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

#stage 2
FROM openjdk:17-slim

#Add volume pointing to tmp
VOLUME /tmp

ARG DEPENDENCY=/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

#execute the application
ENTRYPOINT ["java","-cp", "app:app/lib/*", "com.chinmay.licensing.LicensingServiceApplication"]




