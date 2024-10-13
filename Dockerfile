# jdk17 Image Start
FROM openjdk:17

# jar 파일 복제
ARG JAR_FILE=build/libs/AuctionSeats-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Redis 서버와 Java 애플리케이션 실행
CMD sh -c "java -jar -Dspring.profiles.active=prod app.jar"