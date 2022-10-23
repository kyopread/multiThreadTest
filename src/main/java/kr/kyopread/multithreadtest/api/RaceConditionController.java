package kr.kyopread.multithreadtest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/race-condition")
public class RaceConditionController {
    public static Integer studentCount = 0;

    // 공유 자원을 통한
    // Read - Modify - Write 패턴
    // 같은 공유 자원에 동시에 접근하여 결국 하나만 반영되는 문제
    @PostMapping("/1/increase")
    public ResponseEntity<Void> increaseCount() {
        studentCount++;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/1/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(studentCount);
    }


    // Check - then - act
    // 1번 쓰레드가 if문을 통과하고 출력을 해야하는데
    // if 분기문을 통과한 시점에서
    // 2번 스레드가 studentCount 값을 올리면 엉뚱한 값이 1번 쓰레드에서 출력된다.
    @PostMapping("/2/check-then-act")
    public ResponseEntity<Void> printWarning() throws InterruptedException {
        studentCount++;
        if (studentCount < 30) {
            Thread.sleep(1);
            System.out.println("폐강위험!! studentCount = " + studentCount);
        }

        return ResponseEntity.ok().build();
    }

    // 이런 경쟁상태를 방지하기 위해 '원자성'과 '가시성'을 보장해야한다.
    // 원자성 : 공유 자원에 대한 작업의 단위가 더 이상 쪼갤 수 없는 하나의 연산인 것처럼 동작하는 것
    // 다른 스레드가 현재 스레드의 연산 상태에 개입 할 수 없도록 해야한다.
    // 위 케이스들의 경우 메모리에서 값을 읽어오고(read) 읽어온 값을 수정(modify) 하는 과정에서
    // 시간텀이 발생하고 그 사이 다른 스레드의 연산 결과에 따라 값이 영향을 받기 때문에 문제 되는 것



    // 동기화 - 블로킹
    // 특정 스레드가 작업을 수행하는동안 다른 작업은 진행하지 않고 대기하는 방식.
    // 베타동기 - synchronized
    // 조건동기 - wait(), notify(), notifyAll()
    // Synchronized - 연산결과가 메모리에 써질때까지 다른 스레드는 대기
    // 연산 시 임계구역에 들어가서 연산을 하고
    // 나머지 스레드들은 임계구역에 접근하지 못하고 대기한다.
    // 임계구역에 있는 스레드는 메인 메모리에 접근하여 연산을 진행하고 마무리 되면
    // 임계구역에서 나오게 되어 다른 스레드가 접근하여 연산 처리를 한다.
    // 원자성과 가시성을 챙길 수 있지만
    // 블로킹 방식의 단점으로 나머지 스레드 대기 -> 성능 저하가 발생한다.
    // 치명적인 데드락이 발생하기도 한다.
    // 예제 DeadLockEx
    @PostMapping("/3/check-then-act")
    public synchronized ResponseEntity<Void> printWarning2() throws InterruptedException {
        studentCount++;
        if (studentCount < 30) {
            Thread.sleep(1);
            System.out.println("폐강위험!! studentCount = " + studentCount);
        }

        return ResponseEntity.ok().build();
    }
}
