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

        for(int index = 0; index < mHeight*mWidth; index++){
            yData[yDataIndex++] = data[index*2];

            if(index%8 == 0){
                uData[uDataIndex++] = (byte)(((data[index*2+1] + data[index*2+5])/2)&0xff);
                vData[vDataIndex++] = (byte)(((data[index*2+3] + data[index*2+7])/2)&0xff);
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





}
