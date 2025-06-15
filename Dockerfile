FROM openjdk:11-jre-slim
ARG JAR_FILE=target/employee.jar
COPY  ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
