<div align="center">

# 🟦🟧 QUOKKA TRAVEL 🟧🟦

![스크린샷 2024-11-14 오후 6 59 41](https://github.com/user-attachments/assets/f3e4bc05-86a7-4916-b805-b1bac7a6fcf3)

</div>

사용자 중심의 여행 예약 플랫폼을 구축하여 사용자가 필요한 전국의 숙소를 실시간으로 예약 가능하고 사용후 리뷰를 남길 수 있는 서비스를 제공하는 야놀자, 에어비앤비를 벤치마킹한 여행 플랫폼 서비스입니다.


<br>
<br>

<div align="center">

# 🟦🟧 여행을 더 편리하고 스마트하게! 🟧🟦

![스크린샷 2024-11-14 오후 7 24 54](https://github.com/user-attachments/assets/355f0d16-6132-4aca-a20f-ac800880b114)

</div>

Quokka Travel은 사용자가 손쉽게 여행을 계획하고, 원하는 숙소를 검색 및 예약할 수 있도록 지원하는 여행 플랫폼입니다. 사용자가 설정한 여행지의 예산을 보다 저렴하게 제공할 수 있도록 슬랙 알림을 통해 선착순 쿠폰을 지원하며 사용자가 예약을 완료하는 단계에서 안전하고 편리한 결제 기능을 제공합니다. 또한, 사용자는 숙소를 이용한 후 리뷰를 남기고 평점을 부여할 수 있어, 이러한 리뷰와 평점은 다른 사용자가 숙소를 선택하는 데 중요한 정보를 제공하며, 신뢰성 있는 숙소를 찾는 데 도움을 줍니다.

<br>
<br>

<div align="center">

# 🟦🟧 프로젝트 핵심 목표 🟧🟦

</div>

1. **대규모 트래픽 대응**
    - Redis와 Kafka를 활용한 비동기 처리를 통해 API 요청 200req/sec 이상 처리.
    - 동시성 문제를 해결하며 안정적인 쿠폰 발급 서비스 제공.

2. **성능 최적화**
    - Redis 기반 캐싱으로 실시간 상품 조회 성능을 3배 향상.
    - Redisson을 사용하여 CPU 점유율 50% 감소 및 안정적 데이터 처리 구현.

3. **운영 및 배포 효율화**
    - Docker와 Github Actions를 이용한 CI/CD 파이프라인 구축으로 배포 자동화.
    - Prometheus와 Grafana를 활용한 실시간 모니터링으로 시스템 안정성 확보.

4. **데이터 일관성 및 트랜잭션 관리**
    - Kafka를 이용한 SAGA 패턴으로 분산 트랜잭션 관리.
    - 중복 및 데이터 손실을 방지하는 Kafka Batch Listener 구현.


<div align="center">

<br>
<br>

# 🟦🟧 KEY SUMMARY 🟧🟦

</div>

### 💡 [성능개선] 최저가 상품 조회 성능, Redis 도입으로 3배 이상 향상
<br>
<details>
<summary> 한 줄 요약 </summary> 
  <br>
   - Redis 도입으로 기존 DB 조회보다 **348% 성능 개선**  
   - 대규모 트래픽 환경에서도 안정적인 서비스 유지  

![성능 개선 이미지]
</details>
<br>
<details>
<summary> 도입 배경 </summary> 
   - 상품의 최저가를 제공하기 위해 외부 서버에서 제공하는 타임세일 상품의 할인율과 상품 자체의 할인율을 비교하는 기능이 필요  
</details>
<br>
<details>
<summary> 기술적 선택지 </summary>

1. **DB 데이터 적재**
    - 스케줄링 작업으로 짧은 시간 내 대량의 데이터를 수정하는 것은 데이터베이스에 과도한 부하 발생
    - 상품 자체의 할인율과 타임세일 할인율을 분리하여 별도 컬럼 저장 필요

2. **Redis 캐싱**
    - 실시간 최저가 할인율로 최신 정보와 가격 제공
    - TTL 설정으로 타임세일 종료 시 자동 데이터 삭제

**결론:** Redis 도입을 결정하여 성능 및 효율성을 크게 개선
</details>
<br>

### 💡 [트러블 슈팅] LazyConnectionDataSourceProxy - 불필요한 커넥션 점유 해결
<br>
<details>
<summary> 배경 </summary>
   - **스프링 배치 5버전 도입**  
     - 정산은 실시간이 아닌, 이용자가 적은 시간에 일괄 처리하도록 배치 선택  
     - 메인 DB와 배치 메타데이터 DB 분리 필요  
   - **배치 메타데이터 테이블 생성 필수화**  
     - 메타데이터 전용 DB를 나누는 구조로 전환  
   - **멀티 DataSource 구성**  
     - 메인 DataSource와 배치 DataSource로 데이터베이스 모듈 구분  
</details>
<br>
<details>
<summary> 문제 </summary>  
  <br>
  - 실제 DB 요청 없이도 불필요한 커넥션 점유 발생  
  - 스프링은 트랜잭션 진입 시 커넥션 풀에서 커넥션을 점유  
  - 멀티 DataSource로 인해 두 DataSource 모두 커넥션 점유  
</details>

<br>

<details>

  <summary> 해결 방안 </summary>
  <br>
  - **LazyConnectionDataSourceProxy 클래스 사용**  
  - 실제 DB 요청 전까지 커넥션 점유를 지연시키는 프록시 DataSource 활용  

![LazyConnectionDataSourceProxy 이미지]

- 이를 통해 **실제 DB 요청 시에만 커넥션 점유**로 불필요한 리소스 낭비를 해결
</details>

<br>
<br>

<div align="center">

# 🟦🟧 인프라 아키텍처 & 적용 기술 🟧🟦

</div>

<br>

<details>
  <summary> 아키텍처 다이어그램 </summary>
  <br>
  위 아키텍처는 **MSA 기반의 이커머스 서비스** 구조를 나타냅니다.  
  각 모듈은 Redis, Kafka를 통해 통신하며, Docker로 컨테이너화되어 CI/CD를 통해 자동 배포됩니다.
</details>

<br>

<details>
  <summary> 데이터베이스 및 캐싱 </summary>
  <br>
  1. **Redis**  
  - **적용 위치**: 캐시 서버  
  - **사용 이유**: 실시간 상품 할인율과 최저가 조회 성능 향상. TTL 설정으로 타임세일 종료 시 데이터 자동 삭제. 
</details>

<br>

<details>
  <summary> 메시징 시스템 </summary>
  <br>
  1. **Apache Kafka**  
  - **적용 위치**: 서비스 간 비동기 통신  
  - **사용 이유**: 대규모 메시지 처리를 위한 안정적 메시징 큐 구현.  
  - **구체적 역할**: 주문 생성 시 재고 차감, 쿠폰 적용 등 서비스 간 데이터 일관성 보장.
</details>

<br>

<details>
  <summary> 인프라 및 배포 </summary>
  <br>
1. **Docker**  
  - **적용 위치**: 모든 서비스 컨테이너화  
  - **사용 이유**: 환경 이식성과 배포 속도 개선.  

2. **Github Actions**
- **적용 위치**: CI/CD 파이프라인
- **사용 이유**: 자동화된 코드 품질 검사와 배포 구현.

3. **Prometheus & Grafana**
    - **적용 위치**: 실시간 모니터링
    - **사용 이유**: 주요 메트릭(트래픽, CPU 사용량 등) 수집 및 시각화로 장애 발생 시 빠른 대응 가능.
</details>


<br>
<br>

<div align="center">

# 🟦🟧 주요 기능 🟧🟦

</div>

<br>

<details>
  <summary> 쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>

<details>
  <summary> 주문 처리: Saga 패턴 적용 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>

<details>
  <summary> 최저가 조회: Redis 캐싱 활용 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>

<details>
  <summary> 결제 정산: Spring Batch 5 활용 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>
<br>

<div align="center">

# 🟦🟧 기술적 고도화 🟧🟦

</div>

<br>

<details>
  <summary> 성능 개선 과정 작성 프레임워크 </summary>
  <br>
[내가 구현한 기능]

[주요 로직]

[배경]

[요구사항]

[선택지]

[의사결정/사유]

</details>

<br>

<details>
  <summary> 문제해결 과정 작성 프레임워크 </summary>
  <br>
[성능 개선 / 코드 개선 요약]

[문제 정의]

[가설]

[해결 방안]
- 문제 해결을 위한 의사결정 과정
- 해결 과정

[해결 완료]
- 결과
- 전후 데이터 비교

[회고]
- 기술의 장단점
- 다시 시도한다면?
</details>

<br>
<br>

<div align="center">

# 🟦🟧 역할 분담 및 협업 방식 🟧🟦

</div>

<details>
  <summary> 역할 </summary>
  <br>
| 이름   | 포지션   | 담당(개인별 기여점)                                                                                                            | Github 링크                       |
|--------|----------|-----------------------------------------------------------------------------------------------------------------------------|-----------------------------------|
| 김ㅇㅇ | 리더     | ▶ **결제**: 토스페이먼츠 PG 연동, 간편결제 구현<br>▶ **정산**: 스프링 배치 활용 (Jpa → Jdbc 변경하여 4.6배 개선)<br>▶ **배포**: 멀티모듈 설정, 멀티 데이터소스 설정, Graceful Shutdown 구현, CI/CD 적용 | [🍁 깃헙링크] |
| 조ㅇㅇ | 부리더   | ▶ **쿠폰**: Redis Lua Script를 통한 동시성 제어 및 대규모 트래픽 제어<br>▶ **타임세일**: Kafka 비동기 발급 처리, 분산락을 통한 동시성 제어                          | [🍁 깃헙링크]    |
| 박ㅇㅇ | 팀원     | ▶ **주문**: MSA 기반 주문 로직 구현, Kafka 기반 SAGA 패턴 적용<br>▶ **스케줄러**: 결제 단계 주문 자동 삭제                                                | [🍁 깃헙링크]     |
| 정ㅇㅇ | 팀원     | ▶ **유레카 & 게이트웨이**: CircuitBreaker, Retry 장애 대응 구축<br>▶ **유저**: Kafka Batch Listener를 활용한 대용량 데이터 적재<br>▶ **상품**: Redis 최저가 조회 성능 개선, Kafka 비동기 통신 구현 | [🍁 깃헙링크]    |


<br>

<details>
  <summary> 그라운드 룰 </summary>
  <br>
🍁 **문제 발생 시 즉시 공유**  
- 문제가 발생하면 팀원들에게 빠르게 상황을 공유하여 협력 해결.

🍁 **정규 시간 내 풀타임 화면 공유**
- 업무 시간 동안 항상 화면을 공유하여 투명한 협업 유지.

🍁 **사소한 것도 질문하기**
- 궁금한 점이나 막힌 부분은 사소한 것이라도 즉시 물어보고 해결.

🍁 **스크럼에서 트러블 슈팅 및 구현 사항 설명**
- 매일 스크럼 시간에 구현 진행 상황과 문제 해결 과정을 공유.

🍁 **1Day, 1Issue, 1PR 원칙**
- 하루에 하나의 이슈를 처리하고 PR 생성.

🍁 **1PR 당 3개 이상의 리뷰 남기기**
- 각 PR에 대해 최소 3개의 리뷰를 작성하여 코드 품질을 개선.
</details>

<br>
<br>

<div align="center">

# 🟦🟧 성과 및 회고 🟧🟦

</div>


<br>
<br>
<br>
<br>
<br>
<br>
# ✈️ Quokka Travel

QuokkaTravel은 쿼카와 함께 여행을 다니며 여행지의 사람들과 커뮤니케이션을 할 수 있는 서비스입니다.

> 여행 숙소 정보를 확인하고, 여행이 시작되면 여행지의 사람들과 **대화**를 나눌 수 있습니다.
> 여행지의 다양한 이벤트와 함께 행복한 여행을 할 수 있습니다.

### 기능 1

- 여행지 숙소 예약

### 기능 2

- 여행지에 대해서 다양한 사람들과의 대화를 할 수 있는 오픈채팅

---

# ✔︎ Index

1. [팀 소개](#👨‍👦‍👦-팀-소개)
2. [주요 기능](#💡-주요-기능)
3. [Framework](#📚-Framework)
4. [기술 특장점](#🛠-기술-특장점)
5. [Challenges & TroubleShooting](#Challenges-and-TroubleShooting)
6. [라이센스](#라이센스)
7. [문의](#문의)

---

# 👨‍👨‍👦‍👦 팀 소개

|         [배주희]          |         [이재현]          |          [이승언]           |         [안동환]          |         [안예환]          |
| :---: | :---: | :---: | :---: | :---: |
| ![DALL·E-2024-11-06-21 27](https://github.com/user-attachments/assets/2affac05-5b2c-47f4-a894-85205e3d1bc1) | ![DALL·E-2024-11-06-21 22](https://github.com/user-attachments/assets/f5a19c45-d6e0-421d-8eb6-37b253c6cffb) | ![DALL·E-2024-11-06-21 37_1](https://github.com/user-attachments/assets/3c7ca556-9035-421c-b1e4-1a9c45aa6589) | ![DALL·E-2024-11-06-21 37](https://github.com/user-attachments/assets/b9f08ce2-f4af-41a6-b8c7-ad5e0316a03e) | ![DALL·E-2024-11-06-21 21](https://github.com/user-attachments/assets/a90ec70a-d2af-49ac-9aeb-6397c1fe43f4) |
| Pear Caution Caution | 한산이가 | 미니언즈 | 안안동동환환 | Mosquito An |

---

# 💡 주요 기능

## 📐 숙소 예약

- 여행지의 숙소를 예약할 수 있습니다.

<br />

## 🖊️ 지역별 실시간 오픈 채팅

- 실시간 채팅으로 해당 지역 사람들과 소통하며 여행에 대한 정보를 얻을 수 있습니다.

<br />

## 💭 다양한 이벤트 확인

- 여행지의 다양한 이벤트를 한눈에 확인할 수 있습니다.

---

# 📚 System Architecture
- Quokka Travel에서 사용한 Architecture는 다음과 같습니다.


---

# 🚀 Challenges and TroubleShooting
- 구현을 하면서 겪은 문제들과 그 해결방안들을 정리해두었습니다.




-------


![스크린샷 2024-11-14 오후 6 59 20](https://github.com/user-attachments/assets/26e4539f-c0f2-45d4-b243-a979afebac67)








# Quakka-Travel

![largeImageSrc adapt 740 medium](https://github.com/user-attachments/assets/fb3c6851-7b93-4f06-86c0-d54b75bb5a30)

쿼카와 함께하는 여행 플랫폼입니다!
---

# ✈️ Quokka Travel

QuokkaTravel은 쿼카와 함께 여행을 다니며 여행지의 사람들과 커뮤니케이션을 할 수 있는 서비스입니다.

> 여행 숙소 정보를 확인하고, 여행이 시작되면 여행지의 사람들과 **대화**를 나눌 수 있습니다.
> 여행지의 다양한 이벤트와 함께 행복한 여행을 할 수 있습니다.

### 기능 1

- 여행지 숙소 예약

### 기능 2

- 여행지에 대해서 다양한 사람들과의 대화를 할 수 있는 오픈채팅

---

# ✔︎ Index

1. [팀 소개](#👨‍👦‍👦-팀-소개)
2. [주요 기능](#💡-주요-기능)
3. [Framework](#📚-Framework)
4. [기술 특장점](#🛠-기술-특장점)
5. [Challenges & TroubleShooting](#Challenges-and-TroubleShooting)
6. [라이센스](#라이센스)
7. [문의](#문의)

---

# 👨‍👨‍👦‍👦 팀 소개

|         [배주희]          |         [이재현]          |          [이승언]           |         [안동환]          |         [안예환]          |
| :---: | :---: | :---: | :---: | :---: |
| ![DALL·E-2024-11-06-21 27](https://github.com/user-attachments/assets/2affac05-5b2c-47f4-a894-85205e3d1bc1) | ![DALL·E-2024-11-06-21 22](https://github.com/user-attachments/assets/f5a19c45-d6e0-421d-8eb6-37b253c6cffb) | ![DALL·E-2024-11-06-21 37_1](https://github.com/user-attachments/assets/3c7ca556-9035-421c-b1e4-1a9c45aa6589) | ![DALL·E-2024-11-06-21 37](https://github.com/user-attachments/assets/b9f08ce2-f4af-41a6-b8c7-ad5e0316a03e) | ![DALL·E-2024-11-06-21 21](https://github.com/user-attachments/assets/a90ec70a-d2af-49ac-9aeb-6397c1fe43f4) |
| Pear Caution Caution | 한산이가 | 미니언즈 | 안안동동환환 | Mosquito An |

---

# 💡 주요 기능

## 📐 숙소 예약

- 여행지의 숙소를 예약할 수 있습니다.

<br />

## 🖊️ 지역별 실시간 오픈 채팅

- 실시간 채팅으로 해당 지역 사람들과 소통하며 여행에 대한 정보를 얻을 수 있습니다.

<br />

## 💭 다양한 이벤트 확인

- 여행지의 다양한 이벤트를 한눈에 확인할 수 있습니다.

---

# 📚 System Architecture
- Quokka Travel에서 사용한 Architecture는 다음과 같습니다.


---

# 🚀 Challenges and TroubleShooting
- 구현을 하면서 겪은 문제들과 그 해결방안들을 정리해두었습니다.

------------------------

복사본

<div align="center">

# 🟦🟧 QUOKKA TRAVEL 🟧🟦

![스크린샷 2024-11-14 오후 6 59 41](https://github.com/user-attachments/assets/f3e4bc05-86a7-4916-b805-b1bac7a6fcf3)

</div>

사용자 중심의 여행 예약 플랫폼을 구축하여 사용자가 필요한 전국의 숙소를 실시간으로 예약 가능하고 사용후 리뷰를 남길 수 있는 서비스를 제공하는 야놀자, 에어비앤비를 벤치마킹한 여행 플랫폼 서비스입니다.


<br>
<br>

<div align="center">

# 🟦🟧 여행을 더 편리하고 스마트하게! 🟧🟦

![스크린샷 2024-11-14 오후 7 24 54](https://github.com/user-attachments/assets/355f0d16-6132-4aca-a20f-ac800880b114)

</div>

Quokka Travel은 사용자가 손쉽게 여행을 계획하고, 원하는 숙소를 검색 및 예약할 수 있도록 지원하는 여행 플랫폼입니다. 사용자가 설정한 여행지의 예산을 보다 저렴하게 제공할 수 있도록 슬랙 알림을 통해 선착순 쿠폰을 지원하며 사용자가 예약을 완료하는 단계에서 안전하고 편리한 결제 기능을 제공합니다. 또한, 사용자는 숙소를 이용한 후 리뷰를 남기고 평점을 부여할 수 있어, 이러한 리뷰와 평점은 다른 사용자가 숙소를 선택하는 데 중요한 정보를 제공하며, 신뢰성 있는 숙소를 찾는 데 도움을 줍니다.

<br>
<br>

<div align="center">

# 🟦🟧 프로젝트 핵심 목표 🟧🟦

</div>

1. **대규모 트래픽 대응**
   - Redis와 Kafka를 활용한 비동기 처리를 통해 API 요청 200req/sec 이상 처리.
   - 동시성 문제를 해결하며 안정적인 쿠폰 발급 서비스 제공.

2. **성능 최적화**
   - Redis 기반 캐싱으로 실시간 상품 조회 성능을 3배 향상.
   - Redisson을 사용하여 CPU 점유율 50% 감소 및 안정적 데이터 처리 구현.

3. **운영 및 배포 효율화**
   - Docker와 Github Actions를 이용한 CI/CD 파이프라인 구축으로 배포 자동화.
   - Prometheus와 Grafana를 활용한 실시간 모니터링으로 시스템 안정성 확보.

4. **데이터 일관성 및 트랜잭션 관리**
   - Kafka를 이용한 SAGA 패턴으로 분산 트랜잭션 관리.
   - 중복 및 데이터 손실을 방지하는 Kafka Batch Listener 구현.


<div align="center">

<br>
<br>

# 🟦🟧 KEY SUMMARY 🟧🟦

</div>

### 💡 [성능개선] 최저가 상품 조회 성능, Redis 도입으로 3배 이상 향상
<br>
<details>
<summary> 한 줄 요약 </summary> 
  <br>
   - Redis 도입으로 기존 DB 조회보다 **348% 성능 개선**  
   - 대규모 트래픽 환경에서도 안정적인 서비스 유지  

![성능 개선 이미지]
</details>
<br>
<details>
<summary> 도입 배경 </summary> 
   - 상품의 최저가를 제공하기 위해 외부 서버에서 제공하는 타임세일 상품의 할인율과 상품 자체의 할인율을 비교하는 기능이 필요  
</details>
<br>
<details>
<summary> 기술적 선택지 </summary>

1. **DB 데이터 적재**
   - 스케줄링 작업으로 짧은 시간 내 대량의 데이터를 수정하는 것은 데이터베이스에 과도한 부하 발생
   - 상품 자체의 할인율과 타임세일 할인율을 분리하여 별도 컬럼 저장 필요

2. **Redis 캐싱**
   - 실시간 최저가 할인율로 최신 정보와 가격 제공
   - TTL 설정으로 타임세일 종료 시 자동 데이터 삭제

**결론:** Redis 도입을 결정하여 성능 및 효율성을 크게 개선
</details>
<br>

### 💡 [트러블 슈팅] LazyConnectionDataSourceProxy - 불필요한 커넥션 점유 해결
<br>
<details>
<summary> 배경 </summary>
   - **스프링 배치 5버전 도입**  
     - 정산은 실시간이 아닌, 이용자가 적은 시간에 일괄 처리하도록 배치 선택  
     - 메인 DB와 배치 메타데이터 DB 분리 필요  
   - **배치 메타데이터 테이블 생성 필수화**  
     - 메타데이터 전용 DB를 나누는 구조로 전환  
   - **멀티 DataSource 구성**  
     - 메인 DataSource와 배치 DataSource로 데이터베이스 모듈 구분  
</details>
<br>
<details>
<summary> 문제 </summary>  
  <br>
  - 실제 DB 요청 없이도 불필요한 커넥션 점유 발생  
  - 스프링은 트랜잭션 진입 시 커넥션 풀에서 커넥션을 점유  
  - 멀티 DataSource로 인해 두 DataSource 모두 커넥션 점유  
</details>

<br>

<details>

  <summary> 해결 방안 </summary>
  <br>
  - **LazyConnectionDataSourceProxy 클래스 사용**  
  - 실제 DB 요청 전까지 커넥션 점유를 지연시키는 프록시 DataSource 활용  

![LazyConnectionDataSourceProxy 이미지]

- 이를 통해 **실제 DB 요청 시에만 커넥션 점유**로 불필요한 리소스 낭비를 해결
</details>

<br>
<br>

<div align="center">

# 🟦🟧 인프라 아키텍처 & 적용 기술 🟧🟦

</div>

<br>

<details>
  <summary> 아키텍처 다이어그램 </summary>
  <br>
  위 아키텍처는 **MSA 기반의 이커머스 서비스** 구조를 나타냅니다.  
  각 모듈은 Redis, Kafka를 통해 통신하며, Docker로 컨테이너화되어 CI/CD를 통해 자동 배포됩니다.
</details>

<br>

<details>
  <summary> 데이터베이스 및 캐싱 </summary>
  <br>
  1. **Redis**  
  - **적용 위치**: 캐시 서버  
  - **사용 이유**: 실시간 상품 할인율과 최저가 조회 성능 향상. TTL 설정으로 타임세일 종료 시 데이터 자동 삭제. 
</details>

<br>

<details>
  <summary> 메시징 시스템 </summary>
  <br>
  1. **Apache Kafka**  
  - **적용 위치**: 서비스 간 비동기 통신  
  - **사용 이유**: 대규모 메시지 처리를 위한 안정적 메시징 큐 구현.  
  - **구체적 역할**: 주문 생성 시 재고 차감, 쿠폰 적용 등 서비스 간 데이터 일관성 보장.
</details>

<br>

<details>
  <summary> 인프라 및 배포 </summary>
  <br>
1. **Docker**  
  - **적용 위치**: 모든 서비스 컨테이너화  
  - **사용 이유**: 환경 이식성과 배포 속도 개선.  

2. **Github Actions**
- **적용 위치**: CI/CD 파이프라인
- **사용 이유**: 자동화된 코드 품질 검사와 배포 구현.

3. **Prometheus & Grafana**
   - **적용 위치**: 실시간 모니터링
   - **사용 이유**: 주요 메트릭(트래픽, CPU 사용량 등) 수집 및 시각화로 장애 발생 시 빠른 대응 가능.
</details>


<br>
<br>

<div align="center">

# 🟦🟧 주요 기능 🟧🟦

</div>

<br>

<details>
  <summary> 쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>

<details>
  <summary> 주문 처리: Saga 패턴 적용 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>

<details>
  <summary> 최저가 조회: Redis 캐싱 활용 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>

<details>
  <summary> 결제 정산: Spring Batch 5 활용 </summary>
  <br>
### 🍁 **쿠폰 발급: Redis 및 Kafka를 통한 비동기 처리**
- 대용량 트래픽을 수용하기 위해 Redis와 Kafka를 활용한 비동기 쿠폰 발급 시스템 구현.
- Redis Lua Script로 동시성 제어 및 쿠폰 발급 상태 관리.
</details>

<br>
<br>

<div align="center">

# 🟦🟧 기술적 고도화 🟧🟦

</div>

<br>

<details>
  <summary> 성능 개선 과정 작성 프레임워크 </summary>
  <br>
[내가 구현한 기능]

[주요 로직]

[배경]

[요구사항]

[선택지]

[의사결정/사유]

</details>

<br>

<details>
  <summary> 문제해결 과정 작성 프레임워크 </summary>
  <br>
[성능 개선 / 코드 개선 요약]

[문제 정의]

[가설]

[해결 방안]
- 문제 해결을 위한 의사결정 과정
- 해결 과정

[해결 완료]
- 결과
- 전후 데이터 비교

[회고]
- 기술의 장단점
- 다시 시도한다면?
</details>

<br>
<br>

<div align="center">

# 🟦🟧 역할 분담 및 협업 방식 🟧🟦

</div>

<details>
  <summary> 역할 </summary>
  <br>
| 이름   | 포지션   | 담당(개인별 기여점)                                                                                                            | Github 링크                       |
|--------|----------|-----------------------------------------------------------------------------------------------------------------------------|-----------------------------------|
| 김ㅇㅇ | 리더     | ▶ **결제**: 토스페이먼츠 PG 연동, 간편결제 구현<br>▶ **정산**: 스프링 배치 활용 (Jpa → Jdbc 변경하여 4.6배 개선)<br>▶ **배포**: 멀티모듈 설정, 멀티 데이터소스 설정, Graceful Shutdown 구현, CI/CD 적용 | [🍁 깃헙링크] |
| 조ㅇㅇ | 부리더   | ▶ **쿠폰**: Redis Lua Script를 통한 동시성 제어 및 대규모 트래픽 제어<br>▶ **타임세일**: Kafka 비동기 발급 처리, 분산락을 통한 동시성 제어                          | [🍁 깃헙링크]    |
| 박ㅇㅇ | 팀원     | ▶ **주문**: MSA 기반 주문 로직 구현, Kafka 기반 SAGA 패턴 적용<br>▶ **스케줄러**: 결제 단계 주문 자동 삭제                                                | [🍁 깃헙링크]     |
| 정ㅇㅇ | 팀원     | ▶ **유레카 & 게이트웨이**: CircuitBreaker, Retry 장애 대응 구축<br>▶ **유저**: Kafka Batch Listener를 활용한 대용량 데이터 적재<br>▶ **상품**: Redis 최저가 조회 성능 개선, Kafka 비동기 통신 구현 | [🍁 깃헙링크]    |


<br>

<details>
  <summary> 그라운드 룰 </summary>
  <br>
🍁 **문제 발생 시 즉시 공유**  
- 문제가 발생하면 팀원들에게 빠르게 상황을 공유하여 협력 해결.

🍁 **정규 시간 내 풀타임 화면 공유**
- 업무 시간 동안 항상 화면을 공유하여 투명한 협업 유지.

🍁 **사소한 것도 질문하기**
- 궁금한 점이나 막힌 부분은 사소한 것이라도 즉시 물어보고 해결.

🍁 **스크럼에서 트러블 슈팅 및 구현 사항 설명**
- 매일 스크럼 시간에 구현 진행 상황과 문제 해결 과정을 공유.

🍁 **1Day, 1Issue, 1PR 원칙**
- 하루에 하나의 이슈를 처리하고 PR 생성.

🍁 **1PR 당 3개 이상의 리뷰 남기기**
- 각 PR에 대해 최소 3개의 리뷰를 작성하여 코드 품질을 개선.
</details>

<br>
<br>

<div align="center">

# 🟦🟧 성과 및 회고 🟧🟦

</div>















<br>
<br>
<br>
<br>
<br>
<br>
# ✈️ Quokka Travel

QuokkaTravel은 쿼카와 함께 여행을 다니며 여행지의 사람들과 커뮤니케이션을 할 수 있는 서비스입니다.

> 여행 숙소 정보를 확인하고, 여행이 시작되면 여행지의 사람들과 **대화**를 나눌 수 있습니다.
> 여행지의 다양한 이벤트와 함께 행복한 여행을 할 수 있습니다.

### 기능 1

- 여행지 숙소 예약

### 기능 2

- 여행지에 대해서 다양한 사람들과의 대화를 할 수 있는 오픈채팅

---

# ✔︎ Index

1. [팀 소개](#👨‍👦‍👦-팀-소개)
2. [주요 기능](#💡-주요-기능)
3. [Framework](#📚-Framework)
4. [기술 특장점](#🛠-기술-특장점)
5. [Challenges & TroubleShooting](#Challenges-and-TroubleShooting)
6. [라이센스](#라이센스)
7. [문의](#문의)

---

# 👨‍👨‍👦‍👦 팀 소개

|         [배주희]          |         [이재현]          |          [이승언]           |         [안동환]          |         [안예환]          |
| :---: | :---: | :---: | :---: | :---: |
| ![DALL·E-2024-11-06-21 27](https://github.com/user-attachments/assets/2affac05-5b2c-47f4-a894-85205e3d1bc1) | ![DALL·E-2024-11-06-21 22](https://github.com/user-attachments/assets/f5a19c45-d6e0-421d-8eb6-37b253c6cffb) | ![DALL·E-2024-11-06-21 37_1](https://github.com/user-attachments/assets/3c7ca556-9035-421c-b1e4-1a9c45aa6589) | ![DALL·E-2024-11-06-21 37](https://github.com/user-attachments/assets/b9f08ce2-f4af-41a6-b8c7-ad5e0316a03e) | ![DALL·E-2024-11-06-21 21](https://github.com/user-attachments/assets/a90ec70a-d2af-49ac-9aeb-6397c1fe43f4) |
| Pear Caution Caution | 한산이가 | 미니언즈 | 안안동동환환 | Mosquito An |

---

# 💡 주요 기능

## 📐 숙소 예약

- 여행지의 숙소를 예약할 수 있습니다.

<br />

## 🖊️ 지역별 실시간 오픈 채팅

- 실시간 채팅으로 해당 지역 사람들과 소통하며 여행에 대한 정보를 얻을 수 있습니다.

<br />

## 💭 다양한 이벤트 확인

- 여행지의 다양한 이벤트를 한눈에 확인할 수 있습니다.

---

# 📚 System Architecture
- Quokka Travel에서 사용한 Architecture는 다음과 같습니다.


---

# 🚀 Challenges and TroubleShooting
- 구현을 하면서 겪은 문제들과 그 해결방안들을 정리해두었습니다.




-------


![스크린샷 2024-11-14 오후 6 59 20](https://github.com/user-attachments/assets/26e4539f-c0f2-45d4-b243-a979afebac67)


