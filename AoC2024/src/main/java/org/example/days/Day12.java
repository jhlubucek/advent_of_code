package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.LineDataReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day12 extends Day{
    Character[][] data = LineDataReader.getCharArray("day12", INPUT,false);
    Boolean[][] visited = Helpers.createVisitedMatrix(data);

    @Override
    public void task1() {
        int fencePrice = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (!visited[i][j]) {
                    fencePrice += calculateRegionFencePrice(i,j);
                }
            }
        }

        System.out.println();
        System.out.println(fencePrice);
    }

    public int calculateRegionFencePrice(int i, int j) {
        Character regionType = data[i][j];
        int totalFenceLength = 0;
        int regionSize = 0;

        ArrayList<Point> toVisit = new ArrayList<>();
        Set<Point> visitedPoints = new HashSet<>();
        toVisit.add(new Point(j, i));

        while (!toVisit.isEmpty()) {
            Point current = toVisit.remove(toVisit.size() - 1);

            if (visitedPoints.contains(current)) {
                continue;
            }

            visitedPoints.add(current);
            visited[current.y][current.x] = true;

            regionSize++;
            totalFenceLength += countFenceLength(current);

            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x, current.y + 1)); // Up
            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x, current.y - 1)); // Down
            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x + 1, current.y)); // Right
            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x - 1, current.y)); // Left
        }

        return regionSize * totalFenceLength;
    }

    private void addNeighborIfValid(List<Point> toVisit, Set<Point> visitedPoints, Point current, Point neighbor) {
        if (!isBorder(current, neighbor) && !visitedPoints.contains(neighbor) && !toVisit.contains(neighbor)) {
            toVisit.add(neighbor);
        }
    }

    public int countFenceLength(Point p) {
        int n = 0;
        n += isBorder(p, new Point(p.x+1,p.y)) ? 1 : 0;
        n += isBorder(p, new Point(p.x-1,p.y)) ? 1 : 0;
        n += isBorder(p, new Point(p.x,p.y+1)) ? 1 : 0;
        n += isBorder(p, new Point(p.x,p.y-1)) ? 1 : 0;
        return n;
    }

    public boolean isBorder(Point a, Point b){
        return !Helpers.liesOnMap(b,data) || data[a.y][a.x] != data[b.y][b.x];
    }

    public void printMap(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(visited[i][j] ? "*" : data[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public void task2() {
        int fencePrice = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (!visited[i][j]) {
                    fencePrice += calculateDiscountedRegionFencePrice(i,j);
                }
            }
        }

        System.out.println();
        System.out.println(fencePrice);
    }

    public int calculateDiscountedRegionFencePrice(int i, int j) {
        Character regionType = data[i][j];
        int regionSize = 0;

        ArrayList<Point> toVisit = new ArrayList<>();
        Set<Point> visitedPoints = new HashSet<>();
        toVisit.add(new Point(j, i));

        List<Point> topFences = new ArrayList<>();
        List<Point> bottomFences = new ArrayList<>();
        List<Point> leftFences = new ArrayList<>();
        List<Point> rightFences = new ArrayList<>();

        while (!toVisit.isEmpty()) {
            Point current = toVisit.remove(toVisit.size() - 1);

            if (visitedPoints.contains(current)) {
                continue;
            }

            visitedPoints.add(current);
            visited[current.y][current.x] = true;

            regionSize++;

            addNewFence(current, new Point(current.x, current.y + 1), bottomFences);
            addNewFence(current, new Point(current.x, current.y - 1), topFences);
            addNewFence(current, new Point(current.x + 1, current.y), rightFences);
            addNewFence(current, new Point(current.x - 1, current.y), leftFences);

            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x, current.y + 1)); // Up
            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x, current.y - 1)); // Down
            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x + 1, current.y)); // Right
            addNeighborIfValid(toVisit, visitedPoints, current, new Point(current.x - 1, current.y)); // Left
        }

        int numberOfFences = Helpers.sum(
                countFences(topFences,true),
                countFences(bottomFences,true),
                countFences(leftFences,false),
                countFences(rightFences,false)
        );


        System.out.println(regionType+":"+regionSize+"*"+numberOfFences);
        return regionSize * numberOfFences;
    }

    public void addNewFence(Point current, Point neighbor, List<Point> fenceList){
        if (isBorder(current, neighbor)) {
            fenceList.add(current);
        }
    }


    public int countFences(List<Point> fences, boolean isHorizontal) {
        int count = 0;
        Set<Point> fenceSet = new HashSet<>(fences);

        while (!fenceSet.isEmpty()) {
            Point start = fenceSet.iterator().next();
            fenceSet.remove(start);

            Point left = new Point(start);
            Point right = new Point(start);


            if (isHorizontal) {
                extendFence(left, right, fenceSet, true);
            } else {
                extendFence(left, right, fenceSet, false);
            }


            count++;
        }

        return count;
    }

    private void extendFence(Point left, Point right, Set<Point> fenceSet, boolean isHorizontal) {
        while (left != null || right != null) {
            if(left != null){
                if (isHorizontal) {
                    left.x--;
                } else {
                    left.y--;
                }
                if (!fenceSet.remove(left)) {
                    left = null;
                }
            }
            if(right != null){
                if (isHorizontal) {
                    right.x++;
                } else {
                    right.y++;
                }
                if (!fenceSet.remove(right)) {
                    right = null;
                }
            }
        }
    }
}
