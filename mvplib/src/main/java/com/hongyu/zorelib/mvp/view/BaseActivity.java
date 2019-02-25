package com.hongyu.zorelib.mvp.view;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hongyu.zorelib.ActivityControl;
import com.hongyu.zorelib.R;
import com.hongyu.zorelib.utils.ActivityManage;
import com.hongyu.zorelib.utils.DensityTools;
import com.hongyu.zorelib.utils.WindowUtils;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * base
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private AlertDialog loadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManage.addActivity(this);
        logicBeforeInitView();
        WindowUtils.setImmersion(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            int layoutId = getLayoutId();
            setContentView(layoutId);
            unbinder = ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logicAfterInitView();
        initData();
    }


    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.finishSingleActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithParams(Class clazz, Map<String, Object> params) {

    }

    /**
     * 初始化之前的逻辑
     */
    protected abstract void logicBeforeInitView();

    /**
     * 获取布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化之后的逻辑
     */
    protected abstract void logicAfterInitView();

    protected void toastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected <T> T transToClass(String str, Class<T> clazz) {
        Gson gson = getGson();
        return gson.fromJson(str, clazz);
    }

    protected Gson getGson() {
        return new Gson();
    }

    protected Object transToClass(String str, Class clazz, Gson gson) {
        return gson.fromJson(str, clazz);
    }

    protected void loading() {
        if (loadDialog == null) {
            loadDialog = new AlertDialog.Builder(this)
                    .create();
            //   loadDialog.setCanceledOnTouchOutside(false);
            loadDialog.setCancelable(false);
            loadDialog.getWindow().setDimAmount(0);//设置昏暗度为0
//            loadDialog.getWindow().setBackgroundDrawable(null);
        }

        loadDialog.show();
        loadDialog.setContentView(getLayoutInflater().inflate(R.layout.dialog_loading, null));
        WindowManager.LayoutParams params = loadDialog.getWindow().getAttributes();
        params.width = DensityTools.dp2px(this, 120);
        params.height = DensityTools.dp2px(this, 120);
        loadDialog.getWindow().setAttributes(params);
        loadDialog.getWindow().setBackgroundDrawable(null);

    }

    public void dismissLoad() {
        if (loadDialog != null) {
            if (loadDialog.isShowing()) {
                loadDialog.dismiss();
            }
        }
    }
}
