package kr.kyopread.multithreadtest.api;

public class Visibility {

    // NoVisibility 에서 보지 못했던
    // 가시성을 챙기기 위해 volatile이라는 키위드를 붙여 사용하면
    // 이 변수는 cpu cache를 사용하지 않고 메인 메모리에서만 접근하여 값을 사용한다.
    // 원자성과 가시성을 챙기는 것을 '동기화'라고 지칭한다.
    public volatile static boolean missileLaunched = false;

    private static class MissileInterceptor extends Thread {
        @Override
        public void run() {
            while (!missileLaunched) {/* 대기 */}
            System.out.println("요격");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final MissileInterceptor missileInterceptor = new MissileInterceptor();
        missileInterceptor.start();
        Thread.sleep(5000);
        launchMissile();
        missileInterceptor.join();
    }

    private static void launchMissile() {
        missileLaunched = true;
    }
}
