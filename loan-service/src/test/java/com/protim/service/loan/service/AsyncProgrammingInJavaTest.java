package com.protim.service.loan.service;

import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class AsyncProgrammingInJavaTest {

    /*
     * Notice how Future is created.
     * At first, it needs an executor service to be created
     * This executor MUST be closed explicitly at the end. Here we are using try with resources to close it.
     *
     * Then, you must provide a Callable object via the executor.submit() method.
     * Here we have used an arrow function to achieve that. We are creating a container and setting
     * the first entry as 10.
     *
     * Future does not support method chaining. Hence, applying subsequent actions need you to first
     * retrieve the future result using get(), and then proceed with further operations.
     * Thus, in this case to set the second entry, we are required to retrieve the future using get().
     *
     */
    @Test
    void checkFuture() throws Exception {
        try(ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<Container> future = executor.submit(
                    () -> Container.builder().dataPointOne(10).build()
            ); // TASK submitted at this stage
            Container retrievedContainer = future.get();
            Assertions.assertEquals(10, retrievedContainer.getDataPointOne());
            Assertions.assertEquals(0, retrievedContainer.getDataPointTwo());
            // Now you can set the second data point to 20...
        }
    }

    /*
     * For Completable Future, we DO NOT need to declare an executor service.
     * Hence, try or try with resources is not needed.
     *
     * To submit the task, you must provide a Supplier. Here we are doing it using arrow function.
     * As soon as you provide the supplier, the TASK is submitted.
     * Now you have the liberty to apply the subsequent actions. We are setting the next data point
     * using thenApply().
     * Completable Future, thus supports method chaining feature of functional programming.
     *
     * It is worth noting that get() is a BLOCKING call for both.
     */
    @Test
    void checkCompletableFuture() throws Exception {
        CompletableFuture<Container> completableFuture = CompletableFuture
                .supplyAsync(
                        () -> Container.builder().dataPointOne(10).build()
                ) // TASK submitted at this stage
                .thenApply(container -> {
                    container.setDataPointTwo(20);
                    return container;
                });
        Container retrievedContainer = completableFuture.get();
        Assertions.assertEquals(10, retrievedContainer.getDataPointOne());
        Assertions.assertEquals(20, retrievedContainer.getDataPointTwo());
    }
}


@Data
@Builder
class Container {
    int dataPointOne;
    int dataPointTwo;
}
