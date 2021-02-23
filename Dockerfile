FROM openjdk:8u131-jre-alpine


COPY target /usr/src/myapp
WORKDIR /usr/src/myapp

EXPOSE 8080

CMD ["java", "-jar", "KMWebsocket-1.0.jar"]

