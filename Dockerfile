# jdk17 Image Start
FROM openjdk:17

# jar 파일 복제
ARG JAR_FILE=build/libs/my-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
