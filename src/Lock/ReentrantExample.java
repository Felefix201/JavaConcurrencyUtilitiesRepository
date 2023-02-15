package Lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {

    private final ReentrantLock lock = new ReentrantLock();

    public void Kundenanlage() throws InterruptedException {
        lock.lock();
        System.out.println("Kunde wurde angelegt");
        try {
            Kundenpruefung();
        } finally {
            lock.unlock();
        }
    }

    public void Kundenpruefung() throws InterruptedException {
        lock.lock();
        System.out.println("Kundendaten wurden geprüft");
        try {
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantExample example = new ReentrantExample();
        example.Kundenanlage();
        System.out.println("Kundenanlage abgeschlossen");
        example.Kundenanlage();
        System.out.println("Kundenablage 2 ist abgeschlossen");
    }
}
