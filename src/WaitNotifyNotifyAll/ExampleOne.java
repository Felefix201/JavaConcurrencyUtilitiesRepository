package WaitNotifyNotifyAll;

class ExampleOne {

    // Eine IDE kann maximal 3 mal hintereinander coden, dann muss mindestens 1 test erfolgen, es können beliebig viele tests erfolgen
    // Gleiche Aufgabe nochmal mit Semaphoren
    // Gleiche Aufgabe nochmal mit Locks
    // Gleiche Aufgabe nochmal mit Blocking Queue

    public static void main(String[] args) {
        thread1 {
            try {
                IDE.code();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread2 {
            try {
                IDE.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
class IDE {

    public static void code() throws InterruptedException {
        sc();
        Thread.sleep(1000);
        dc();
    }

    public static void test () throws InterruptedException {
        st();
        Thread.sleep(1000);
        dt();
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
