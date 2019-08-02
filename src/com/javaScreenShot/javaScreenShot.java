package com.javaScreenShot;

// 1300 55
// 1480 85
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;


public class javaScreenShot {

    private  void captureScreen( String folder ,String fileName) throws Exception {

         // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Rectangle screenRectangle = new Rectangle(screenSize);
        // System.out.println(screenSize);
        Rectangle screenRectangle = new Rectangle(1300,55,180,30);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        // File screenFile = new File(fileName);
        // System.out.println( screenFile );
        // 如果路径不存在,则创建
//        if ( !screenFile.getParentFile().exists() ) {
//            screenFile.getParentFile().mkdirs();
//        }
        //判断文件是否存在，不存在就创建文件
        /// if(!screenFile.exists()&& !screenFile .isDirectory()) {
            // screenFile.mkdir();
       // }

        File f = new File( folder,fileName );
        System.out.println( f );

        if ( !f.getParentFile().exists() ) {
            f.getParentFile().mkdirs();


       }
        ImageIO.write(image, "png", f);
        //自动打开
    /*if (Desktop.isDesktopSupported()
             && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                Desktop.getDesktop().open(f);*/
    }

    private void startCapture(  ) {
        Date date = new Date();
        SimpleDateFormat folderDatePattern  = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat fileDatePattern = new SimpleDateFormat("yyyyMMddHHmmss");

        String folderName = folderDatePattern.format(date);
        String fileName = fileDatePattern.format(date);

        try {
            captureScreen("F:\\image\\"+folderName,fileName+".png");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        javaScreenShot app = new javaScreenShot();
         app.startCapture();


    }


}
