package Conditions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int dataAvailable = 0;

    public void produceData() throws InterruptedException {
        lock.lock();
        try{
            System.out.println("Daten werden produziert");
            dataAvailable ++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consumeData() throws InterruptedException {
        lock.lock();
        try {
            while (dataAvailable == 0) {
                condition.await();
            }
            System.out.println("Daten werden konsumiert");
            dataAvailable --;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionExample example = new ConditionExample();
        Thread t1 = new Thread(() -> {
            try {
                example.produceData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                example.consumeData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1 = new Thread(t1);
        t2 = new Thread(t2);
        t1.start();
        t2.start();
        t1 = new Thread(t1);
        t2 = new Thread(t2);
        t1.start();
        t2.start();
        t1 = new Thread(t1);
        t2 = new Thread(t2);
        t1.start();
        t2.start();
    }
}


