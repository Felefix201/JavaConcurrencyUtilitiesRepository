package WaitNotifyNotifyAll;

public class ExampleForeWaitNotify {

    // Code genau 2 mal und teste dann beliebig oft

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
        ExampleForeWaitNotify k = new ExampleForeWaitNotify();

        new Thread(k.r1).start();
        new Thread(k.r2).start();

    }

}
class IDE4 {

    private int codeCount = 0;
    private boolean testPossible = false;

    // Code genau 2 mal
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
    // Teste beliebig oft
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