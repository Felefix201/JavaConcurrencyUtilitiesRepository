package WaitNotifyNotifyAll;

import java.util.concurrent.Semaphore;

class ExampleOneSemaphore {

    // Eine IDE kann maximal 3 mal hintereinander coden, dann muss mindestens 1 test erfolgen, es können beliebig viele tests erfolgen
    // Gleiche Aufgabe nochmal mit Semaphoren
    // Gleiche Aufgabe nochmal mit Locks
    // Gleiche Aufgabe nochmal mit Blocking Queue

    IDES1 ide = new IDES1();

    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    ide.code();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }}
    };
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    ide.test();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }}
    };

    public static void main(String[] args) {
        ExampleOneWaitNotify k = new ExampleOneWaitNotify();

        new Thread(k.r1).start();
        new Thread(k.r2).start();
    }

}

class IDES1 {

    Semaphore code = new Semaphore(3);

    public synchronized void code() throws InterruptedException {
        code.acquire();
        sc();
        Thread.sleep(0500);
        dc();
    }

    public synchronized void test () throws InterruptedException {
        st();
        Thread.sleep(0500);
        dt();
        if (code.availablePermits() == 0) {
            code.release(3);
        }
        else if (code.availablePermits() == 1) {
            code.release(2);
        }
        else if (code.availablePermits() == 2) {
            code.release(1);
        }
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


//    IDE ide = new IDE();
//
//    Runnable r1 = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                ide.code();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    };
//    Runnable r2 = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                ide.test();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    };
//
//    public static void main(String[] args) {
//        ExampleOne k = new ExampleOne();
//        for (int i = 0; i < 5; i++) {
//            new Thread(k.r1).start();
//            new Thread(k.r2).start();
//        }
//    }
//}
//
//class IDE {
//
//    public static void code() throws InterruptedException {
//        sc();
//        Thread.sleep(1000);
//        dc();
//    }
//
//    public static void test () throws InterruptedException {
//        st();
//        Thread.sleep(1000);
//        dt();
//    }
//
//    private static void sc() {
//        System.out.println("Start coding");
//    }
//
//    private static void dc() {
//        System.out.println("Done coding");
//    }
//
//    private static void st() {
//        System.out.println("Start testing");
//    }
//
//    private static void dt() {
//        System.out.println("Done testing");
//    }
//}
