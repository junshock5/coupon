# coupon
목적
---
- ### 쿠폰 시스템 개발
- 사용자에게 할인, 선물등 쿠폰을 제공하는 서비스를 개발

사용 기술
---
- Maven Project
- Java 8
- Junit
- assertj
- Swagger
- h2 database
- mybatis
- lombok

기술적인 집중 요소
---
- 객체 지향의 기본 원리와 의미 있는 코드 작성
- 라이브러리 및 기능 추가 시 이유 있는 선택과 사용 목적 고려
- Junit을 사용하여 테스트 코드 작성
- batch Insert 기능을 이용한 대용량 데이터 처리
- 대용량 트래픽 환경을 고려하여 WAS의 Scale-out 구조로 개발
- 20만개 쿠폰 벌크 csv Import 기능 개발

REST API 사양
---
- 조건 : 각각의 쿠폰은 만료기간이 존재하며, 쿠폰형식은 번호, 코드, 자릿수등 자유롭게 선택
1. 랜덤한 코드의 쿠폰을 N개 생성하여 데이터베이스에 보관하는 API를 구현하세요.
- input : N
2. 생성된 쿠폰중 하나를 사용자에게 지급하는 API를 구현하세요.
- output : 쿠폰번호(XXXXX-XXXXXX-XXXXXXXX)
3. 사용자에게 지급된 쿠폰을 조회하는 API를 구현하세요.
4. 지급된 쿠폰중 하나를 사용하는 API를 구현하세요. (쿠폰 재사용은 불가)
- input : 쿠폰번호
5. 지급된 쿠폰중 하나를 사용 취소하는 API를 구현하세요. (취소된 쿠폰 재사용 가능)
- input : 쿠폰번호
6. 발급된 쿠폰중 당일 만료된 전체 쿠폰 목록을 조회하는 API를 구현하세요.

제약사항
---
- 100억개 이상 쿠폰 관리 저장 관리 가능하도록 구현할것
- 10만개 이상 벌크 csv Import 기능
- 대용량 트랙픽(TPS 10K 이상)을 고려한 시스템 구현

실행 방법
---
- coupon.7z 압축 파일 해제
- (윈도우 기준) 파일 경로 에서 실행 명령어 실행 (jre or jdk 필요)
- mvn test package 시 target 파일에 InitialData.txt 복사 해야 정상 구동 됩니다.
```
cd coupon
java -jar coupon-0.0.1-SNAPSHOT.jar
```

API 확인 방법
---
- url 접근시 swagger 를통해 API 확인
- http://localhost:8080/swagger-ui.html#/ 

DB 데이터 확인 방법
---
- http://localhost:8080/h2-console/ 접속 후
- setting 값을 Generic h2 (embedded) 설정
- JDBC url 값을 jdbc:h2:~/coupon 접속 후 확인

ERD
---

![2020-11-26 14;33;25](https://user-images.githubusercontent.com/61732452/100312008-7405e080-2ff4-11eb-9243-ea98c4ef79a8.PNG)
