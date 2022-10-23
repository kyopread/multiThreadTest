package kr.kyopread.multithreadtest.api;

public class NoVisibility {
    public static boolean missileLaunched = false;

    // 메인 메모리와 cpu 사이의 거리가 멀기 때문에
    // cpu cache에 값을 두고 사용하게 되는데
    // 메인쓰레드에서 true로 값을 변경하였더라도
    // 다른 스레드의 경우 계속 cpu cache의 값을 바라보기 때문에
    // 값이 바뀐것을 알아차리지 못하여 문제가 발생한다.
    // 그러기 위해서는 가시성을 보장해야하는데
    // 메인 메모리에 진짜 값을 보기 위해서라는 점을 인지하면 좋다.
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
