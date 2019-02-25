package com.github.library.xUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by 苏坡坡要吃婆婆酥 on 2018/9/12.
 */

public class Image2Base64Utils {

    public static String GetImageStr(String imageUrl) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        Bitmap bitmap = convertToBitmap(imageUrl);
        //压缩图片
        Bitmap bitmap1 = compressImage(bitmap);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, bao);

        byte[] data = bao.toByteArray();

        //对字节数组Base64编码
        System.out.println("byte的大小" + data.length / 1024);
        return new String(android.util.Base64.encode(data, android.util.Base64.DEFAULT));//返回Base64编码过的字节数组字符串
    }

    public static String GetImageBit(Bitmap bitmap) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //压缩图片
        Bitmap bitmap1 = compressImage(bitmap);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, bao);

        byte[] data = bao.toByteArray();

        //对字节数组Base64编码
        return new String(android.util.Base64.encode(data, android.util.Base64.DEFAULT));//返回Base64编码过的字节数组字符串
    }

    public static Bitmap convertToBitmap(String path) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1280f;//这里设置高度为800f
        float ww = 780f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 150) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            System.out.println(options);
            if (options < 40) {
                break;
            }

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
