package edu.multithreading.fni;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CollectionBasedInterview {
    public static void main(String[] args) {
        // find duplicate String in an array using Java 8 streams
        String[] str = {"abs", "b", "c", "d", "e", "f", "g", "h", "i", "j", "abs", "d", "g"};

        Arrays.stream(str).
                collect(Collectors.groupingBy(s -> s, Collectors.counting())).
                entrySet().
                stream().
                filter(entry -> entry.getValue() > 1).
                forEach(entry -> System.out.println("Duplicate String: " + entry.getKey() + ", Count: " + entry.getValue()));


        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] arr2 = {2,6,8,14};
        // find intersection of two arrays using Java 8 streams
        Arrays.stream(arr1).filter(n -> Arrays.stream(arr2).anyMatch(m -> m ==n)).
                forEach(n -> System.out.println("Intersection: " + n));

        // find using best approach and more precise ways

    }
}
