package CyclicBarrier;

import java.util.concurrent.CyclicBarrier;


/**
 * The CyclicBarrier is used to synchronize multiple threads at a certain point.
 * In this example, the CyclicBarrier is used to synchronize three threads.
 * The barrier is set to 3, so that the barrier action is executed when all three threads have reached the barrier.
 * The barrier action is a lambda expression which prints a message to the console.
 * The threads are started in a loop and each thread waits at the barrier after starting its work.
 */
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
