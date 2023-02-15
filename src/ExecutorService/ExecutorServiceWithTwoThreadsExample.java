package ExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceWithTwoThreadsExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Callable<String> callableTask1 = () -> {
            return "Task 1 completed.";
        };
        Callable<String> callableTask2 = () -> {
            return "Task 2 completed.";
        };
        Future<String> future1 = executorService.submit(callableTask1);
        Future<String> future2 = executorService.submit(callableTask2);
        String result1 = future1.get();
        String result2 = future2.get();
        System.out.println(result1);
        System.out.println(result2);
        executorService.shutdown();
    }
}