package Semaphore;


/**
 * SemaphoreWithinMain is a class that demonstrates the use of a binary semaphore within the main method.
 * The main method creates a binary semaphore and starts three threads. The first thread simulates the
 * taking of an order, the second thread simulates the ordering of the order and the third thread simulates
 * the storage of the order. The first thread releases the binary semaphore after the order has been taken.
 * The second thread acquires the binary semaphore and releases it after the order has been placed. The third
 * thread acquires the binary semaphore and releases it after the order has been stored.
 */
public class SemaphoreWithinMain {

    //private static BinarySemaphore bestellungAufnehmen = new BinarySemaphore();
    private static BinarySemaphore purchaseOrdering = new BinarySemaphore();
    private static BinarySemaphore storePurchasedGoods = new BinarySemaphore();

    public static void main(String[] args) {
        new Thread (() -> {
            System.out.println("purchase order taken");
            purchaseOrdering.release();
        }).start();

        new Thread (() -> {
            try{
                purchaseOrdering.acquire();
                System.out.println("purchase order placed");
                storePurchasedGoods.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread (() -> {
            try{
                storePurchasedGoods.acquire();
                System.out.println("purchase order stored");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class BinarySemaphore extends CountingSemaphore {
    public BinarySemaphore(){
        super(0);
    }
}

class CountingSemaphore {
    private int count;

    public CountingSemaphore(int count) {
        this.count = count;
    }

    public synchronized void acquire() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        count--;
    }

    public synchronized void release() {
        count++;
        notify();
    }
}