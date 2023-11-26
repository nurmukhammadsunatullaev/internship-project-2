FROM maven:3.8.4-openjdk-17

WORKDIR /usr/src/app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

CMD ["java", "-jar", "target/project.jar"]