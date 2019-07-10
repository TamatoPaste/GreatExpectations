package com.bupt.chapter2;

import org.opencv.core.*;

import java.util.Arrays;

public class S2_PixelManipulation {
    /*
    *   put(row, col, value)
    *
    *  OpenCV stores its matrix internally in the BGR (blue, green, and red) format.
    *
    *
    *
    *
    * */

    public static void filterBlueRay(Mat image){
        byte[] byteArray = new byte[(int)(image.rows() * image.cols() * image.elemSize())];
        image.get(0,0,byteArray);
        // System.out.println(Arrays.toString(byteArray));
        for ( int i = 0; i < byteArray.length; i++ ){
            if ( i % 3 == 0 ){
                byteArray[i] = 0;
            }
        }
        // System.out.println(Arrays.toString(byteArray));
        image.put(0,0,byteArray);
    }
}
