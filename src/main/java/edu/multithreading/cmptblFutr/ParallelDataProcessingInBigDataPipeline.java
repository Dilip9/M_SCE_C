package edu.multithreading.cmptblFutr;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ParallelDataProcessingInBigDataPipeline {
    /****
     * A data pipeline receives raw sensor data from IoT devices and must process it in parallel through multiple stages:
     *
     * Cleanse the data (remove duplicates, invalid values).
     * Enrich the data (add metadata, location info).
     * Aggregate the data (compute averages, detect anomalies).
     * How would you implement this efficiently using CompletableFuture?"_
     *
     * Solution:
     * Each stage runs independently in parallel using supplyAsync().
     * The final result is computed using thenCompose() to ensure dependent transformations.
     * ***/

    public static void main(String[] args) {
        List<String> rawSensorData = Arrays.asList("Sensor1:45", "Sensor2:50", "Sensor1:45", "Sensor3:60", "Sensor2:50", "InvalidData", "Sensor3:60");
        CompletableFuture<List<String>> cleanedData = CompletableFuture.supplyAsync(() -> cleanseData(rawSensorData));
        CompletableFuture<List<String>> enrichedData = cleanedData.thenCompose(data -> CompletableFuture.supplyAsync(() -> enrichData(data)));
        CompletableFuture<Map<String, Double>> aggregatedData = enrichedData.thenCompose(data -> CompletableFuture.supplyAsync(() -> aggregateData(data)));
        System.out.println("Final Processed Data: "+aggregatedData.join());
    }

    public static List<String> enrichData(List<String> rawSensorData) {
        return rawSensorData.stream().map(d -> d + " (Location: NY)").toList();
    }

    public static List<String> cleanseData(List<String> rawSensorData) {
        return rawSensorData.stream().filter(d -> d.contains(":")).distinct().toList();
    }

    public static Map<String, Double> aggregateData(List<String> rawSensorData) {
        Map<String, List<Integer>> grouped = new HashMap<>();
        for(String s : rawSensorData) {
            String[] split = s.split(":");
            grouped.computeIfAbsent(split[0], k -> new ArrayList<>()).add(Integer.parseInt(split[1].split(" ")[0]));
        }
        Map<String, Double> averages = new HashMap<>();
        grouped.forEach((sensor, readings) -> {
            averages.put(sensor, readings.stream().mapToInt(Integer::intValue).average().orElse(0.0));
        });
        return averages;

    }
}
