package com.characterRecognition;



import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;
import java.io.IOException;

public class OCR {

    public static void main(String[] args){

        File image = new File("C:\\Users\\Administrator\\IdeaProjects\\C\\javaopencvIDEA\\resources\\OCRTest04.png");
        ITesseract tesseract = new Tesseract();


        //设置训练库的位置,命令行情况下需要设置  TESSDATA_PREFIX 环境变量
        // java调用这一行就起到类似的作用
        tesseract.setDatapath("E:\\tesseract\\Tess4J");

        tesseract.setLanguage("chi_sim");//chi_sim ：简体中文， eng	根据需求选择语言库
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result =  tesseract.doOCR(image);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        System.out.println("result: ");
        System.out.println(result);
    }


}
