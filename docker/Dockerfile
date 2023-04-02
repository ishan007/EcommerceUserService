FROM openjdk:17
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=../../target/userservice-1.jar
ADD ${JAR_FILE} userservice.jar
ENTRYPOINT ["java","-jar","/userservice.jar"]