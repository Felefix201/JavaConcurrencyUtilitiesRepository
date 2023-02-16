package Semaphore;

import java.util.concurrent.Semaphore;

class ExampleOneSemaphore {

    IDES1 ide = new IDES1();

    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    ide.code();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    ide.test();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    public static void main(String[] args) {
        ExampleOneSemaphore k = new ExampleOneSemaphore();

        new Thread(k.r1).start();
        new Thread(k.r2).start();
    }
}

class IDES1 {

    Semaphore code = new Semaphore(3);
    Semaphore test = new Semaphore(0);
    // 1-3 mal coden
    public void code() throws InterruptedException {
        code.acquire();
        sc();
        Thread.sleep(0500);
        dc();
        test.release();
    }
    // genau 1 test
    public void test() throws InterruptedException {
        test.acquire(3);
        st();
        Thread.sleep(0500);
        dt();
        code.release(3);
    }

    private static void sc() {
        System.out.println("Start coding");
    }

    private static void dc() {
        System.out.println("Done coding");
    }

    private static void st() {
        System.out.println("Start testing");
    }

    private static void dt() {
        System.out.println("Done testing");
    }
}
