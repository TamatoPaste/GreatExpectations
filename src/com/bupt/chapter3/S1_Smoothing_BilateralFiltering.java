package com.bupt.chapter3;

import com.bupt.chapter2.S3_loadAndDisplayImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class S1_Smoothing_BilateralFiltering {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) {
        Mat src = S3_loadAndDisplayImage.loadImageViaFileName("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\src\\resources\\gugong.jpg");
        Mat dest = new Mat();

        Imgproc.bilateralFilter(src,dest,1,1,1  );

        S3_loadAndDisplayImage.displayImageWithSwing( src , "原图");
        S3_loadAndDisplayImage.displayImageWithSwing( dest , "双边滤波");


    }
}




