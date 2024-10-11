# jdk17 Image Start
FROM openjdk:17

# jar 파일 복제
ARG JAR_FILE=build/libs/AuctionSeats-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Redis 서버와 Java 애플리케이션 실행
CMD sh -c "redis-server /etc/redis/redis.conf & \
            redis-server /etc/redis/redis-6380.conf & \
            redis-server /etc/redis/redis-6381.conf & \
            sleep 5 && \
            java -jar -Dspring.profiles.active=prod app.jar"