package WaitNotifyNotifyAll;

public class ExampleTwo {

    // Eine IDE kann maximal 3 mal hintereinander arbeiten, dann muss mindestens 1 test erfolgen, es können max 10 tests erfolgen
    // Gleiche Aufgabe nochmal mit Semaphoren
    // Gleiche Aufgabe nochmal mit Locks
    // Gleiche Aufgabe nochmal mit Blocking Queue

    IDE2 ide = new IDE2();
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
class IDE2 {

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