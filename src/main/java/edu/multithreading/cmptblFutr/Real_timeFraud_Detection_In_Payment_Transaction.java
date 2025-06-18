package edu.multithreading.cmptblFutr;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Real_timeFraud_Detection_In_Payment_Transaction {
    /***
     *  A banking system must analyze multiple parameters asynchronously (transaction amount, location, past history)
     *  and flag suspicious transactions if a fraud score exceeds a threshold.
     *  How would you implement this?
     *
     *  Solution:
     *  Use multiple CompletableFutures to compute fraud risk factors asynchronously.
     *  Combine results using thenCombine.
     *  Use Threshold to determine if a transaction is flagged as fraudulent.
     *
     * **/
    public static void main(String[] args) {

        CompletableFuture<Double> amountRisk = analyzeAmountRisk(5000.00);
        CompletableFuture<Double> locationRisk = analyzeLocationRisk("Auckland");
        CompletableFuture<Double> historyRisk = analyzeHistoryRisk("user123");
        CompletableFuture<Double> fraoudScore = amountRisk
                .thenCombine(locationRisk, Double::sum)
                .thenCombine(historyRisk, Double::sum);
        fraoudScore.thenAccept(score -> {
            if(score > 7.5){
                System.out.println("Transaction flagged as fraudulent with score: " + score);
            } else {
                System.out.println("Transaction is safe with score: " + score);
            }
        });
    }
    public static CompletableFuture<Double> analyzeAmountRisk(double amount) {
        return CompletableFuture.supplyAsync(() -> amount > 3000.00 ? 4.0 : 1.0);
    }

    public static CompletableFuture<Double> analyzeLocationRisk(String location){
        return CompletableFuture.supplyAsync(() -> location.equals("Auckland") ? 3.0 : 1.0);
    }

    public static CompletableFuture<Double> analyzeHistoryRisk(String userId) {
        return CompletableFuture.supplyAsync(() -> new Random().nextDouble() * 3.0);
    }
}
