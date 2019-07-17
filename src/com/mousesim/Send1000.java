package com.mousesim;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Send1000 {
    public static void main(String[] args) throws AWTException {

        Map<Integer, Integer> keyMap = new HashMap<Integer, Integer>();
        keyMap.put(0, KeyEvent.VK_NUMPAD0);
        keyMap.put(1, KeyEvent.VK_NUMPAD1);
        keyMap.put(2, KeyEvent.VK_NUMPAD2);
        keyMap.put(3, KeyEvent.VK_NUMPAD3);
        keyMap.put(4, KeyEvent.VK_NUMPAD4);
        keyMap.put(5, KeyEvent.VK_NUMPAD5);
        keyMap.put(6, KeyEvent.VK_NUMPAD6);
        keyMap.put(7, KeyEvent.VK_NUMPAD7);
        keyMap.put(8, KeyEvent.VK_NUMPAD8);
        keyMap.put(9, KeyEvent.VK_NUMPAD9);
        Robot robot = new Robot();

        pressKey(robot, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_Z);

        int count  = 1;
        while ( count  < 10 ){
            pressKey(robot,keyMap.get(count));
            robot.delay(2000);
            pressKey(robot, KeyEvent.VK_ENTER);
            robot.delay(2000);
            count++;
        }
    }

    private static void  pressKey(Robot robot, int... keys)  {
        for ( int key : keys ){
            robot.keyPress(key);
            robot.delay(200);
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for ( int key : keys ){
            robot.keyRelease(key);
            robot.delay(100);
        }

    }



}
