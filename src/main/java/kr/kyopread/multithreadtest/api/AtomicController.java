package kr.kyopread.multithreadtest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/atomic")
public class AtomicController {

    // 논블로킹이란 다른 스레드의 작업여부와 상관없이 자신의 작업을 수행하는 방식
    // Atomic 타입 - 동시성을 보장하기 위해서 자바에서 제공하는 Wrapper class
    // CAS + Volatile
    // 일반적인 연산의 경우 JVM과 CPU 사이에 있는 cache 값을 끌어온다.
    // 하지만 AtomicReference를 설정하게 되면
    // value 변수에 volatile이 위치해 있어 메모리 상의 변수를 바로 끌어올 수 있다.
    // 연산 시에 compareAndSet() 메서드를 호출하고 (CAS 알고리즘을 녹인 메서드)
    // 메모리에 저장된 값과 스레드 내부 기댓값을 비교하여
    // 일치하면 true, 일치하지 않으면 false 반환


    // Compare and set (CAS 알고리즘)
    // 자원값 = 기댓값(최초 자원값), 새로운 값이 있는데
    // 새로운 값이 발생하고나서
    // 자원값과 기댓값을 비교해봤을 때
    // 같다면 자원값을 새로운 값으로 수정하고 true를 반환하고
    // 다르면 수정하지 않고 False를 반환한다.
    // 이를 통해 원자성을 보장한다.


    // 논블로킹 AtomicInteger를 적용하게 되면?
    // 원자성과 가시성을 보장한다.
    public AtomicInteger studentCount = new AtomicInteger(0);

    @PostMapping("/increase")
    public ResponseEntity<Void> increaseCount() {
        studentCount.addAndGet(1);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<AtomicInteger> getCount() {
        return ResponseEntity.ok(studentCount);
    }
}
