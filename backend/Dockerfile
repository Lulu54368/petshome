FROM openjdk:17
LABEL maintainer="pet's home"
WORKDIR /backend

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY  lombok-1.18.24.jar ./


RUN  ./mvnw install:install-file  \
     -DgroupId=org.project-lombok  \
     -DartifactId=lombok -Dversion=1.18.24  \
     -Dpackaging=jar  \
     -Dfile=lombok-1.18.24.jar
RUN ./mvnw dependency:resolve
COPY wait-for-it.sh ./
RUN chmod +x ./wait-for-it.sh
COPY src ./src
CMD ["./mvnw",  "spring-boot:run"]