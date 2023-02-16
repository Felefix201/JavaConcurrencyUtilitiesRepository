package BlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferAsBlockingQueue {

    private BlockingQueue<Integer> dataQueue;

    public BufferAsBlockingQueue(int size) {
        dataQueue = new LinkedBlockingQueue<>(size);
    }

    public synchronized void put(int data) throws InterruptedException {
        while (dataQueue.remainingCapacity() == 0) {
            wait();
        }
        dataQueue.put(data);
        System.out.println("Produced: " + data);
        notifyAll();
    }

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
}

class Producer extends Thread {

    private BufferAsBlockingQueue buffer;

    public Producer(BufferAsBlockingQueue buffer) {
        this.buffer = buffer;
    }

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
