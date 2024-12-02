package org.example.common;

import java.util.List;
import java.util.Map;

public class Helpers {
    public static <T> void printList(List<T> list) {
        list.forEach(i -> System.out.print(i + ","));
        System.out.println();
    }

    public static <T> void printList(String name, List<T> list) {
        System.out.print(name + ": ");
        printList(list);
    }

    public static <K, V> void printMap(Map<K, V> map) {
        map.forEach((key, value) -> System.out.print(key + "=" + value + ", "));
        System.out.println();
    }

    public static <K, V> void printMap(String name, Map<K, V> map) {
        System.out.print(name + ": ");
        printMap(map);
    }
}
