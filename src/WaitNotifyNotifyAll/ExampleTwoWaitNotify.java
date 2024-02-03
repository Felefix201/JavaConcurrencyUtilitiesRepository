package WaitNotifyNotifyAll;


/**
 * IDE can code and test, but it can only code 3 times in a row, then it has to test at least once, and it can only
 * test 5 times in a row, then it has to code at least once.
 */
public class ExampleTwoWaitNotify {
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
        ExampleTwoWaitNotify k = new ExampleTwoWaitNotify();

        new Thread(k.r1).start();
        new Thread(k.r2).start();

    }

}
class IDE2 {

    private int codeCount = 0;
    private int testCount = 0;

    public synchronized void code() throws InterruptedException {
        while(codeCount > 2){
            wait();
        }
        codeCount++;
        sc();
        Thread.sleep(0500);
        dc();
        testCount = 0;
        notifyAll();
    }

    public synchronized void test () throws InterruptedException {
        while(testCount > 4){
            wait();
        }
        testCount++;
        st();
        Thread.sleep(0500);
        dt();
        codeCount = 0;
        notifyAll();
    }

    private static void sc() {
        System.out.println("Start coding...........................");
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