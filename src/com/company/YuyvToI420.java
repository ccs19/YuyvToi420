package com.company;

/**
 * Converts a YUY2 frame to I420
 */
public class YuyvToI420 {

    private int mWidth, mHeight;


    /**
     * Constructor for class
     * @param width Frame width
     * @param height Frame height
     */
    public YuyvToI420(int width, int height){
        mWidth = width;
        mHeight = height;
    }

    /**
     * Converts a single YUY2 frame to I420
     * @param data byte array of YUY2 data
     * @return I420 Frame
     */
    public byte[] convertData(byte[] data){
        byte[] processedDataByte = new byte[mWidth*mHeight + (mWidth*mHeight/2)];
        int uIndexOffset = mWidth*mHeight;
        int uvArraySize = mWidth*mHeight/4;
        int dataIndex;

        for(int y = 0; y < mHeight; y++){ //Y plane
            for(int x = 0; x < mWidth; x++) { //X Plane
                dataIndex = (x + y * mWidth) * 2;
                processedDataByte[x + mWidth * y] = data[dataIndex];
                if (y % 2 == 0 && x % 2 == 0) {
                    int uIndex = uIndexOffset + x/2 + y*(mWidth/4);
                    int vIndex = uIndex + uvArraySize ;
                    processedDataByte[uIndex] = clip(data[dataIndex + 1], data[dataIndex + 1 + 4]);
                    processedDataByte[vIndex] = clip(data[dataIndex + 3], data[dataIndex + 3 + 4]);
                }
            }
        }
        return processedDataByte;
    }


    private byte clip(byte one, byte two){
        return (byte)
                (((Byte.toUnsignedInt(one) + Byte.toUnsignedInt(two))/2)&0xff);
    }
}
