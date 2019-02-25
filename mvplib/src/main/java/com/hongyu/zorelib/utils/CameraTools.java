package com.hongyu.zorelib.utils;

/**
 * Created by isle on 2017/8/9.
 */

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;

/**
 * Created by honjane on 2016/9/11.
 */

public class CameraTools {

    private static String APP_DIR_NAME = "ostsz";
    private static String FILE_DIR_NAME = "files";
    private static String mRootDir;
    private static String mAppRootDir;
    private static String mFileDir;

    public static void init() {
        mRootDir = getRootPath();
        if (mRootDir != null && !"".equals(mRootDir)) {
            mAppRootDir = mRootDir + "/" + APP_DIR_NAME;
            mFileDir = mAppRootDir + "/" + FILE_DIR_NAME;
            File appDir = new File(mAppRootDir);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            File fileDir = new File(mAppRootDir + "/" + FILE_DIR_NAME);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

        } else {
            mRootDir = "";
            mAppRootDir = "";
            mFileDir = "";
        }
    }

    public static String getFileDir() {
        return mFileDir;
    }


    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context     activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public static void startActionFile(Context context, File file, String contentType) throws ActivityNotFoundException {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriForFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param file        File
     * @param requestCode result requestCode
     */
    public static void startActionCapture(Activity activity, File file, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * PICK PHOTO
     * 兼容7.0
     *
     * @param activity    Activity
     * @param requestCode result requestCode
     */
    public static void starPictrueSelector(Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }


    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.hzx.ostsz.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    /**
     * 打开相机
     * @param pictureMimeType 选择类型
     */
    public  static  void  openCamera(Activity  mActivity,int pictureMimeType){
        PictureSelector.create(mActivity)
                .openCamera(pictureMimeType)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .compress(true)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    /**
     * 打开相机
     * @param pictureMimeType 选择类型
     */
    public  static  void  openCamera(Fragment  fragment,int pictureMimeType){
        PictureSelector.create(fragment)
                .openCamera(pictureMimeType)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .compress(true)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(10)// 显示多少秒以内的视频 or 音频也可适用 int
                .recordVideoSecond(10)//视频秒数录制 默认 60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 打开图片选择器
     *
     */
    public static void openPictureSelector(Fragment fragment,int pictureMimeType) {
            PictureSelector.create(fragment)
                    .openGallery(pictureMimeType)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .maxSelectNum(1)// 最大图片选择数量
                    .isCamera(false)// 是否显示拍照按钮
                    .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .previewImage(true)// 是否可预览图片
                    .compress(true)
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 打开图片选择器
     */
    public static void openPictureSelector(Activity  mActivity,int pictureMimeType) {
        PictureSelector.create(mActivity)
                .openGallery(pictureMimeType)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .maxSelectNum(1)// 最大图片选择数量
                .isCamera(false)// 是否显示拍照按钮
                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .previewImage(true)// 是否可预览图片
                .compress(true)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 判断是否开启相机权限
     * @return 是 否
     */
    public static boolean isHaveCameraPermission() {
        boolean isHave = true;
        Camera camera;
        try {
            camera = Camera.open();
            Camera.Parameters mParameters = camera.getParameters();
            camera.setParameters(mParameters);
            camera.release();
        } catch (Exception e) {
            isHave = false;
        }
        return isHave;
    }

}
