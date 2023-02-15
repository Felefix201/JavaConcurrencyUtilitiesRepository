package Stream;

import java.util.Arrays;
import java.util.List;

public class StreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
                .filter(n -> n % 2 == 0)    // result = (2,4)
                .map(n -> n * n)            // restult = (2*2, 4*4)
                .forEach(System.out::println);
    }
}
