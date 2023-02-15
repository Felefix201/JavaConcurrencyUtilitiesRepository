package WaitNotifyNotifyAll;

public class ExampleThreeWaitNotify {

    // Code genau 2 mal und teste dann 4 mal

    IDE3 ide = new IDE3();

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
        ExampleThreeWaitNotify k = new ExampleThreeWaitNotify();

        new Thread(k.r1).start();
        new Thread(k.r2).start();

    }

}
class IDE3 {

    private int codeCount = 0;
    private int testCount = 0;

    public synchronized void code() throws InterruptedException {
        while(codeCount >= 2){
            wait();
        }
        codeCount++;
        sc();
        Thread.sleep(0500);
        dc();
        if(codeCount == 2){
            testCount = 0;
            notifyAll();
        }
    }

    public synchronized void test () throws InterruptedException {
        while(testCount > 3 || codeCount < 2){
            wait();
        }
        testCount++;
        st();
        Thread.sleep(0500);
        dt();
        if(testCount == 3) {
            codeCount = 0;
            notifyAll();
        }
    }

    private static void sc() {
        System.out.println("Start coding........................");
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