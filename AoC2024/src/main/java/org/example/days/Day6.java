package org.example.days;

import org.example.common.Helpers;
import org.example.common.Helpers.Position;
import org.example.dataReaders.LineDataReader;

import java.util.ArrayList;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day6 extends Day{
    List<List<Character>> map = new ArrayList<>() ;
    List<List<Boolean>> visited = new ArrayList<>() ;


    Position start;


    public Day6() {
        fillMap();
    }

    public void fillMap(){
        List<String> input = LineDataReader.getLines("day6", INPUT,false);
        for(int i = 0; i < input.size(); i++){
            List<Character> row = new ArrayList<>() ;
            List<Boolean> rowW = new ArrayList<>() ;
            for(int j = 0; j < input.get(i).length(); j++){

                if (input.get(i).charAt(j) == '^' ){
                    start = new Position(i,j);
                    rowW.add(true);
                    row.add('.');
                }else{
                    rowW.add(false);
                    row.add(input.get(i).charAt(j));
                }
            }
            visited.add(rowW);
            map.add(row);
        }
            map.forEach(Helpers::printList);

    }

    @Override
    public void task1() {

        Position direction = new Position(-1, 0);
        Position position = start.toBuilder().x(start.getX()).y(start.getY()).build();
        try {
        //ano jsem prase
            while (true) {
                makeStep(position, direction);
            }
        }catch (IndexOutOfBoundsException e){
            //end cycle
        }

        int counter = 0;
        for (int i = 0; i < visited.size(); i++) {
            for (int j = 0; j < visited.get(i).size(); j++) {
                if (visited.get(i).get(j)) {
                    counter++;
                }
            }
        }
        System.out.println(counter);

    }



    public void makeStep(Position pos, Position dir){
        if (map.get(pos.x+dir.x).get(pos.y+dir.y) != '#'){
            pos.x += dir.x;
            pos.y += dir.y;
            visited.get(pos.x).set(pos.y, true);
        }else {
            rotateRight(dir);
        }

//        for (int i = 0; i < visited.size(); i++) {
//            for (int j = 0; j < visited.get(i).size(); j++) {
//                if (visited.get(i).get(j)) {
//                    System.out.print("0");
//                }else {
//                    System.out.print(map.get(i).get(j));
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();

    }

    public void rotateRight(Position pos) {
        int x = pos.x;
        int y = pos.y;
        pos.x = (y);
        pos.y = (-x);
    }


    @Override
    public void task2() {

    }
}
