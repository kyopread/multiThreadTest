# 멀티스레드 예제를 통한 테스트 코드 작성

### 스레드 안전한 객체란?

여러 스레드가 동시에 클래스를 사용하려 하는 상황에서
클래스 내부의 값을 안정적인 상태로 유지할 수 있다.

### 스레드 안전한 객체를 설계하는 법
- Volatile
- 동기화 컬렉션
- Producer - Consumer 패턴 (kafka)
- 상태범위 제한
- 조건 큐
- 위임기법
- 스레드 한정
- 블로킹 큐
- 자바 모니터 패턴
- 락
- 인스턴스 한정
- ThreadLocal

### 결론
- 공유변수 최소화
- 캡슐화 (관리 포인트를 한 곳으로 모으자)
- 문서화
를 잘 하도록 하자

참고 : [테코톡](https://youtu.be/ktWcieiNzKs)