package org.example.days;

import lombok.AllArgsConstructor;
import org.example.dataReaders.LineDataReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day10 extends Day{
    List<List<Node>> map = new ArrayList<>();
    List<Point> starts = new ArrayList<>();

    public Day10() {
        List<String> input = LineDataReader.getLines("day10", INPUT,false);
        for (int i = 0; i < input.size(); i++) {
            List<Node> row = new ArrayList<>();
            for (int j = 0; j < input.get(i).length(); j++) {
                char c = input.get(i).charAt(j);
                row.add(new Node((Character.isDigit(c) ? Character.getNumericValue(c) : -1), false));
                if (c == '0') {
                    starts.add(new Point(j, i));
                }
            }
            map.add(row);
        }
    }

    @Override
    public void task1() {
        List<Point> ends = new ArrayList<>();
        for (Point start : starts) {
            ends.addAll(findEnds(map,start).stream().distinct().toList());
        }
        System.out.println(ends.stream().toArray().length);
    }

    public static List<Point> findEnds(List<List<Node>> map, Point start){
        Node node = map.get(start.y).get(start.x);
        if (node.value == 9) {
            return List.of(start);
        }
        List<Point> ends = new ArrayList<>();
        suroundingPoints(map, start).stream()
                .filter(point -> map.get(point.y).get(point.x).value == node.value + 1) // Filter nodes meeting the condition
                .flatMap(point -> findEnds(map, new Point(point.x, point.y)).stream()) // Find ends and flatten the stream
                .forEach(ends::add);
        return ends;
    }

    public static List<Point> suroundingPoints(List<List<Node>> map, Point point){
        List<Point> neighbors = List.of(
                new Point(point.x, point.y - 1), // Up
                new Point(point.x, point.y + 1), // Down
                new Point(point.x - 1, point.y), // Left
                new Point(point.x + 1, point.y)  // Right
        );
        return neighbors.stream()
                .filter(neighbor -> isOnMap(map, neighbor))
                .toList();
    }

    public static boolean isOnMap(List<List<Node>> map, Point point){
        return point.y >= 0 && point.y < map.size() && point.x >= 0 && point.x < map.get(point.y).size();
    }

    @Override
    public void task2() {
        List<Point> ends = new ArrayList<>();
        for (Point start : starts) {
            ends.addAll(findEnds(map,start).stream().toList());
        }
        System.out.println(ends.stream().toArray().length);
    }

    @AllArgsConstructor
    public class Node{
        public int value;
        public boolean visited;
    }
}
