package kr.kyopread.multithreadtest.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DeadLockExTest {

    @Test
    @DisplayName("한 명은 상의를 입고 한 명은 바지를 입고 서로 상대방 옷을 입으려 해서 데드락을 발생시킨다.")
    void deadlock() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final DeadLockEx deadLockEx = new DeadLockEx();

        executorService.submit(() -> {
            deadLockEx.wearTShirtThenWearPants();
            countDownLatch.countDown();
        });

        executorService.submit(() -> {
            deadLockEx.wearPantsThenWearTShirt();
            countDownLatch.countDown();
        });

        // 두개의 스레드가 정상 작동 하는 경우 혹은 10초 기다려서 나감.
        countDownLatch.await(10000, TimeUnit.MILLISECONDS);

        // 두개의 스레드가 정상 작동하게 되면 예외 발생
        if (countDownLatch.getCount() == 0) {
            throw new RuntimeException("데드락이 발생하지 않았습니다.");
        }

    }
}