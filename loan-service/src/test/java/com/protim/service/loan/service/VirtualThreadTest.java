package com.protim.service.loan.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class VirtualThreadTest {

    @Test
    void memoryTestVirtual(){
        // .
        // .
        // .
        // Completed. Result = 385
        // Completed. Result = 385
        // Completed. Result = 385
        // Completed. Result = 385
        // Completed. Result = 385
        // Total time taken: 2146 ms
        long start = System.currentTimeMillis();
        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()){
            long numberOfTasks = 10_000L;
            List<CompletableFuture<Integer>> taskList = new ArrayList<>();
            for(var i = 0; i < numberOfTasks; i++) {
                taskList.add(CompletableFuture.supplyAsync(task, executor));
            }

            taskList.forEach(t -> {
                try {
                    int result = t.get(); // Blocks the main thread and waits for t to finish
                    System.out.println("Completed. Result = " + result);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("Total time taken: " + (System.currentTimeMillis() - start) + " ms");
    }

    @Test
    void memoryTestPlatformFixedThreadPool(){
        // Output:
        // pool-1-thread-20152045 started...
        // pool-1-thread-20162046 started...
        // pool-1-thread-20172047 started...
        // [0.446s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 2048k, guardsize: 16k, detached.
        // [0.446s][warning][os,thread] Failed to start the native thread for java.lang.Thread "pool-1-thread-2018"
        long start = System.currentTimeMillis();
        try(ExecutorService executor = Executors.newFixedThreadPool(10_000)){
            long numberOfTasks = 10_000L;
            List<CompletableFuture<Integer>> taskList = new ArrayList<>();
            for(var i = 0; i < numberOfTasks; i++) {
                taskList.add(CompletableFuture.supplyAsync(task, executor));
            }

            taskList.forEach(t -> {
                try {
                    int result = t.get(); // Blocks the main thread and waits for t to finish
                    System.out.println("Completed. Result = " + result);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("Total time taken: " + (System.currentTimeMillis() - start) + " ms");
    }

    @Test
    void memoryTestPlatformForkJoinPool(){
        // Output:
        // ForkJoinPool.common pool-worker Threads
        long start = System.currentTimeMillis();
        long numberOfTasks = 10_000L;
        List<CompletableFuture<Integer>> taskList = new ArrayList<>();
        for(var i = 0; i < numberOfTasks; i++) {
            taskList.add(CompletableFuture.supplyAsync(task));
        }

        taskList.forEach(t -> {
            try {
                int result = t.get(); // Blocks the main thread and waits for t to finish
                System.out.println("Completed. Result = " + result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Total time taken: " + (System.currentTimeMillis() - start) + " ms");
    }

    Supplier<Integer> task = () -> {
        try {
            // Simulate I/O operation
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().threadId() + " started...");
            Thread.sleep(2000);
            return sumOfSquares(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    public static int sumOfSquares(int n){
        if(n==0) return 0;
        return IntStream.rangeClosed(1, n).boxed().map(i -> i*i).reduce(0, Integer::sum);
    }

    @Test
    void sumOfSquaresTest(){
        Assertions.assertEquals(0, sumOfSquares(0));
        Assertions.assertEquals(1, sumOfSquares(1));
        Assertions.assertEquals(14, sumOfSquares(3));
        Assertions.assertEquals(30, sumOfSquares(4));
    }


}


