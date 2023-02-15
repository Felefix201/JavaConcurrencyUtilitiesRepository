package CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    static final int numThreads = 3;
    static CyclicBarrier barrier = new CyclicBarrier(numThreads, () -> System.out.println("Alle Threads sind bereit!"));

    public static void main(String[] args) {
        for (int i = 0; i < numThreads; i++) {
            int id = i;
            new Thread(() -> {
                System.out.println("Thread " + id + " startet seine Arbeit");
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + id + " hat seine Arbeit beendet");
            }).start();
        }
    }
}
