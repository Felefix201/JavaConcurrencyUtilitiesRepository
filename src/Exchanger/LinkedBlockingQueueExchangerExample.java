package Exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Exchanger is a synchronization point at which threads can pair and swap elements within pairs.
 * Each thread presents some object on entry to the exchange method, matches with a partner thread,
 * and receives its partner's object on return.
 * An Exchanger may be viewed as a bidirectional form of a SynchronousQueue.
 * Exchanger is useful in applications such as genetic algorithms and pipeline designs.
 * The Exchanger class provides a method exchange() that waits for another thread to arrive at this exchange point
 * (unless the current thread is interrupted), and then transfers the given object to it, receiving its object in return.
 * When both threads have reached the exchange point, the objects are exchanged, and the exchange() method returns.
 * If the current thread is interrupted while waiting, the exchange is still performed, and the method throws
 * InterruptedException.
 * The exchange() method may also throw a checked BrokenBarrierException if another thread has interrupted the
 * current thread while the current thread was waiting, or if the barrier was reset before the current thread reached it.
 *
 */
public class LinkedBlockingQueueExchangerExample {
    public static void main(String[] args) {
        Exchanger<LinkedBlockingQueue<String>> exchanger = new Exchanger<>();
        Thread thread1 = new Thread(() -> {
            try {
                LinkedBlockingQueue<String> queue1 = new LinkedBlockingQueue<>();
                queue1.put("Data 1");
                System.out.println("Thread 1 sending: " + queue1);
                LinkedBlockingQueue<String> queue2 = exchanger.exchange(queue1);
                System.out.println("Thread 1 received: " + queue2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                LinkedBlockingQueue<String> queue2 = new LinkedBlockingQueue<>();
                queue2.put("Data 2");
                System.out.println("Thread 2 sending: " + queue2);
                LinkedBlockingQueue<String> queue1 = exchanger.exchange(queue2);
                System.out.println("Thread 2 received: " + queue1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }
}