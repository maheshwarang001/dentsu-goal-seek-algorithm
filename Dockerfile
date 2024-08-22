FROM openjdk:17-jdk-alpine
LABEL authors="maheshwarang"
WORKDIR /app
COPY target/datson_goal_seeker_algo-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar" , "/app.jar"]
