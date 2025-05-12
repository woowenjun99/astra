FROM openjdk:17
ARG JAR_FILE=./astra_app/target/astra_app-0.0.1-SNAPSHOT.jar
COPY ./astra_app/target/astra_app-0.0.1-SNAPSHOT.jar ./astra_app/target/astra_app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./astra_app/target/astra_app-0.0.1-SNAPSHOT.jar"]