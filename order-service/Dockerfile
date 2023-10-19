FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} app.jar

CMD ["java","-jar","app.jar"]