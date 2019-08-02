package com.mousesim;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class BlueBlockClick {

    public static void main(String[] args) throws AWTException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Robot robot = new Robot();
        Rectangle rectangle = new Rectangle(0, 100, 1920, 920);
        int x,y;
        int i = 0;
        while   ( i < 1000 ){
            x = 0;
            y = 0;

            BufferedImage screenCapture = robot.createScreenCapture(rectangle);
            int width = screenCapture.getWidth();
            int height = screenCapture.getHeight();
            int flag = 0;
            long startTime = System.currentTimeMillis();
            for ( int j = 0; j < width; j+=10){
                for ( int k = 100; k < height; k+=10 ){
                    int rgb = screenCapture.getRGB(j, k);
                    int red  = (rgb & 0xff0000) >> 16;
                    int green = (rgb & 0xff00) >> 8;
                    int blue = (rgb & 0xff);
                    if( green > 100 && blue < 50 && red < 50){
                        x = j;
                        y = k;
                        flag = 1;
                        break;
                    }
                }
                if ( flag == 1){
                    break;
                }
            }

            if ( flag == 1 ){
                // click(x+10,y+10);
                System.out.println("找到像素点：(" + x +"," + y + ") 符合要求,第" + i + "个");
                robot.mouseMove(x+20,y+120);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(5);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                i++;
            }else{
                System.out.println("没找到合适的点");
            }
            System.out.println(System.currentTimeMillis() - startTime);




        }



    }


}
