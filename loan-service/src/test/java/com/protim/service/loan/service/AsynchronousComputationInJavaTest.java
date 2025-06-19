package com.protim.service.loan.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class AsynchronousComputationInJavaTest {

    List<Integer> generateListOfNIntegers(int N) {
        return IntStream.rangeClosed(1, N).boxed().toList();
    }

    /*
     * Here we are using a sequential stream.
     * Each number from the list of N numbers is squared and added to generate the sum of squares.
     * For the below operation it took 31 ms to complete:
     * OUTPUT: computeSumOfSquaresSequentially - Result: 333332833333500000	Time Taken(millis): 31
     */
    @Test
    void computeSumOfSquaresSequentially(){
        long startTimeMillis = System.currentTimeMillis();
        Long result = generateListOfNIntegers(10000000).stream().map(i -> (long) i * i).reduce(0L, Long::sum);
        System.out.println("computeSumOfSquaresSequentially - Result: " + result + "\tTime Taken(millis): " + (System.currentTimeMillis() - startTimeMillis));
    }

    /*
     * Here we are using a parallel stream.
     * The list of N numbers is converted to a stream and the stream is divided among T threads.
     * The Thread Pool used is the default fork-join pool, which has the default size T = N-1
     * where N is the number of cores this CPU has.
     *
     * For the below operation it took 49 ms to complete.
     * OUTPUT: computeSumOfSquaresParallel - Result: 333332833333500000	Time Taken(millis): 49
     *
     * We can see that even though the operation executed in multiple cores, dividing the work,
     * merging the results, managing the threads in the pool and other relevant operations create
     * a performance overhead, hence the delay.
     *
     * In simple scenarios, parallel streams should be avoided. There are multiple factors to be considered:
     * Splitting Cost - ArrayLists are faster to split than LinkedLists
     * Merging Cost - Sum operation can easily be merged
     * Memory - Primitives are faster than Objects
     * NQ Model - Oracle presented a simple model that can help us determine whether parallelism
     * can offer us a performance boost. In the NQ model, N stands for the number of source data
     * elements, while Q represents the amount of computation performed per data element.
     * The larger the product of N*Q, the more likely we are to get a performance boost from parallelization.
     * For problems with a trivially small Q, such as summing up numbers, the rule of thumb is that N
     * should be greater than 10,000. As the number of computations increases, the data size required to get
     * a performance boost from parallelism decreases.
     *
     * OUTPUT: computeSumOfSquaresParallel - Result: 333332833333500000	Time Taken(millis): 49
     */
    @Test
    void computeSumOfSquaresParallel(){
        long startTimeMillis = System.currentTimeMillis();
        Long resultParallel = generateListOfNIntegers(10000000).parallelStream().map(i -> (long) i * i).reduce(0L, Long::sum);
        System.out.println("computeSumOfSquaresParallel - Result: " + resultParallel + "\tTime Taken(millis): " + (System.currentTimeMillis() - startTimeMillis));
    }

}
