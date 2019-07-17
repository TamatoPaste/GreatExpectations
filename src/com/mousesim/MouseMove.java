package com.mousesim;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MouseMove {

    private Robot robot = null;

    public MouseMove() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以弹出QQ,快捷键 Alt + Ctl + Z
     */
    public void keyBoardDemo() {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_Z);
        robot.keyRelease(KeyEvent.VK_Z);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    /**
     * 前提是有个最大化的窗口，功能是移动到标题栏，然后拖拽到600,600的位置
     */
    public void mouseDemo() {
        robot.mouseMove(694, 466);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        try {
            Thread.sleep(210);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MouseMove demo = new MouseMove();
        demo.keyBoardDemo();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo.mouseDemo();

        System.out.println(Math.PI);

    }

}