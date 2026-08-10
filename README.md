# 온숨 (Onsoom) Backend

고령자의 쇼핑을 돕는 음성 기반 요청 서비스입니다. 부모님이 음성으로 필요한 물건을 요청하면, STT로 텍스트화하고 보호자에게 전달되어 대신 구매할 수 있도록 연결합니다.

### 링크
[온숨 바로가기](https://onsum.store/)

> 도메인 스펠링에 오타가 있습니다(onsoom → onsum). 인지하고 있으며, 우선순위상 그대로 사용 중입니다.

## 1. 프로젝트 정보

| 항목      | 내용                                   |
|---------|--------------------------------------|
| 개발 인원   | 2명                                   |
| 담당 역할   | Backend, Infra                       |
| 주요 기술 스택 | Spring Boot, MariaDB, GCP, Docker    |

## 2. 주요 기능

### 🔐 사용자 인증
- 카카오 OAuth2 + JWT 기반 로그인
- 부모님 / 보호자 역할 구분

### 🔗 페어링
- 부모님이 페어링 코드를 발급하고, 보호자가 코드를 입력해 서로 연결
- 페어링 해제 가능

### 🎙️ 음성 요청
- 부모님이 음성으로 필요한 물건을 요청하면 STT 서버로 전송해 텍스트로 변환
- 요청은 대기중 → 확인됨 → (취소됨 | 완료됨) 상태를 거치며, 보호자의 행동에 따라 상태가 전환됨
- 잘못된 상태 전이(예: 완료된 요청을 취소로 변경)는 서버에서 차단

### 🛍️ 쇼핑 추천
- 별도 추천 서버(Python)와 연동해 요청 내용을 바탕으로 상품 후보와 질문을 생성

### 📂 파일 저장
- 음성 파일은 GCP Cloud Storage에 저장

## 3. 기술 스택

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![GCP](https://img.shields.io/badge/Google_Cloud-4285F4?style=for-the-badge&logo=googlecloud&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## 4. 서버 아키텍처

<img alt="서버 아키텍처" src="https://github.com/user-attachments/assets/88c9f6e1-851c-4001-a720-b365148a7901" />

Spring Boot 백엔드가 REST API를 담당하고, 음성 인식(STT)과 쇼핑 추천은 별도의 Python 서버(GCP Speech-to-Text, OpenAI 연동)로 분리되어 있습니다. 음성 파일은 GCP Cloud Storage에, 요청/유저 데이터는 MariaDB에 저장됩니다.