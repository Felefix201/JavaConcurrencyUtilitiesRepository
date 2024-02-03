package BlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * This class is an example of using BlockingQueue as a buffer.
 * The BufferAsBlockingQueue class has a BlockingQueue of Integer type.
 * The producer and consumer threads are created and started in the main method.
 * The producer thread puts the data into the queue and the consumer thread gets the data from the queue.
 * The producer and consumer threads are interrupted after 1 second
 */
public class BufferAsBlockingQueue {
    // final BlockingQueue due to the fact that it does not change the length after initialization
    private final BlockingQueue<Integer> dataQueue;

    public BufferAsBlockingQueue(int size) {
        dataQueue = new LinkedBlockingQueue<>(size);
    }

    /**
     * The main method creates the BufferAsBlockingQueue object and the producer and consumer threads.
     * The producer and consumer threads are started and then interrupted after 1 second.
     */
    public static void main(String[] args) {
        BufferAsBlockingQueue buffer = new BufferAsBlockingQueue(3);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.interrupt();
        consumer.interrupt();
        System.out.println("Main interupting Producer & Consumer");
    }

    /**
     * The put() method is used to put the data into the queue.
     * The put() method waits if the queue is full.
     */
    public synchronized void put(int data) throws InterruptedException {
        while (dataQueue.remainingCapacity() == 0) {
            wait();
        }
        dataQueue.put(data);
        System.out.println("Produced: " + data);
        notifyAll();
    }

    /**
     * The get() method is used to get the data from the queue.
     * The get() method waits if the queue is empty.
     */
    public synchronized int get() throws InterruptedException {
        while (dataQueue.size() == 0) {
            wait();
        }
        int data = dataQueue.poll();
        System.out.println("Consumed: " + data);
        try{
            return data;
        } finally {
            notifyAll();
        }
    }
}

/**
 * The Producer and Consumer classes are the threads that produce and consume the data from the queue.
 */
class Producer extends Thread {
    private final BufferAsBlockingQueue buffer;

    public Producer(BufferAsBlockingQueue buffer) {
        this.buffer = buffer;
    }

    // The run() method is used to put the data into the queue.
    @Override
    public void run() {
        Random random = new Random();
        while (!isInterrupted()) {
            try {
                buffer.put(random.nextInt());
            } catch (InterruptedException e) {
                System.out.println("Producer interupted");
            }
        }
    }
}

/**
 * The Consumer class is the thread that consumes the data from the queue.
 */
class Consumer extends Thread{
    private final BufferAsBlockingQueue buffer;

    public Consumer(BufferAsBlockingQueue buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                buffer.get();
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }
    }
}
