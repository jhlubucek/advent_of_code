package org.example.common;

import lombok.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Helpers {
    public static <T> void printList(List<T> list) {
        list.forEach(System.out::print);
        System.out.println();
    }

    public static <T> void printList(List<T> list, String separator) {
        list.forEach( t -> System.out.print(t.toString()+separator));
        System.out.println();
    }

    public static <T> void printList(String name, List<T> list, String separator) {
        System.out.print(name + ": ");
        printList(list,separator);
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

    public static List<String> getRegexMatches(String regex, String input) {
        List<String> matches = new ArrayList<>();

        Matcher matcher = matchRegex(regex,input);

        while (matcher.find()) {
            String match = matcher.group();
            matches.add(match);
        }

        return matches;
    }

    public static Matcher matchRegex(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    //"\\s+"
    public static List<Integer> stringToIntList(String input, String separator) {
        input = input.stripLeading();
        String[] numbers = input.split(separator);
        List<Integer> list = new ArrayList<>();
        IntStream.range(0,numbers.length).forEach(i -> {
            list.add(Integer.parseInt(numbers[i]));
        });
        return list;
    }

    //"\\s+"
    public static List<Long> stringToLongList(String input, String separator) {
        input = input.stripLeading();
        String[] numbers = input.split(separator);
        List<Long> list = new ArrayList<>();
        IntStream.range(0,numbers.length).forEach(i -> {
            list.add(Long.parseLong(numbers[i]));
        });
        return list;
    }

    public static <K, V> K getMapKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value != null && value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder(toBuilder=true)
    @EqualsAndHashCode
    public static class Position{
        public int x;
        public int y;

        public Position(Position other) {
            this.x = other.x;
            this.y = other.y;
        }
    }

    public static <T> List<List<T>> deepCopy(List<List<T>> original) {
        if (original == null) {
            return null; // Return null if the original list is null
        }

        List<List<T>> copy = new ArrayList<>();
        for (List<T> innerList : original) {
            List<T> innerCopy = new ArrayList<>(innerList); // Create a new copy of each inner list
            copy.add(innerCopy);
        }
        return copy;
    }

    public static class MutableInt {
        public int value;

        public MutableInt(int value) {
            this.value = value;
        }
    }

    public static Boolean[][] createVisitedMatrix(Character[][] matrix) {
        Boolean[][] visitedMatrix = new Boolean[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            visitedMatrix[i] = new Boolean[matrix[i].length];
            // Initialize all elements in the row to `false`
            Arrays.fill(visitedMatrix[i], false);
        }

        return visitedMatrix;
    }

    public static int sum(int... numbers) {
        return IntStream.of(numbers).sum();
    }

    public static int sum(boolean... values) {
        return IntStream.range(0, values.length)
                .map(i -> values[i] ? 1 : 0)
                .sum();
    }

    public static boolean liesOnMap(Point p, Character[][] map) {
        return p.y >= 0 && p.y < map.length && p.x >= 0 && p.x < map[0].length;
    }
}
