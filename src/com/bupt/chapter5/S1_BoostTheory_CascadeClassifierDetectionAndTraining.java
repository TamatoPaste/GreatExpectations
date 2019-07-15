package com.bupt.chapter5;

import com.bupt.chapter2.S3_loadAndDisplayImage;
import com.bupt.chapter2.util.ImageProcessor;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;

public class S1_BoostTheory_CascadeClassifierDetectionAndTraining {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME );}

    private CascadeClassifier faceDetector;
    private JFrame frame;
    private JLabel imageLabel;

    public static void main(String[] args) {
        S1_BoostTheory_CascadeClassifierDetectionAndTraining app = new S1_BoostTheory_CascadeClassifierDetectionAndTraining();

        app.loadCascade();
        app.initGUI();

        //Mat image = S3_loadAndDisplayImage.loadImageViaFileName("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\src\\resources\\dididada.jpg");

        //app.detectAndDrawFace(image);

        //S3_loadAndDisplayImage.displayImageWithSwing(image,"image");

        app.playVideo("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\src\\resources\\VideoTestResource.mp4");
    }


    private void loadCascade(){
        faceDetector = new CascadeClassifier("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\src\\resources\\lbpcascade_frontalface.xml");
    }

    private void detectAndDrawFace( Mat imageFromVideo){
        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(imageFromVideo, faceDetections);

        for ( Rect rect : faceDetections.toArray() ){
            Imgproc.rectangle(imageFromVideo,new Point(rect.x,rect.y),
                    new Point(rect.x + rect.width,rect.y+rect.height),
                    new Scalar(0,255,0));
        }
    }


    private void initGUI(){
        frame = new JFrame("Video Player");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,400);

        imageLabel = new JLabel();
        frame.add(imageLabel);

        frame.setVisible(true);
        frame.add(imageLabel);
    }

    private void playVideo(String fileName)  {
        Mat mat = new Mat();
        Image videoFrame;

        VideoCapture vc = new VideoCapture();
        vc.open(fileName);
        vc.set(Videoio.CAP_PROP_FRAME_WIDTH,800);
        vc.set(Videoio.CAP_PROP_FRAME_HEIGHT,800);

        if ( vc.isOpened() ){
            //  double fps = vc.get(Videoio.CAP_PROP_FPS);
            //  System.out.println( fps );
            while( true ){
                vc.read(mat);
                if ( !mat.empty() ){
                    videoFrame = ImageProcessor.toBufferedImage(mat);
                    ImageIcon imageIcon = new ImageIcon(videoFrame, "Captured Image from video");
                    imageLabel.setIcon( imageIcon );
                    frame.pack();

                    detectAndDrawFace(mat);

                }else {
                    System.out.println("!!!!!!!! Job Finished !!!!!!!!");
                    break;
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("------------------VideoCapture Is Not Opened!-----------------");
        }
    }


}
