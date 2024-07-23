FROM maven:3-openjdk-18-slim
EXPOSE 8080
WORKDIR /app
COPY ./ /app
RUN mvn clean package
<<<<<<< HEAD
ENTRYPOINT java -jar /app/target/*.jar
=======
ENTRYPOINT java -jar /app/target/*.jar
>>>>>>> 3d2d35f8facc49518b8e384b6965d542ffb83064
