package com.bupt.chapter3;

import com.bupt.chapter2.S3_loadAndDisplayImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class S1_Smoothing_Gaussian {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) {
        Mat image = S3_loadAndDisplayImage.loadImageViaFileName("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\src\\com\\bupt\\chapter3\\gugong.jpg");
        Mat dest = new Mat();

        Imgproc.GaussianBlur(image,dest,new Size(3,3),0);

        S3_loadAndDisplayImage.displayImageWithSwing(image,"原图");
        S3_loadAndDisplayImage.displayImageWithSwing(dest,"高斯滤波后图");
    }

}

