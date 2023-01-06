package WaitNotifyNotifyAll;

public class ExampleThree {

    // Eine IDE kann beliebig oft hintereinander arbeiten, dann muss abschließend mindestens 1 test erfolgen, es können beliebig viele tests erfolgen
    // Gleiche Aufgabe nochmal mit Semaphoren
    // Gleiche Aufgabe nochmal mit Locks
    // Gleiche Aufgabe nochmal mit Blocking Queue

    public static void main(String[] args) {

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
