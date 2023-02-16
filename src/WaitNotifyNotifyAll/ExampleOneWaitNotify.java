package WaitNotifyNotifyAll;

class ExampleOneWaitNotify {

    // Eine IDE kann maximal 3 mal hintereinander coden, dann muss mindestens 1 test erfolgen, es können beliebig viele tests erfolgen
    // Gleiche Aufgabe nochmal mit Semaphoren
    // Gleiche Aufgabe nochmal mit Locks
    // Gleiche Aufgabe nochmal mit Blocking Queue

    IDE ide = new IDE();

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

class IDE {

    private int codeCount = 0;

    public synchronized void code() throws InterruptedException {
        while (codeCount > 2){
            wait();
        }
        codeCount++;
        sc();
        Thread.sleep(0500);
        dc();
    }

    public synchronized void test () throws InterruptedException {
        st();
        Thread.sleep(0500);
        dt();
        notify();
        codeCount = 0;
    }

    private static void sc() {
        System.out.println("Start coding............................................");
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
