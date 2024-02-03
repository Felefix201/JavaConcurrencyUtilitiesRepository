package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchExample which demonstrates the usage of the CountDownLatch class.
 * The CountDownLatch class is used to block a thread until a certain number of tasks are completed.
 * The CountDownLatch class is used to signal a thread that a certain number of tasks are completed.
 */
public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(new Task(latch), "Task 1");
        Thread t2 = new Thread(new Task(latch), "Task 2");
        Thread t3 = new Thread(new Task(latch), "Task 3");

        t1.start();
        t2.start();
        t3.start();

        latch.await();
        System.out.println("Alle Aufgaben sind abgeschlossen");
    }

    static class Task implements Runnable {
        private CountDownLatch latch;

        Task(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Aufgabe wird ausgeführt von: " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}
