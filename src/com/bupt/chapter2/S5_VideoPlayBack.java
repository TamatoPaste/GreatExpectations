package com.bupt.chapter2;

import com.bupt.chapter2.util.ImageProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;

public class S5_VideoPlayBack {
    private JFrame frame;
    private JLabel imageLabel;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        S5_VideoPlayBack vp = new S5_VideoPlayBack();
        vp.initGUI();
        vp.playVideo("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\src\\resources\\VideoTestResource.mp4");
    }

    /*
    *
    *  Instead of constructing it with a device number, as was done previously,
    *    we need to create it with the file path.
    *
    *  We can also use the empty constructor and make the open(String filename) method
    *    responsible for pointing to the file.
    *
    * */

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
            double fps = vc.get(Videoio.CAP_PROP_FPS);
            //  System.out.println( fps );
            while( true ){
                vc.read(mat);
                if ( !mat.empty() ){
                    videoFrame = ImageProcessor.toBufferedImage(mat);
                    ImageIcon imageIcon = new ImageIcon(videoFrame, "Captured Image from video");
                    imageLabel.setIcon( imageIcon );
                    frame.pack();
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
