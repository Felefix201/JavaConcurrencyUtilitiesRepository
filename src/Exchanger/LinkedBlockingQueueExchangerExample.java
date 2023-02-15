package Exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;

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