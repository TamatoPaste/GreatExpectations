package com.bupt.chapter5;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.ml.Boost;
import org.opencv.ml.Ml;

public class S1_BoostTheory_AdaBoost {
    static { System.loadLibrary( Core.NATIVE_LIBRARY_NAME );}

    public static void main(String[] args) {
        Mat data = new Mat(5, 3, CvType.CV_32FC1, new Scalar(0));

        // 赋值
        data.put(0,0,new float[]{1.69f,1,0});
        data.put(1,0,new float[]{1.76f,0,0});
        data.put(2,0,new float[]{1.80f,0,0});
        data.put(3,0,new float[]{1.83f,0,1});
        data.put(4,0,new float[]{1.77f,0,0});

        Mat response = new Mat(5, 1, CvType.CV_32SC1, new Scalar(0));

        response.put(0,0,new int[]{0,1,1,0,1});

        Boost boost = Boost.create();
        boost.setBoostType(Boost.DISCRETE);
        boost.setWeakCount(3);
        boost.setMinSampleCount(4);

        boost.train(data, Ml.ROW_SAMPLE,response);

        for (int i = 0; i < 5; i++){
            System.out.println("Result = " + boost.predict(data.row(i)));
        }

        Mat newPerson1 = new Mat(1, 3, CvType.CV_32FC1, new Scalar(0));
        newPerson1.put(0,0,new float[]{1.60f,1,0});
        System.out.println(" new woman 1.6 长发 无须 ：" + boost.predict(newPerson1));

        Mat newPerson2 = new Mat(1, 3, CvType.CV_32FC1, new Scalar(0));
        newPerson2.put(0,0,new float[]{1.80f,0,1});
        System.out.println(" new man ：1.8 短发 有须 ： " + boost.predict(newPerson2));


        Mat newPerson3 = new Mat(1, 3, CvType.CV_32FC1, new Scalar(0));
        newPerson3.put(0,0,new float[]{1.7f,1,0});
        System.out.println(" new man ：1.7 短发 有须 ： " + boost.predict(newPerson3));
    }
}
