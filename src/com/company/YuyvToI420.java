package com.company;

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
        byte[] processedData = new byte[mWidth*mHeight*(3/2)];
        byte[] uData = new byte[mWidth*mHeight/4];
        byte[] vData = new byte[mWidth*mHeight/4];
        byte[] yData = new byte[mWidth*mHeight];




        return processedData;
    }





}
