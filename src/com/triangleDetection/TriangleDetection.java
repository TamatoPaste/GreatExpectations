package com.triangleDetection;


import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/*
* 我们不妨来整理一下思路：

     1.该图像噪声比较明显，而且属于椒盐噪声，比较适合用中值滤波得到交清晰的图像；

     2.进行边缘检测，对于相对来说比较复杂的图像，我们考虑用经典实用的canny算法进行边缘检测；

     3.如果边缘存在厚度，我们要进行边缘细化，这张图边缘黑白分界明显，目测没有必要细化；

     4.霍夫变换得到三条直线信息。想要得到线段范围信息，只能采用PPHT方法。

    如果顺利的话，四步就能够得到我们想要的结果。那我们看看这样做的效果如何把：
    *
    * Step1：滤波  cvSmooth(gray, middle, CV_MEDIAN, 5, 5);
    * Step2：边缘检测   cvCanny(middle, canny, 80, 160);
    * Step3：霍夫变换
    * 我们可以看到，整体的检测效果还是不错的，但是三角形的顶点检测不一，也就是说三个边长并没有完全检测出来，
    * 有漏检测的问题。这个实际上是调参数很难弥补的问题，三条边长检测总会存在误差，所以我们要想办法来解决这个问题。
    我们发现，虽然顶点没有检测得很好，但是直线方向检测的非常准确，
    * 如果我们能作出这三条直线，求出三条直线的交点，我们就可以完美地得到三角形的顶点
*
*
* */
public class TriangleDetection {
    static { System.loadLibrary( Core.NATIVE_LIBRARY_NAME );}

    // 此方法用于找两条直线的交点
    private static Point findPoint(double k1, double b1, double k2, double b2){
        Point point = new Point();

        point.x = (int)((b2 - b1) / (k1 - k2));
        point.y = (int)(k1 * point.x + b1);

        return point;
    }

    private static void findPoints(){
        Mat srcImageChannel3 = Imgcodecs.imread("C:\\Users\\Daye Ni\\Desktop\\333.png", CvType.CV_32FC3);


       /*  高斯滤波，实际检测效果并不好
        Mat gaussian = new Mat();
        Imgproc.GaussianBlur(srcImage,gaussian,new Size(3.0,3.0),0);
        Imgcodecs.imwrite("F:\\gaussian.jpg",gaussian);
        */


        // 中值滤波,ksize必须为奇数
        //Mat mid = new Mat();
        //Imgproc.medianBlur(srcImage,mid,13);
        //Imgcodecs.imwrite("F:\\mid" + System.currentTimeMillis() + ".jpg",mid);


        // 用Canny算法在滤波后图像进行边缘检测，检测后的结果存在 Mat canny 中
        // 其中较大的threshold2用于检测图像中明显的边缘，但一般情况下检测的效果不会那么完美
        // 边缘检测出来是断断续续的，所以这时候用较小的threshold1用于将这些间断的边缘连接起来。
        //可选参数apertureSize是Sobel算子的大小（默认值为3），而参数L2gradient是一个布尔值，
        // 如果为真，则使用更精确的L2范数进行计算（即两个方向的倒数的平方和再开方）
        // 否则使用L1范数（直接将两个方向导数的绝对值相加）。
        Mat canny = new Mat();
        Imgproc.Canny(srcImageChannel3,canny,10,160,3,false);
        //Imgcodecs.imwrite("F:\\canny" + System.currentTimeMillis() +".jpg",canny);

        // 霍夫变化计算后的结果是极坐标体系下的线，存在第二个参数中
        // lines中rho就是离坐标原点也就是图像左上角的距离，
        // theta就是角度θ，表示线条旋转的角度，0 表示垂直线，Π/2表示水平线
        // 第三个参数和第四个参数也是rho和theta，但意义和lines中的不一样
        Mat lines =  new Mat();
        Imgproc.HoughLines(canny,lines,1,Math.PI/180,100);
        System.out.println( lines.dump());
        /* dump结果为下，
        [304, 1.5707964;
        331, 0;
        167, 0.47123888;
        168, 0.48869219;
        -162, 2.8274333]
        注意lines其实是一个5行1列的数组，每个元素是一个double数组，数组大小为2，如果输出 lines.row(i),
        Mat [ 1*1*CV_32FC2, isCont=true, isSubmat=true, nativeObj=0xcb91a0, dataAddr=0xcc5bc8 ]
        可以看到还是一个Mat,不是一个数组，lines.row(1)[0]这种格式直接报错
        经测试，可以用get()方法，返回的就是数组
        */
        //System.out.println("D:" + lines.depth() + " C:" + lines.channels() + " R:" +lines.rows() + " C:" + lines.cols());
        // D:5 C 2 R: 5 C: 1
        //Imgcodecs.imwrite("F:\\lines.jpg",lines);

        double [] ks = new double[lines.rows()];//保存斜率值
        double [] bs = new double[lines.rows()];//保存截距值
        //double [] judges = new double[lines.rows()];//保存是否斜率不存在的判断值

        for (int i = 0; i < lines.rows(); i++) {
            double[] doubles = lines.get(i, 0);
            double rho = doubles[0];
            double theta = doubles[1];
            double k = (-1) * ( Math.cos(theta) / Math.sin(theta));
            double b = rho / (Math.sin(theta));
            ks[i] = k;
            bs[i] = b;
        }

        for (int  i = 0;  i < lines.rows() - 1;  i++) {
            for (int j = 0; j < lines.rows(); j++) {
                Point point = findPoint(ks[i],bs[i],ks[j],bs[j]);
                Imgproc.circle( srcImageChannel3, point,3, new Scalar(0,255,0),2);

            }
        }

//        Imgproc.line(srcImageChannel3,point1,point2,new Scalar(255,0,0),2,10,0);
//        Imgproc.line(srcImageChannel3,point1,point3,new Scalar(255,0,0),2,10,0);
//        Imgproc.line(srcImageChannel3,point3,point2,new Scalar(255,0,0),2,10,0);
        imshow("srcImage",srcImageChannel3);
        waitKey(0);
    }

    private static void ppht(String imagePath){
        Mat src = Imgcodecs.imread(imagePath, CvType.CV_32FC3);

        Mat edges = new Mat();
        Imgproc.Canny(src,edges,200,500);
        imshow("edges",edges);


        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges,lines,1,Math.PI /180,5);
        System.out.println("直线数：" + lines.rows());
//        // 为啥lines一行是四个数？？？不应该是两个么？？？
//        //  返回的是（x0,y0）,(x1,y1)，已经帮我们转换到了直角坐标系空间，并提供了线上的两个点
//        System.out.println(lines.depth()); // 4
//        System.out.println(lines.channels());  // 4
//        System.out.println(lines.rows());   // 96
//        System.out.println(lines.cols());   // 1
        for (int i = 0; i < lines.rows(); i++) {
            double[] doubles = lines.get(i, 0);
            //System.out.println(Arrays.toString(doubles));
            Imgproc.line(src,new Point(doubles[0],doubles[1]),
                    new Point(doubles[2],doubles[3]),
                    new Scalar(255,0,0),2,2);
        }

        imshow("line_detected",src);
        waitKey(0);
    }


    public static void main(String[] args) {
        //findPoints();
        ppht("D:\\image\\20190715\\222.png");
    }
}
