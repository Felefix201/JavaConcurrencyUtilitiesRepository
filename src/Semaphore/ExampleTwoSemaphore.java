package Semaphore;

import java.util.concurrent.Semaphore;

class ExampleTwoSemaphore {

    IDES2 ide = new IDES2();

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

class IDES2 {

    Semaphore code = new Semaphore(3);
    Semaphore test = new Semaphore(1);
    public void code() throws InterruptedException {
        code.acquire();
        test.acquire();
        sc();
        Thread.sleep(0500);
        dc();
        test.release();
    }

    public void test() throws InterruptedException {
        test.acquire();
        st();
        Thread.sleep(0500);
        dt();
        if(code.availablePermits() == 0){
            code.release(3);

        }
        test.release();
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
