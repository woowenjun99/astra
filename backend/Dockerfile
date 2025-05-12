# ---------- Stage 1: Build ----------
FROM maven:3.9.9 AS build
WORKDIR /app
COPY . .
RUN mvn -B package --file pom.xml -Dmybatis.generator.skip=true

# ---------- Stage 2: Runtime ----------
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/astra_app/target/astra_app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app"]