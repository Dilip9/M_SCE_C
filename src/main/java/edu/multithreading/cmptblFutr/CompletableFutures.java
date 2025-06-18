package edu.multithreading.cmptblFutr;

import java.util.concurrent.CompletableFuture;

public class CompletableFutures {

    public static void main(String[] args) {
        System.out.println("::::::::: CompletableFutures Example :::::::::");
        /**
         *
         * Scenarios 1. Stock Market Live Data Aggregation
         * "A financial application retrieves stock prices from three APIs and needs to compute the average stock price.
         * If any APIs fails, the system should return a default value for that source.
         * How would you implement this using CompletableFutures?"
         *
         *
         * *
         * * *
         * *
         *
         * **/

        CompletableFuture<Double> api1 = fetchStockPriceFromApi("API1");
        CompletableFuture<Double> api2 = fetchStockPriceFromApi("API2");
        CompletableFuture<Double> api3 = fetchStockPriceFromApi("API3");
        CompletableFuture<Double> averagePrice = api1.thenCombine(api2, Double::sum).thenCombine(api3, (sum, price) -> (sum + price)/3);
        System.out.println(" Final Stock price is : $" + averagePrice.join());



        /**
         *  Scenario 2: E-Commerce Order Processing with parallel Tasks
         *
         *
         *
         * **/
    }

    public static CompletableFuture<Double> fetchStockPriceFromApi(String apiName) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate API call
            try {
                Thread.sleep(1000); // Simulating network delay
                if (Math.random() > 0.8) { // Simulating a failure
                    throw new RuntimeException("Failed to fetch data from " + apiName);
                }
                return Math.random() * 100; // Simulated stock price
            } catch (InterruptedException e) {
                throw new RuntimeException("API call interrupted", e);
            }
        }).exceptionally(ex -> {
            System.err.println("Error fetching data from " + apiName + ": " + ex.getMessage());
            return 100.0; // Default value in case of failure
        });
    }


}