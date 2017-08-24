package com.scorpio.framework.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ScreenUtil {
    public static int screenWidth;
    public static int screenHeight;
    public static float density;
    public static float densityDpi;

    private static Properties properties;

    public static int dip2px(float dipValue) {
        return (int) (dipValue * density);
    }

    public static int px2dip(float pxValue) {

        return (int) (pxValue / density);
    }

    public static float adapterPaintTextSize(float paintTextSize){
        return paintTextSize * density;
    }

    private static Properties getProperties(){
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }

}
