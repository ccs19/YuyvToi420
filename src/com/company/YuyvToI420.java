package com.company;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Chris on 4/17/2015.
 */
public class YuyvToI420 {

    private int mWidth, mHeight;


    public YuyvToI420(int width, int height){
        mWidth = width;
        mHeight = height;
    }

    public byte[] convertData(byte[] data){
        //byte[] processedData = new byte[mWidth*mHeight*(3/2)];
        byte[] uData = new byte[mWidth*mHeight/4];
        byte[] vData = new byte[mWidth*mHeight/4];
        byte[] yData = new byte[mWidth*mHeight];

        int yDataIndex = 0, uDataIndex = 0, vDataIndex = 0;
        int index = 0;

        for(int y = 0; y < mHeight; y++){
            for(int x = 0; x < mWidth; x++) {
                index = (x + y*mWidth)*2;
                System.out.println(index);
                yData[yDataIndex++] = data[index];
                if (y % 2 == 0 && x%2 == 0) {
                    uData[uDataIndex++] = clip(data[index + 1], data[index + 1 + 4]);
                    vData[vDataIndex++] = clip(data[index + 3], data[index + 3 + 4]);
                }
            }
        }




        ByteArrayOutputStream processedData = new ByteArrayOutputStream();
        try {
            processedData.write(yData);
            processedData.write(uData);
            processedData.write(vData);
        }catch(IOException e){
            System.err.println("Failed to write to ByteArrayOutputStream " + e);
        }

        return processedData.toByteArray();
    }


    private byte clip(byte one, byte two){
        int byteOne = Byte.toUnsignedInt(one);
        int byteTwo = Byte.toUnsignedInt(two);
        int result = (byteOne+byteTwo)/2;
        //System.out.println(result&0xff);
        if(result > 255) return Byte.MAX_VALUE;
        else if(result < 0) return Byte.MIN_VALUE;
        else return (byte)(result&0xff);
    }





}
