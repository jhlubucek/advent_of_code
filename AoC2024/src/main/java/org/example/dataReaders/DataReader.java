package org.example.dataReaders;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.beans.ConstructorProperties;

public abstract class DataReader {

    public static String getfileName(String dayName, FileType fileType, boolean partTwo){
        return ("data/"+dayName+"/"+ (partTwo ? fileType.filenameTwo : fileType.getFilenameOne()));
    }

    @Getter
    @AllArgsConstructor
    public enum FileType {
        TEST( "test.txt","test2.txt"),
        INPUT("input.txt","input2.txt");

        private final String filenameOne;
        private final String filenameTwo;

    }
}
