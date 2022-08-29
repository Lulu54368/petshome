FROM openjdk:17
LABEL maintainer="Pet's home"
WORKDIR /

ADD target/petshome-0.0.1-SNAPSHOT.jar petshome.jar
COPY wait-for-it.sh ./

RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["java", "-jar", "petshome.jar"]
EXPOSE 8080