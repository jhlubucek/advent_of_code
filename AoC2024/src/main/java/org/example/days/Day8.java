package org.example.days;

import org.example.dataReaders.LineDataReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day8 extends Day{
    @Override
    public void task1() {
        List<String> input = LineDataReader.getLines("day8", INPUT,false);

        Map<Character, List<Point>> map = new HashMap<>();

        int xMax = 0;
        int yMax = 0;

        for(int i = 0; i < input.size(); i++){
            for(int j = 0; j < input.get(i).length(); j++){
                Character c = input.get(i).charAt(j);
                if (!c.equals('.')) {
                    if (map.containsKey(c)) {
                        map.get(c).add(new Point(j, i));
                    } else {
                        map.put(c, new ArrayList<>());
                        map.get(c).add(new Point(j, i));
                    }
                }
                xMax=j;
            }
            yMax=i;
        }

        System.out.println(processPointsWithStream(map,xMax,yMax,this::generatePoints).size());

    }

    @Override
    public void task2() {
        List<String> input = LineDataReader.getLines("day8", INPUT,false);

        Map<Character, List<Point>> map = new HashMap<>();

        int xMax = 0;
        int yMax = 0;

        for(int i = 0; i < input.size(); i++){
            for(int j = 0; j < input.get(i).length(); j++){
                Character c = input.get(i).charAt(j);
                if (!c.equals('.')) {
                    if (map.containsKey(c)) {
                        map.get(c).add(new Point(j, i));
                    } else {
                        map.put(c, new ArrayList<>());
                        map.get(c).add(new Point(j, i));
                    }
                }
                xMax=j;
            }
            yMax=i;
        }

        System.out.println(processPointsWithStream(map,xMax,yMax,this::generatePointsRepeatedly).size());
    }

    public static boolean isInRange(Point p,  Point range){
        return (p.x >= 0 && p.x <= range.x) && (p.y >= 0 && p.y <= range.y);
    }

    public static List<List<Point>> getAllPointCombinations(List<Point> points) {
        List<List<Point>> combinations = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                List<Point> pair = new ArrayList<>();
                pair.add(points.get(i));
                pair.add(points.get(j));
                combinations.add(pair);
            }
        }

        return combinations;
    }

    public static Point getDistance(Point p1, Point p2) {
        return new Point(p2.x - p1.x, p2.y - p1.y);
    }
    public static Point subtractDistance(Point originalPoint, Point distance) {
        return new Point(originalPoint.x - distance.x, originalPoint.y - distance.y);
    }
    public static Point addDistance(Point originalPoint, Point distance) {
        return new Point(originalPoint.x + distance.x, originalPoint.y + distance.y);
    }


    public static List<Point> processPointsWithStream(
            Map<Character, List<Point>> map,
            int xMax,
            int yMax,
            BiFunction<List<Point>, Point, List<Point>> generatorFunction
            ) {

        return map.values().stream()
                .flatMap(points -> getAllPointCombinations(points).stream())
                .flatMap(combination -> {


                    return generatorFunction.apply(combination,new Point(xMax,yMax)).stream(); // Stream of generated points
                })
                .distinct()
                .filter(point -> (isInRange(point, new Point(xMax, yMax))))
                .collect(Collectors.toList()); // Collect the result into a list
    }

    public List <Point> generatePointsRepeatedly(List<Point> combination, Point range){
        Point distance = getDistance(combination.get(0), combination.get(1));
        List<Point> generatedPoints = new ArrayList<>();

        Point a = new Point(combination.get(1));
        while (isInRange(a, range)) {
            generatedPoints.add(a);
            a = addDistance(a, distance);
        }

        Point b = new Point(combination.get(0));
        while (isInRange(b, range)) {
            generatedPoints.add(b);
            b = subtractDistance(b, distance);
        }
        return generatedPoints;
    }

    public List <Point> generatePoints(List<Point> combination, Point boundry){
        Point distance = getDistance(combination.get(0), combination.get(1));
        List<Point> generatedPoints = new ArrayList<>();

        generatedPoints.add(addDistance(combination.get(1), distance));
        generatedPoints.add(subtractDistance(combination.get(0), distance));
        return generatedPoints;
    }
}
