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
        byte[] uData = new byte[mWidth*mHeight/2];
        byte[] vData = new byte[mWidth*mHeight/2];
        byte[] yData = new byte[mWidth*mHeight];

        byte[] uDataAveraged = new byte[mWidth*mHeight/4];
        byte[] vDataAveraged = new byte[mWidth*mHeight/4];

        int yDataIndex = 0, uDataIndex = 0, vDataIndex = 0;

        for(int index = 0; index < mHeight*mWidth; index++){
            yData[yDataIndex++] = data[index*2];

            if(index%2 == 0){
                try {
                    uData[uDataIndex++] = data[index * 2 + 1];
                    vData[vDataIndex++] = data[index * 2 + 3];
                }catch(Exception e){

                }
            }
        }
        System.out.println("index " + uDataIndex);


        //Parse UV data
        int uAvgInd = 0;
        int vAvgInd = 0;
        int rowOffset = 1;
        for(int x = 0; x < uData.length/2; x++) {
            byte uDataOne, uDataTwo, vDataOne, vDataTwo;
            if(x%(mWidth/2*rowOffset) == 0 && x>=(mWidth/2*rowOffset))rowOffset++;
            uDataOne = uData[x+(mWidth/2)*(rowOffset-1)];
            uDataTwo = uData[x+(mWidth/2)*(rowOffset-1) + (mWidth/2)];
            vDataOne = vData[x+(mWidth/2)*(rowOffset-1)];
            vDataTwo = vData[x+(mWidth/2)*(rowOffset-1) + (mWidth/2)];

            uDataAveraged[uAvgInd++] = (byte) (((uDataOne + uDataTwo) / 2) & 0xff);
            vDataAveraged[vAvgInd++] = (byte) (((vDataOne + vDataTwo) / 2) & 0xff);
        }

        System.out.println("index " + vAvgInd);




        ByteArrayOutputStream processedData = new ByteArrayOutputStream();
        try {
            processedData.write(yData);
            processedData.write(uDataAveraged);
            processedData.write(vDataAveraged);
        }catch(IOException e){
            System.err.println("Failed to write to ByteArrayOutputStream " + e);
        }

        return processedData.toByteArray();
    }





}
