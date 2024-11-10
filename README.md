# AuctionSeats

## 소개

암표 문제를 방지하기 위해 양도 불가능하며 어차피 비싸게 살 티켓을 합법적으로 경매로 살 수 있는 티켓 예매 서비스 개발

## 🏗 아키텍쳐
<a href='https://ifh.cc/v-tzhqQp' target='_blank'><img src='https://ifh.cc/g/tzhqQp.jpg' border='0'></a>

## 🏗 ERD
<a href='https://ifh.cc/v-ogAZ7x' target='_blank'><img src='https://ifh.cc/g/ogAZ7x.jpg' border='0'></a>

## 🛠️ 사용 기술


## **핵심구현**

- 공연 카테고리,장소,시간,좌석등을 입력하여 공연 생성 기능
- 공연안에 경매 생성 기능
- 토스 API로 포인트 충전밑 경매 참여가능
- 현재 경매 상황 확인 기능
- 경매가 확정 되거나 좌석을 구매하면 입장표 qr 생성기능
-
### Backend

- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Data Redis
- QueryDsl

- Lombok
- Jwt
- Zxing (QR Code)
- Spring Security
- Spring Validation

### Frontend

- HTML 5
- CSS
- JQuery
- Javascript

- Bootstrap
- sweetalert
- js-cookie
- fullcalendar
- jQuery Seat Charts

### Infrastructure

- EC2
- Application Load Balancer
- S3
- RDS
- Docker
- Elastic Cache for Redis


## 🍀 주요 기술

### **서비스**

- 동시성 제어 - (Unique Index, Distribution Lock)
- Redis 캐시 서버
- Server-Sent-Events
- 토스 결제 API

### 성능 개선

- CI/CD - Github Actions

### 인프라

- CI/CD
  - GitHubActions
  - Code Deploy - Blue Green Deploy
  - ECR
- 분산처리
  - Application Load Balancer
  - Auto Scaling group

