package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.DataReader;
import org.example.dataReaders.LineDataReader;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;

public class Day4 extends Day{
    List<List<Integer>> matrix = new ArrayList<>();
    List<List<Boolean>> controlMatrix = new ArrayList<>();

    @Override
    public void task1() {
        int count = 0;
        List<String> input = LineDataReader.getLines("day4", DataReader.FileType.INPUT,false);


        for (int i = 0; i < input.size(); i++) {
            List<Integer> row = new ArrayList<>();
            List<Boolean> controlRow = new ArrayList<>();
            for (int j = 0; j < input.get(i).length(); j++) {
                row.add(mapToInt(input.get(i).charAt(j)));
                controlRow.add(false);

            }
            matrix.add(row);
            controlMatrix.add(controlRow);
        }

        for (int i = 0; i < matrix.size(); i++) {
            Helpers.printList(matrix.get(i));
        }

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 1) {
                    count += searchHorizontal(i,j,false);
                    count += searchVertical(i,j,false);
                    count += searchDiagonalDown(i,j,false);
                    count += searchDiagonalUp(i,j,false);
                }else if (matrix.get(i).get(j) == 4) {
                    count += searchHorizontal(i,j,true);
                    count += searchVertical(i,j,true);
                    count += searchDiagonalDown(i,j,true);
                    count += searchDiagonalUp(i,j,true);
                }
            }
        }


        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                System.out.print(controlMatrix.get(i).get(j) ? matrix.get(i).get(j) : ".");
            }
            System.out.println();
        }
        System.out.println(count);

    }

    private int searchHorizontal(int x, int y, boolean backward){
        int state = backward ? 4 : 1;
        int distance = backward ? -1 : 1;

        try {
            for (int i = 1; i<4; i++){
                if (matrix.get(x).get(y+i) - state == distance){
                    state=matrix.get(x).get(y+i);
                }else {
                    return 0;
                }
            }
        }catch (IndexOutOfBoundsException e){
            return 0;
        }

        for (int i = 0; i<4; i++){
            controlMatrix.get(x).set(y+i,true);
        }


        return 1;
    }

    private int searchVertical(int x, int y, boolean backward){
        int state = backward ? 4 : 1;
        int distance = backward ? -1 : 1;

        try {
            for (int i = 1; i<4; i++){
                if (matrix.get(x+i).get(y) - state == distance){
                    state=matrix.get(x+i).get(y);
                }else {
                    return 0;
                }
            }
        }catch (IndexOutOfBoundsException e){
            return 0;
        }

        for (int i = 0; i<4; i++){
            controlMatrix.get(x+i).set(y,true);
        }


        return 1;
    }

    private int searchDiagonalDown(int x, int y, boolean backward){
        int state = backward ? 4 : 1;
        int distance = backward ? -1 : 1;

        try {
            for (int i = 1; i<4; i++){
                if (matrix.get(x+i).get(y+i) - state == distance){
                    state=matrix.get(x+i).get(y+i);
                }else {
                    return 0;
                }
            }
        }catch (IndexOutOfBoundsException e){
            return 0;
        }

        for (int i = 0; i<4; i++){
            controlMatrix.get(x+i).set(y+i,true);
        }


        return 1;
    }

    private int searchDiagonalUp(int x, int y, boolean backward){
        int state = backward ? 4 : 1;
        int distance = backward ? -1 : 1;

        try {
            for (int i = 1; i<4; i++){
                if (matrix.get(x-i).get(y+i) - state == distance){
                    state=matrix.get(x-i).get(y+i);
                }else {
                    return 0;
                }
            }
        }catch (IndexOutOfBoundsException e){
            return 0;
        }

        for (int i = 0; i<4; i++){
            controlMatrix.get(x-i).set(y+i,true);
        }


        return 1;
    }

    private static int mapToInt(char c) {
        switch (c) {
            case 'X':
                return 1;
            case 'M':
                return 2;
            case 'A':
                return 3;
            case 'S':
                return 4;
            default:
                return 9;
        }
    }

    @Override
    public void task2() {
        int count = 0;
        List<String> input = LineDataReader.getLines("day4", DataReader.FileType.INPUT,true);


        for (int i = 0; i < input.size(); i++) {
            List<Integer> row = new ArrayList<>();
            List<Boolean> controlRow = new ArrayList<>();
            for (int j = 0; j < input.get(i).length(); j++) {
                row.add(mapToInt(input.get(i).charAt(j)));
                controlRow.add(false);

            }
            matrix.add(row);
            controlMatrix.add(controlRow);
        }

        for (int i = 0; i < matrix.size(); i++) {
            Helpers.printList(matrix.get(i));
        }

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 3) {
                    count += masSearch(i,j);
                }
            }
        }


        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                System.out.print(controlMatrix.get(i).get(j) ? matrix.get(i).get(j) : ".");
            }
            System.out.println();
        }
        System.out.println(count);
    }

    private int masSearch(int x, int y)
    {
        try {
            int c1 = matrix.get(x-1).get(y-1);
            int c2 = matrix.get(x+1).get(y-1);
            int c3 = matrix.get(x+1).get(y+1);
            int c4 = matrix.get(x-1).get(y+1);

            if ((c1 == 2 && c2 == 2 && c3 == 4 && c4 == 4)
                    ||(c1 == 4 && c2 == 2 && c3 == 2 && c4 == 4)
                    ||(c1 == 4 && c2 == 4 && c3 == 2 && c4 == 2)
                    ||(c1 == 2 && c2 == 4 && c3 == 4 && c4 == 2)
            ){
                controlMatrix.get(x-1).set(y-1, true);
                controlMatrix.get(x+1).set(y-1, true);
                controlMatrix.get(x+1).set(y+1, true);
                controlMatrix.get(x-1).set(y+1, true);
                controlMatrix.get(x).set(y, true);
                return 1;

            }
        }catch (IndexOutOfBoundsException e){
            return 0;
        }
        return 0;
    }
}
