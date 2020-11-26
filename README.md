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
- mysql
- mybatis
- lombok

기술적인 집중 요소
---
- 객체 지향의 기본 원리와 의미 있는 코드 작성
- 라이브러리 및 기능 추가 시 이유 있는 선택과 사용 목적 고려
- 테스트 코드 작성

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
7. 발급된 쿠폰중 만료 3일전 사용자에게 메세지(“쿠폰이 3일 후 만료됩니다.”)를 발송하는 기능을 구현하
세요. (실제 메세지를 발송하는것이 아닌 stdout 등으로 출력하시면 됩니다.)

제약사항(선택)
---
- API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능을 개발하고 각 API 호출
시에 HTTP Header에 발급받은 토큰을 가지고 호출하세요.
- signup 계정생성 API: ID, PW를 입력 받아 내부 DB에 계정을 저장하고 토큰을 생성하여 출력한
다. 단, 패스워드는 안전한 방법으로 저장한다.
- signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다.
- 100억개 이상 쿠폰 관리 저장 관리 가능하도록 구현할것
- 10만개 이상 벌크 csv Import 기능
- 대용량 트랙픽(TPS 10K 이상)을 고려한 시스템 구현
- 성능테스트 결과 / 피드백

설계 고려 사항(문제해결 전략)
---
 
실행 방법
---
- coupon.7z 압축 파일 해제
- (윈도우 기준) 파일 경로 에서 실행 명령어 실행 (jre or jdk 필요)
- mvn test package 시 target 파일에 InitialData.txt 복사 해야 정상 구동 됩니다.
```
cd leaderboard\
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

참고
---
### **Java8 표준 라이브러리 주소** <br/> https://docs.oracle.com/javase/8/docs/api/
