package GenerischeArrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class GenericArrayBlockingQueueExample {
    private static class Producer implements Runnable {
        private ArrayBlockingQueue<String> queue;
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
        private ArrayBlockingQueue<String> queue;
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
