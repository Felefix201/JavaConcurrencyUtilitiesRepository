package GenerischeArrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * This class demonstrates the use of ArrayBlockingQueue with a generic type.
 * The ArrayBlockingQueue is a bounded blocking queue that stores the elements internally in an array.
 * It is a thread-safe queue and implements the BlockingQueue interface.
 * The ArrayBlockingQueue is a bounded queue, which means it has a fixed size.
 * Once the queue is full, any attempt to add an element to the queue will block the thread until the queue has
 * space to store the element.
 * Similarly, if the queue is empty, any attempt to remove an element from the queue will block the thread until
 * the queue has an element to remove.
 * The ArrayBlockingQueue is a good choice when you need a bounded blocking queue.
 */
public class GenericArrayBlockingQueueExample {
    private static class Producer implements Runnable {
        private final ArrayBlockingQueue<String> queue;
        public Producer(ArrayBlockingQueue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                String item = "Item Produced";
                System.out.println("Producer: Adding " + item + " to the queue");
                queue.put(item);
                System.out.println("Producer: " + item + " added to the queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class Consumer implements Runnable {
        private final ArrayBlockingQueue<String> queue;
        public Consumer(ArrayBlockingQueue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                String item = queue.take();
                System.out.println("Consumer: " + item + " removed from the queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
    }
}
