package Lock;


/**
 * SimpleLock is a simple implementation of a lock. It is not reentrant and does not support
 * multiple locks by the same thread. It is only intended to demonstrate the basic concepts of
 * a lock.
 */
public class SimpleLock {
    private boolean isLocked = false;
    private Thread lockingThread;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (Thread.currentThread() != lockingThread) {
            throw new RuntimeException("Lock is not Thread owner");
        }
        isLocked = false;
        notify();
    }

    public static void main(String[] args) {
        final SimpleLock lock = new SimpleLock();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("Thread 1: Lock acquired");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("Thread 1: Lock released");
                }
            }
        });

        Thread thread2 = new Thread(() -> {     // Example as Lambda Expression
            try {
                try {
                    System.out.println("Thread 2: Trying to acquire lock");
                    lock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2: Lock acquired");
            } finally {
                lock.unlock();
                System.out.println("Thread 2: Lock released");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
