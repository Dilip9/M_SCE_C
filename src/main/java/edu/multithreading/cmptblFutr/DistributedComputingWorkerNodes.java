package edu.multithreading.cmptblFutr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static edu.multithreading.cmptblFutr.CompletableFuturess.simulateDelay;
import static java.lang.Runtime.getRuntime;

public class DistributedComputingWorkerNodes {

    /**
     * “A large dataset must be processed across multiple worker nodes in a distributed environment.
     * Each node processes a subset of data, and the final results are aggregated centrally.
     * How would you implement this?”
     *
     *
     * ****/
    private static final ExecutorService workerool = Executors.newFixedThreadPool(getRuntime().availableProcessors()-1);

    public static void main(String[] args) {
        List<Integer> dataset = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for(Integer chucnk: dataset){
            futures.add(CompletableFuture.supplyAsync(() -> processChunk(chucnk), workerool));
        }
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        CompletableFuture<List<Integer>> aggregatedResults = allTasks.thenApply(v ->
            futures.stream().map(CompletableFuture::join).toList());
        System.out.println("Aggregated results: " + aggregatedResults.join());
        workerool.shutdown();
    }

    private static Integer processChunk(Integer chuck){
        simulateDelay(1000);
        return chuck*2;
    }
}
