package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.LineDataReader;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day14 extends Day{
    final int width = 101;//11;
    final int height = 103;//7;
    final int maxX = width -1;
    final int maxY = height -1;
    int[][] robots;

    public Day14() {
        List<String> input = LineDataReader.getLines("day14", INPUT,false);
        robots = new int[input.size()][];
        int i=0;
        while(i < input.size()) {
            Matcher robotMatcher = Helpers.matchRegex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)", input.get(i));
            robotMatcher.find();
            int[] robot = {
                    Integer.parseInt(robotMatcher.group(1)), Integer.parseInt(robotMatcher.group(2)), Integer.parseInt(robotMatcher.group(3)), Integer.parseInt(robotMatcher.group(4))
            };
            robots[i] = robot;
            i++;
        }
    }

    @Override
    public void task1() {

        int xHalf = width/2;
        int yHalf = height/2;

        int q1counter = 0;
        int q2counter = 0;
        int q3counter = 0;
        int q4counter = 0;
        for (int[] robot : robots) {
            if (robot[0] < xHalf && robot[1] < yHalf) {
                q1counter++;
            }else if ((robot[0] > xHalf && robot[1] < yHalf)){
                q2counter++;
            }else if ((robot[0] < xHalf && robot[1] > yHalf)){
                q3counter++;
            }else if ((robot[0] > xHalf && robot[1] > yHalf)){
                q4counter++;
            }
        }
        System.out.println(q1counter*q2counter*q3counter*q4counter);

    }


    public void moveRobots(int cycles){
        for (int i = 0; i < robots.length; i++) {
            moveRobot(i,cycles);
        }
    }


    public void printRobots() {
        for (int i = 0; i < robots.length; i++) {
            System.out.print(i+": ");
            for (int x : robots[i]) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    public void printRoom() {
        List<Integer> printed = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int counter = 0;
                for (int i = 0; i < robots.length; i++) {
                    if (robots[i][0] == x && robots[i][1] == y) {
                        printed.add(i);
                        counter++;
                    }
                }
                System.out.print(counter > 0 ? counter : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void moveRobot(int robot, int cycles) {
        int x = (robots[robot][0] + robots[robot][2]*cycles) % width;
        int y = (robots[robot][1] + robots[robot][3]*cycles) % height;

        if (x < 0){
            x += width;
        }
        if (y < 0){
            y += height;
        }
        robots[robot][0] = x;
        robots[robot][1] = y;
    }

    public Map<Point,Integer> getVisibleRobots(){
        Map<Point,Integer> visibleRobots = new HashMap<>();

        for (int[] robot : robots) {
            Point p = new Point(robot[0], robot[1]);
            if (visibleRobots.containsKey(p)) {
                visibleRobots.put(p, visibleRobots.get(p) + 1);
            }else {
                visibleRobots.put(p, 1);
            }
        }

        return visibleRobots;
    }


    public boolean hasVerticalLineAtLeast(Map<Point, Integer> visibleRobots, int height) {
        List<Point> occupiedPoints = visibleRobots.keySet().stream().toList();
        for (Point p : occupiedPoints) {
            for(int i = 1 ; i<=height; i++){
                if (!visibleRobots.containsKey(new Point(p.x,p.y+i))) {
                    break;
                }else if (i==height){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void task2() {
        for (int i = 0; i < 100000; i++) {
            moveRobots(1);
            if (hasVerticalLineAtLeast(getVisibleRobots(),10)){
                System.out.println(i);
                printRoom();
            }
        }
    }
}
