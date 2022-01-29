# 업비트 가격 알람 봇

### 프로젝트 소개

이 프로젝트는 업비트 전용 가격 변동 알람 봇입니다.

Java로 개발 되었으며 사용된 프레임워크와 라이브러리는 다음과 같습니다.

- Spring Boot
- PostgreSql
- Docker Selenium Standalone

작동 방식은 다음과 같습니다.

1. 스케줄러를 매분1초에 1분간격으로 실행합니다.
2. UpbitService -> coinCheck() 실행
3. 1분전 가격과 비교해 변동 폭이 설정해 둔 값에 맞을 경우 @Aysnc telegramSendMsg() 실행
4. telegramSendMsg() 실행
   1. SeleniumService -> seleniumRunning 실행
   2. tradingview에 들어가 현재 업비트 차트를 캡쳐 하고 저장후 파일 이름을 리턴합니다.
   3. 리턴 받은 파일명을 텔레그램 메세지에 담아 전송합니다.

결과는 다음과 같습니다.

![images](/document/images/images.png)