package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static int width = 176, height = 144;

    public static void main(String[] args) {
	    Path filePath = Paths.get("C:\\Users\\Chris\\IdeaProjects\\YuyvToi420\\sample.yuv");
        byte[] imageData = null, dataResult = null;


        try {
            imageData = Files.readAllBytes(filePath);
        }catch(Exception e){
            System.err.println("Failed to read file " + e);
        }

        if(imageData != null) {
            YuyvToI420 processor = new YuyvToI420(width, height);
            dataResult = processor.convertData(imageData);
        }


        try {
            FileOutputStream saveResult = new FileOutputStream(new File("convertedImage.yuv"));
            saveResult.write(dataResult);

        }catch(Exception e ){
            System.err.println("Failed to open outputstream and write data " + e);
        }



    }
}
