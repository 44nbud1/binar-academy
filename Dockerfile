FROM bellsoft/liberica-openjdk-alpine-musl
MAINTAINER setiawan.aanbudi
COPY target/student-0.0.1-SNAPSHOT.jar app/student-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","app/student-0.0.1-SNAPSHOT.jar"]