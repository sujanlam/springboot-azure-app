# Add this under build after plugins:
<finalName>springboot-aws-exe</finalName>
=======================================================================================
# Add Docker file for docker image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/springboot-aws-exe.jar springboot-aws-exe.jar
EXPOSE 8080
CMD ["java", "-jar", "springboot-aws-exe.jar"]
=======================================================================================
# Add username and password to Github under Secrets
-> Under Settings: Secrets and Variables-> Actions -> Secrets -> New Repository Secret
-> Add docker_username and docker_password
=======================================================================================
# Add this cicd.yml file
=======================================================================================
name: CICD
#what it will be trigger on? push tp which branch?
on:
  push:
    branches: [main]

jobs:
  build:
    runs-on: [ubuntu-latest]
    steps:
      - name: Checkout source
        uses: actions/checkout@v3
      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '17'
      - name: Build Project
        run: mvn clean install -DskipTests
      - name: Login to Docker Hub
        run: docker login -u ${{secrets.DOCKER_PASSWORD}} -p ${{secrets.DOCKER_USERNAME}}
      - name: Build Docker Image
        run: docker build -t sujann42/springboot-example .
      - name: Publish this image to docker hub
        run: docker push sujann42/springboot-example:latest

  deploy:
    needs: build
    # We need to go to github and get commands under
    # Settings/Runner/New Self Hosted Runner/ Select Linux and run those commands and name it aws-ec2
    # Once runner is ready we the runner name (aws-ec2) will be active (below)
    runs-on: [aws-ec2]
    steps:
      - name: Pull Image from Docker Hub
        run: docker pull sujann42/springboot-example:latest
      - name: Delete Old Container
        run: docker rm -f springboot-example-container
      - name: Run docker container
        run: docker run -d -p 8080:8080 --name springboot-example-container sujann42/springboot-example:latest
================================================================================================================
# Integrate it
Go to Settings -> Actions -> Runners -> New Self-Hosted Runner -> And follow the directions based on OS.
