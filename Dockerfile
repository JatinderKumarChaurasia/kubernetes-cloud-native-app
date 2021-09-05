FROM openjdk:16
ARG JAR_FILE=lib/kubernetes-cloud-native-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080