package com.hongyu.zorelib.mvp.view;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hongyu.zorelib.ActivityControl;
import com.hongyu.zorelib.R;
import com.hongyu.zorelib.mvp.presenter.BasePresenterCml;
import com.hongyu.zorelib.utils.DensityTools;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by isle on 2017/7/29.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

    private Unbinder unbinder;

    private AlertDialog loadDialog;

    protected ActivityControl ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = getApplication();
        if (application instanceof ActivityControl) {
            ac = (ActivityControl) application;
        }
        logicBeforeInitView();
        try {

            int layoutId = getLayoutId();
            setContentView(layoutId);
            unbinder = ButterKnife.bind(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        logicAfterInitView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        removeActivity();
    }

    protected void startActivity(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    protected void startActivityWithParams(Class clazz, Map<String,Object> params){

    }

    /**
     * 初始化之前的逻辑
     */
    protected abstract void logicBeforeInitView();

    /**
     * 获取布局
     * @return
     */
    public abstract int getLayoutId() ;

    /**
     * 初始化之后的逻辑
     */
    protected abstract void logicAfterInitView();



    /**
     * running when activity destroy
     */
    protected abstract void removeActivity();

    protected void toastShort(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    protected void toastLong(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected Object transToClass(String str ,Class clazz){
        Gson gson = new Gson();
        return gson.fromJson(str,clazz);
    }

    protected Object transToClass(String str ,Class clazz,Gson gson){
        return gson.fromJson(str,clazz);
    }

    public void loading(){
        if (loadDialog == null) {
            loadDialog = new AlertDialog.Builder(this)
                    .create();
            loadDialog.getWindow().setDimAmount(0);//设置昏暗度为0
//            loadDialog.getWindow().setBackgroundDrawable(null);
        }

        loadDialog.show();
        loadDialog.setContentView(getLayoutInflater().inflate(R.layout.dialog_loading,null));
        WindowManager.LayoutParams params = loadDialog.getWindow().getAttributes();
        params.width = DensityTools.dp2px(this,120);
        params.height = DensityTools.dp2px(this,120);
        loadDialog.getWindow().setAttributes(params);
        loadDialog.getWindow().setBackgroundDrawable(null);

    }

    public void dismissLoad(){
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

}
