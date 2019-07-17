package com.mousesim;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenCapturer {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(0, 0, 1920, 10800);
        Robot robot = null;
        FileOutputStream fos = null;
        try {
            robot = new Robot();
            BufferedImage screenCapture = robot.createScreenCapture(rectangle);
            fos = new FileOutputStream("E://ScreenCapture.jpg");

            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
            jpegEncoder.encode(screenCapture);
            fos.close();
        } catch (AWTException e) {
            e.printStackTrace();
            System.out.println("Robot没权限");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
