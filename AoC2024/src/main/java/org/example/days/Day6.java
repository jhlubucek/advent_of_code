package org.example.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.common.Helpers;
import org.example.common.Helpers.Position;
import org.example.dataReaders.LineDataReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day6 extends Day{
    List<List<Character>> map = new ArrayList<>() ;


    Position start;


    public Day6() {
        fillMap();
    }

    public void fillMap(){
        List<String> input = LineDataReader.getLines("day6", INPUT,false);
        for(int i = 0; i < input.size(); i++){
            List<Character> row = new ArrayList<>() ;
            for(int j = 0; j < input.get(i).length(); j++){

                if (input.get(i).charAt(j) == '^' ){
                    start = new Position(i,j);
                    row.add('.');
                }else{
                    row.add(input.get(i).charAt(j));
                }
            }
            map.add(row);
        }
            map.forEach(Helpers::printList);

    }

    @Getter
    public class MapSearch{
        private List<List<Character>> map;
        private Position position;
        private List<List<Boolean>> path;
        private Position direction = new Position(-1, 0);
        private HashMap<Position,List<Position>> rotations = new HashMap<>();

        public MapSearch(List<List<Character>> map, Position start) {
            this.map = map;
            this.position = new Position(start);
            this.path = createBlankPath();
        }

        public List<Position> getAditionalObstacles() {
            List<Position> aditionalObstacles = new ArrayList<>();
            for (int i = 0; i < path.size(); i++) {
                for (int j = 0; j < path.get(i).size(); j++) {
                    if (path.get(i).get(j)) {
                        aditionalObstacles.add(new Position(i, j));
                    }
                }
            }
            return aditionalObstacles;
        }

        private List<List<Boolean>> createBlankPath()
        {
            List<List<Boolean>> visited = new ArrayList<>() ;
            for(int i = 0; i < map.size(); i++){
                List<Boolean> row = new ArrayList<>() ;
                for(int j = 0; j < map.get(i).size(); j++){
                    row.add(false);
                }
                visited.add(row);
            }
            visited.get(start.x).add(start.y,true);

            return visited;
        }

        public void search(){
            try {
                //ano jsem prase
                while (true) {
                    makeStep(position, direction, map);

                }
            }catch (IndexOutOfBoundsException e){
                //end cycle
            }
        }

        private void makeStep(Position pos, Position dir, List<List<Character>> map){
            if (map.get(pos.x+dir.x).get(pos.y+dir.y) != '#' && map.get(pos.x+dir.x).get(pos.y+dir.y) != 'O'){
                pos.x += dir.x;
                pos.y += dir.y;
                path.get(pos.x).set(pos.y,true);
            }else {
                if (rotations.containsKey(pos) && rotations.get(pos).contains(dir)){
                    throw new ArithmeticException("loop encountered");
                }
                if (!rotations.containsKey(pos)){
                    rotations.put(new Position(pos),new ArrayList<>());
                }
                rotations.get(pos).add(new Position(dir));
                rotateRight(dir);
            }
        }

        public void rotateRight(Position pos) {
            int x = pos.x;
            int y = pos.y;
            pos.x = (y);
            pos.y = (-x);
        }

        private int getPathCount(){
            int counter = 0;
            for (int i = 0; i < path.size(); i++) {
                for (int j = 0; j < path.get(i).size(); j++) {
                    if (path.get(i).get(j)) {
                        counter++;
                    }
                }
            }
            return counter;
        }

        public void printMap(){
            for (int i = 0; i < map.size(); i++) {
                for (int j = 0; j < map.get(i).size(); j++) {
                    if (path.get(i).get(j)) {
                        System.out.print("*");
                    }else {
                        try {
                            System.out.print(map.get(i).get(j));
                        }catch (IndexOutOfBoundsException e){
                            System.out.println(i);
                            System.out.println(j);
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    @Override
    public void task1() {


        MapSearch mapSearch = new MapSearch(map, start);
        mapSearch.search();
        System.out.println(mapSearch.getPathCount());

    }





    @Override
    public void task2() {
        MapSearch mapSearch = new MapSearch(map, start);
        mapSearch.search();

        List<List<Boolean>> path = mapSearch.getPath();
        int loopCount = 0;
        int count = 0;
        List<Position> aditionalObstacles = mapSearch.getAditionalObstacles();
        for (Position pos : aditionalObstacles) {
            System.out.println(++count + "/" + aditionalObstacles.size());
                List<List<Character>> subMap = Helpers.deepCopy(map);
                subMap.get(pos.x).set(pos.y, 'O');
                MapSearch subMapSearch = new MapSearch(subMap, start);
                try {

                    subMapSearch.search();
                } catch (ArithmeticException e) {
                    loopCount++;
                }
        }
        System.out.println(loopCount);
    }

    @AllArgsConstructor
    public class PositionWithDirection{
        public Position position;
        public Position direction;
    }
}
