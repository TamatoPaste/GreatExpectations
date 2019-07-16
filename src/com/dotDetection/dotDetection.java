package com.dotDetection;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class dotDetection {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    /*
    *   本类旨在提供一个检测图片中点的方法
    *
    *   输入： 图片
    *   输出： 点的坐标
    *   坐标系以图片的左上顶点为原点，横轴为x，正方向向右
    *                                 纵轴为y，正方向向下
    *   坐标系中一个取值代表一个像素
    *
    * */

    public static void main(String[] args) {
        dotDetection app = new dotDetection();
        Map<String, Point[]> colorMap = app.dotClassfier("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\resources\\redAndGreenDot.png");
        Point[] reds = colorMap.get("red");
        System.out.print(" 红色点的坐标： ");
        for ( Point p : reds ){
            System.out.print( " x = " + (int)p.x + " y = " + (int)p.y);
        }
        System.out.println();
        Point[] greens = colorMap.get("green");
        System.out.print(" 绿色点的坐标： ");
        for ( Point p : greens ){
            System.out.print( " x = " + (int)p.x + " y = " + (int)p.y);
        }
    }

    private Point[] getDotPosition(String fileName){
        try{

            final int maxCorners=50,blockSize=3;
            final double qualityLevel=0.01,minDistance=20.0,k=0.04;
            final boolean useHarrisDetector=false;

            MatOfPoint corners = new MatOfPoint();

            Mat image= Imgcodecs.imread(fileName);
            if(image.empty()){
                throw new Exception("no file");
            }
            Mat dst=image.clone();
            Mat gray=new Mat();

            Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
            Imgproc.goodFeaturesToTrack(gray, corners, maxCorners, qualityLevel, minDistance,
                    new Mat(),blockSize,useHarrisDetector,k);

            return corners.toArray();
            // Point[] pCorners=corners.toArray();


//            for (Point pCorner : pCorners) {
//                Imgproc.circle(dst, pCorner, 4, new Scalar(255, 255, 0), 2);
//            }
            // Imgcodecs.imwrite("resources/dotted.jpg", dst);

        }catch(Exception e){
            System.out.println("例外：" + e);
            return  null;
        }
    }

    private Map<String,Point[]> dotClassfier(String fileName)  {
        int rgbR;
        int rgbG;
        int rgbB;

        ArrayList<Point> greenPoints = new ArrayList<>();
        ArrayList<Point> redPoints = new ArrayList<>();

        Point[] dotPositions = getDotPosition(fileName);
        if ( dotPositions == null ){
            return  null;
        }

        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            for(Point p : dotPositions ){
                int rgb = image.getRGB((int) p.x, (int) p.y);
                rgbR = (rgb & 0xff0000) >> 16;
                rgbG = (rgb & 0xff00) >> 8;
                rgbB = (rgb & 0xff);
                // System.out.println( "rgbR : " + rgbR + " rgbG : " + rgbG + " rgbB : " + rgbB );

                if ( rgbR > 150 ){
                    redPoints.add( p );
                }else {
                    greenPoints.add( p );
                }
            }

            Map<String, Point[]> map = new HashMap<>();


            map.put("red",(Point[]) redPoints.toArray());
            map.put("green",(Point[]) greenPoints.toArray());
            return  map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
