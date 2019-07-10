package com.bupt.chapter2;

import org.opencv.core.*;


public class S1_BasicMatrixManipulation {
    /*
       列数为宽，行数为高

      CV_<bit_depth>{U|S|F}C(<number_of_channels>)
         U stands for unsigned
         S for signed
         F stands for floating point
         If the number of channels is omitted, it evaluates to 1
    */

     public static void test01(){
        Mat image1 = new Mat(480,640, CvType.CV_8UC3 );
        Mat image2 = new Mat(new Size(640,480),CvType.CV_8UC3);

        System.out.println( image1 );
        System.out.println("image 1 rows: " + image1.rows() );
        System.out.println("image 1 cols: " + image1.cols() );
        System.out.println("image 1 size: " + image1.size() );
        System.out.println("image 1 elementSize: " + image1.elemSize() );
    }

    public static void  test02(){
        Mat image = new Mat(new Size(3,3), CvType.CV_8UC3, new Scalar(new
                double[]{128,3,4}));
        System.out.println( image.dump() );
    }





















}
