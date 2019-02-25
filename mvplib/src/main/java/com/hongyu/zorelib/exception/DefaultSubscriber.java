package com.hongyu.zorelib.exception;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

public abstract class DefaultSubscriber<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        String reason;
        if (e instanceof JsonSyntaxException) {//数据格式化错误
            reason = "数据格式化错误";
        } else if (e instanceof HttpException) {// http异常
            reason = "服务器异常";
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {//未连接网络或DNS错误
            reason = "未连接网络";
        } else if (e instanceof NetworkErrorException) {
            reason = "网络错误";
        } else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
            reason = "连接超时";
        } else if (e instanceof WineChainExcepTion) {
            reason = e.getMessage();
        } else {
            reason = "未知错误";
        }
   /*     if (reason.equals("账号已在其他设备登录")) {
            reLogin("账号已在其他设备登录");
        } else {
            _onError(reason);
        }*/
        _onError(reason);
        e.printStackTrace();
    }


    @Override
    public void onNext(T entity) {
        _onNext(entity);
    }

    public abstract void _onNext(T entity);

    public abstract void _onError(String e);

    public void reLogin(String msg) {

    }

    @Override
    public void onCompleted() {

    }


}
