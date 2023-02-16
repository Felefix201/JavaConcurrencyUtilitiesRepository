package WaitNotifyNotifyAll;

import java.util.Random;

public class Buffer {

    private int data;
    private boolean full = false;   //Flag

    public Buffer(int maxBufferSize) {
        if (maxBufferSize < 1) {
            throw new IllegalArgumentException("Buffer size must be greater than 0");
        }
    }

    public synchronized void put(int data) throws InterruptedException {
        while (full) {
            wait();
        }
        this.data = data;
        System.out.println("Produced: " + data + "...................................");
        full = true;
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (!full) {
            wait();
        }
        System.out.println("Consumed: " + data);
        full = false;
        notifyAll();
        return data;
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        Producer1 producer = new Producer1(buffer);
        Consumer1 consumer = new Consumer1(buffer);

        producer.start();
        consumer.start();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producer.interrupt();
        consumer.interrupt();
    }
}

class Producer1 extends Thread {

    private Buffer buffer;

    public Producer1(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!isInterrupted()) {
            try {
                buffer.put(random.nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Producer interupted");
    }
}

class Consumer1 extends Thread {

    private final Buffer buffer;

    public Consumer1(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                buffer.get();
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
                break;
            }
        }
        System.out.println("Consumer interupted");
    }
}
