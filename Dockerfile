FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/springboot-aws-exe.jar springboot-aws-exe.jar
EXPOSE 8080
CMD ["java", "-jar", "springboot-aws-exe.jar"]