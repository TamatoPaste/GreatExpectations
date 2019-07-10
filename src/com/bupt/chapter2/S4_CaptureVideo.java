package com.bupt.chapter2;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import com.bupt.chapter2.util.ImageProcessor;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;

public class S4_CaptureVideo {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private JFrame frame;
    private JLabel imageLabel;

    public static void main(String[] args) {
        S4_CaptureVideo app = new S4_CaptureVideo();
        app.initGUI();
        app.runLoop();
    }

    /*
    *  In order to use the VideoCapture class to capture a webcam stream,
    *  you need to instantiate it using the VideoCapture(int device) constructor.
    *
    *   Note that the constructor parameter refers to the camera index in case you have several cameras.
    *
    *   So, if you have one built-in camera and one USB camera and
    *   you create a video capture object, such as new VideoCapture(1), then
    *   this object will refer to your built-in camera, while new VideoCapture(0) will refer
    *   to your just-plugged-in USB camera or the other way around.
    *
    *    isOpened()
    *
    *    read()     grab() and retrieve()
    *
    *    Videoio.CAP_PROP_FRAME_WIDTH
    *    Videoio.CAP_PROP_FRAME_WIDTH
    * */

    private void initGUI(){
        frame = new JFrame("Camera Input Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        
        imageLabel = new JLabel();
        
        frame.add(imageLabel);
        frame.setVisible( true );
    }
    
    private void runLoop(){
        Mat webCamImage = new Mat();
        Image tempImage;
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH,400);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT,400);

        if ( capture.isOpened() ){
            while ( true ){
                capture.read( webCamImage );
                if ( !webCamImage.empty() ){
                    tempImage = ImageProcessor.toBufferedImage(webCamImage);
                    ImageIcon imageIcon = new ImageIcon(tempImage, "Captured Image");
                    imageLabel.setIcon( imageIcon );
                    frame.pack();
                }else {
                    System.out.println("!!!!!!!! Frame Not Captured !!!!!!!!");
                    break;
                }
            }
        }else {
            System.out.println("-------------- Can Not Open Capture ---------------");
        }
    }
}


