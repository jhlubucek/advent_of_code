package org.example.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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
        map.forEach((key, value) -> System.out.print(key + ":" + value + ", "));
        System.out.println();
    }

    public static <K, V> void printMap(String name, Map<K, V> map) {
        System.out.print(name + ": ");
        printMap(map);
    }

    public static List<String> regexMatches(String regex, String input) {
        List<String> matches = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            matches.add(match);

        }

        return matches;
    }
    public static List<Integer> stringToIntList(String input) {
        return stringToIntList(input,"\\s+");
    }

    public static List<Integer> stringToIntList(String input, String separator) {
        String[] numbers = input.split(separator);
        List<Integer> list = new ArrayList<>();
        IntStream.range(0,numbers.length).forEach(i -> {
            list.add(Integer.parseInt(numbers[i]));
        });
        return list;
    }

}
