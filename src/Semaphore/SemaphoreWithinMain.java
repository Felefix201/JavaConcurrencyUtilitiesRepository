package Semaphore;

public class SemaphoreWithinMain {

    //private static BinarySemaphore bestellungAufnehmen = new BinarySemaphore();
    private static BinarySemaphore bestellungOrdern = new BinarySemaphore();
    private static BinarySemaphore bestellungEinlagern = new BinarySemaphore();

    public static void main(String[] args) {
        new Thread (() -> {
            System.out.println("Bestellung wurde aufgenommen");
            bestellungOrdern.release();
        }).start();

        new Thread (() -> {
            try{
                bestellungOrdern.acquire();
                System.out.println("Bestellung wurde aufgegeben");
                bestellungEinlagern.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread (() -> {
            try{
                bestellungEinlagern.acquire();
                System.out.println("Bestellung wurde eingelagert");

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