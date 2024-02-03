package Lock;

import java.util.concurrent.locks.ReentrantLock;


/**
 * ReentrantLock is a mutual exclusion lock with the same basic behavior as the implicit monitor lock accessed using
 * synchronized methods and statements, but with extended capabilities.
 * A ReentrantLock is owned by the thread last successfully locking, but not yet unlocking it.
 * A thread invoking lock will return, successfully acquiring the lock, when the lock is not owned by another thread.
 * The method will return immediately if the current thread already owns the lock.
 * This can be checked using methods isHeldByCurrentThread(), and getHoldCount().
 */
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
            // do something like a customer validation or so. But that is not important for this concept
        }
        finally {
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
