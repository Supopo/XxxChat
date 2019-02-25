package com.hongyu.zorelib.mvp.presenter;

import android.accounts.NetworkErrorException;

import com.google.gson.Gson;
import com.hongyu.zorelib.bean.BaseEntity;
import com.hongyu.zorelib.exception.WineChainExcepTion;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by isle on 2017/7/28.
 */

public class BasePresenterCml {
    protected static String getSecondTime() {
        long timeStampSec = System.currentTimeMillis() / 1000;
        return String.format("%010d", timeStampSec);
    }

    protected Gson g = new Gson();

    protected Map<String, String> getParams() {
        return new HashMap<>();
    }

    protected <T> Observable<T> transform(Observable<BaseEntity<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((baseBean) -> {
                    if (baseBean == null) {
                        return Observable.error(new NetworkErrorException());
                    } else if (baseBean.code == 200) {
                        return Observable.just(baseBean.data);
                    } else if (baseBean.code == 115) {
                        return Observable.error(new WineChainExcepTion("账号已在其他设备登录"));
                    } else {
                        return Observable.error(new WineChainExcepTion(baseBean.msg));
                    }
                });
    }


}
