package com.hongyu.zorelib.mvp.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hongyu.zorelib.R;
import com.hongyu.zorelib.mvp.presenter.BasePresenterCml;
import com.hongyu.zorelib.utils.DensityTools;
import com.hongyu.zorelib.utils.WindowUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by isle on 2017/8/3.
 */

public abstract class BaseFragment extends Fragment {

    private AlertDialog loadDialog;
    private Unbinder bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logicBeforInitView();
    }

    // the mathod running before View init
    protected abstract void logicBeforInitView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = setView();
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logicAfterInitView();
        initData();
    }

    protected abstract View setView();
    /**
     * 初始化之后的逻辑
     */
    protected abstract void logicAfterInitView();

    protected  void initData(){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    protected void toastShort(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastLong(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    protected Object transToClass(String str, Class clazz) {
        Gson gson = new Gson();
        return gson.fromJson(str, clazz);
    }

    protected Object transToClass(String str, Class clazz, Gson gson) {
        return gson.fromJson(str, clazz);
    }

    protected void loading() {
        if (loadDialog == null) {
            loadDialog = new AlertDialog.Builder(getContext())
                    .create();
            loadDialog.setCanceledOnTouchOutside(false);
            loadDialog.getWindow().setDimAmount(0);//设置昏暗度为0
//            loadDialog.getWindow().setBackgroundDrawable(null);
        }

        loadDialog.show();
        loadDialog.setContentView(View.inflate(getActivity(),R.layout.dialog_loading, null));
        WindowManager.LayoutParams params = loadDialog.getWindow().getAttributes();
        params.width = DensityTools.dp2px(getContext(), 120);
        params.height = DensityTools.dp2px(getContext(), 120);
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

    protected void startActivity(Class clazz) {
        Intent intent = new Intent(getContext(), clazz);
        this.startActivity(intent);
    }

}
