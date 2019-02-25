package com.xxx.xxx.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownLoadImageUtil {
    public  static  String CachDir = Environment.getExternalStorageDirectory()+ File.separator+"HeatDir";
    public interface FinishListener {
        void finish(File imgFile);
    }

    public static void loadImg(String img,FinishListener listener) {
        Looper.prepare();
        try {
            URL url = new URL(img);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            File caChDir = new File(CachDir);
            if (!caChDir.exists()) {
                caChDir.mkdirs();
            }
            File myCaptureFile = getImgFile(img);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            listener.finish(getImgFile(img));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File getImgFile(String img){
        File caChDir = new File(CachDir);
        return new File(caChDir +"/"+ AppMD5Util.MD5(img)+".jpg");
    }

}
