package Semaphore;

import java.util.Random;
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
        ExampleTwoSemaphore k = new ExampleTwoSemaphore();

        new Thread(k.r1).start();
        new Thread(k.r2).start();
    }
}

class IDES2 {

    Semaphore code = new Semaphore(4);
    Semaphore test = new Semaphore(0);
    // genau 4 mal coden
    public void code() throws InterruptedException {
        code.acquire();
        sc();
        Thread.sleep(0500);
        dc();
        test.release();
    }
    // 1 - unendlich oft testen
    public void test() throws InterruptedException {
        test.acquire(4);
        st();
        Thread.sleep(0500);
        dt();
        Random r = new Random();
        int temp = r.nextInt(100) + 1;
        if (temp > 80){
            code.release(4);
        } else {
            test.release(4);
        }
    }


//    Funktioniert wundervoll aber musste zum testen vom anderen code auskommentiert werden
//    Semaphore code = new Semaphore(3);
//    Semaphore test = new Semaphore(1);
//    // 1 - 3 mal coden
//    public void code() throws InterruptedException {
//        code.acquire();
//        test.acquire();
//        sc();
//        Thread.sleep(0500);
//        dc();
//        test.release();
//    }
//    // unendlich oft testen
//    public void test() throws InterruptedException {
//        test.acquire();
//        st();
//        Thread.sleep(0500);
//        dt();
//        if(code.availablePermits() == 0){
//            code.release(3);
//        }
//        test.release();
//    }

    private static void sc() {
        System.out.println("Start coding..................");
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
