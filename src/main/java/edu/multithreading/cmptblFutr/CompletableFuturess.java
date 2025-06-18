package edu.multithreading.cmptblFutr;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFuturess {
    public static void main(String[] args) {
        /**
         *
         * Scenario 2: E-Commerce Order Processing with Parallel Tasks
         * In an e-commerce system, when a user places an order, multiple tasks must run concurrently:
         *
         * Validate the order.
         * Process the payment.
         * Update the inventory.
         * Send a confirmation email.
         * These tasks should execute asynchronously, and the system should proceed only when all are completed.
         * How would you implement this using CompletableFuture?"
         *
         * ***/

        CompletableFuture<Void> orderValidation = CompletableFuture.runAsync(() -> {
            simulateDelay(1000);
            System.out.println("Order validation completed");
        });
        CompletableFuture<Void> paymentValidation = CompletableFuture.runAsync(() ->{
            simulateDelay(1500);
            System.out.println("Payment validation completed");
        });
        CompletableFuture<Void> inventoryUpdate = CompletableFuture.runAsync(() -> {
            simulateDelay(2000);
            System.out.println("Inventory update completed");
        });
        CompletableFuture<Void> confirmEmail = CompletableFuture.runAsync(() -> {
            simulateDelay(2500);
            System.out.println("Confirm email completed");
        });
        // wait for all tasks to complete.
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(orderValidation, paymentValidation, inventoryUpdate, confirmEmail);
        allTasks.join();
        System.out.println("All tasks completed successfully. Order processing finished.");
        // Further Enhancement: If any task fails, we can handle it gracefully.
        handlingFailureAndEnhancement();
    }
    public static void simulateDelay(long millis) {
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    /**
     * Key Takeaways:
     * ✅ All tasks run in parallel → Reduced response time.
     * ✅ Non-blocking approach → Uses thread pools efficiently.
     * ✅ Ensures order consistency → Proceeds only after all tasks complete.
     *
     * Note — In real world the above steps run in sequence, validate >> payment >> inventory update >> confirmation .
     * If any of the step in betweek failed there is no point to execute next step. Here is the code to simulate that
     * ****/

    public static void handlingFailureAndEnhancement() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-1);

        CompletableFuture<Void> orderProcessingFlow = CompletableFuture.runAsync(
                () -> {
                    simulateDelay(1000);
                    System.out.println("Order processing completed");
                    // throw new RuntimeException("Order validation failed"); // Simulating failure
                }, executorService
        ).thenRunAsync(
                () -> {
                    simulateDelay(1500);
                    System.out.println("Payment processing completed");
                    // throw new RuntimeException("Payment processing failed"); // Simulating failure
                }, executorService
        ).thenRunAsync(
                () ->{
                    simulateDelay(2000);
                    System.out.println("Inventory update completed");
                    // throw new RuntimeException("Inventory update failed"); // Simulating failure
                }, executorService
        ).thenRunAsync(
                () -> {
                    simulateDelay(2500);
                    System.out.println("Confirmation email sent");
                    // throw new RuntimeException("Email sending failed"); // Simulating failure
                }, executorService
        );
        try{
            orderProcessingFlow.join();
            System.out.println("All tasks completed successfully. Order processing finished.");
        }catch (CompletionException ex){
            System.out.println("Order processing failed due to error: "+ex.getCause().getMessage());
        }
        executorService.shutdown();
    }
}
