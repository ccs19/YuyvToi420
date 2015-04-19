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
        byte[] processedDataByte = new byte[mWidth*mHeight + (mWidth*mHeight/2)];

        int uIndexOffset = mWidth*mHeight;
        int uvSize = mWidth*mHeight/4;

        int index;

        for(int y = 0; y < mHeight; y++){
            for(int x = 0; x < mWidth; x++) {
                index = (x + y * mWidth) * 2;
                processedDataByte[x + mWidth*y] = data[index];
                if (y % 2 == 0 && x%2 == 0) {
                    int uIndex = uIndexOffset + x/2 + y*(mWidth/4);
                    int vIndex = uIndex + uvSize ;
                    //System.out.printf("index u/v/max %d %d %d\n", uIndex, vIndex, processedDataByte.length);
                    processedDataByte[uIndex] = clip(data[index + 1], data[index + 1 + 4]);
                    processedDataByte[vIndex] = clip(data[index + 3], data[index + 3 + 4]);
                }
            }
        }
        return processedDataByte;
    }


    private byte clip(byte one, byte two){
        int byteOne = Byte.toUnsignedInt(one);
        int byteTwo = Byte.toUnsignedInt(two);
        int result = (byteOne+byteTwo)/2;
        if(result > 255) return Byte.MAX_VALUE;
        else if(result < 0) return Byte.MIN_VALUE;
        else return (byte)(result&0xff);
    }





}
