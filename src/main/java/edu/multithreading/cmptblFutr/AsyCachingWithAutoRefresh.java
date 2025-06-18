
package edu.multithreading.cmptblFutr;

import java.util.concurrent.*;

import static edu.multithreading.cmptblFutr.CompletableFuturess.simulateDelay;

public class AsyCachingWithAutoRefresh {

    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    public static CompletableFuture<String> getData(String key){
        return CompletableFuture.supplyAsync(() -> cache.computeIfAbsent(key, AsyCachingWithAutoRefresh::fetchFromDatabase));
    }
    private static String fetchFromDatabase(String key){
        simulateDelay(5000);
        return "Data for " + key + " (fetched from DB)";
    }
    public static void scheduleAutoRefresh(){
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Auto refresh started");
            cache.replaceAll((k, v) -> fetchFromDatabase(k));
        }, 10, 10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        /**
         *
         * A high-traffic application uses an in-memory cache for frequently accessed data (e.g., product details).
         * The cache should:
         * 1. Fetch fresh data from the database only when stale.
         * 2. Auto-refresh data every 10 minutes asynchronously without blocking requests.
         * 3. How would you implement this using CompletableFuture?"_
         *
         * Solution:
         *
         *  The first request fetches from DB and caches it.
         *  Future requests use the cached version.
         *  A background task auto-refreshes every 10 minutes.
         * **/
        scheduleAutoRefresh();
        getData("product123").thenAccept(System.out::println);
        getData("product456").thenAccept(System.out::println);

    }
    /**
     * Key Takeaways:
     * ✅ Reduces load on DB by caching frequently used data.
     * ✅ Non-blocking auto-refresh mechanism keeps cache up-to-date.
     * ✅ Ensures high availability in distributed caching systems.
     *
     *
     * ***/
}
