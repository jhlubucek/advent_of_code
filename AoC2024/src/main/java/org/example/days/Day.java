package org.example.days;

public abstract class Day {
    public abstract void task1();
    public abstract void task2();

    public void runTask(int no){
        if(no == 1){
            task1();
        }else if(no == 2){
            task2();
        }else {
            System.out.println("NON EXISTANT TASK");
        }
    }
}
