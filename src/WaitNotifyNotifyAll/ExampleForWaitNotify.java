package WaitNotifyNotifyAll;


/**
 * This class is an example for the usage of wait and notifyAll.
 * The IDE4 class has two methods code and test. The code method can be called exactly 2 times before the test
 * method can be called.
 * The test method can be called as often as you want, but only if the code method has been called exactly 2 times.
 * The code method is synchronized and the test method is synchronized. The code method has a while loop that checks
 * if the codeCount is greater or equal to 2.
 * If this is the case, the thread waits. The test method has a while loop that checks if the testPossible is false.
 */
public class ExampleForWaitNotify {
    IDE4 ide = new IDE4();

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
        ExampleForWaitNotify k = new ExampleForWaitNotify();

        new Thread(k.r1).start();
        new Thread(k.r2).start();

    }

}
class IDE4 {

    private int codeCount = 0;
    private boolean testPossible = false;

    // code exactly 2 times
    public synchronized void code() throws InterruptedException {
        while(codeCount >= 2){
            wait();
        }
        codeCount++;
        testPossible = false;
        sc();
        Thread.sleep(0500);
        dc();
        if (codeCount == 2){
            testPossible = true;
            notifyAll();
        }

    }
    // test as often as you want
    public synchronized void test () throws InterruptedException {
        while(!testPossible){
            wait();
        }
        st();
        Thread.sleep(0500);
        dt();
        codeCount = 0;
        notifyAll();
    }

    private static void sc() {
        System.out.println("Start coding...................");
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