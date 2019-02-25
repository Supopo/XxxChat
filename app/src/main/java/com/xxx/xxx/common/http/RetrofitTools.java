package com.xxx.xxx.common.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitTools {
    private Retrofit retrofit;

    public static RetrofitTools getInstance() {
        return InstanceHolder.sInstance;
    }

    private static class InstanceHolder {
        public final static RetrofitTools sInstance = new RetrofitTools();
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            // log日志拦截
            //            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> LogUtils.json(LogUtils.I, "wyb_json", message));
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.json(com.xxx.xxx.common.http.LogUtils.I, "--json", message);
                }
            });
            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .removeHeader("User-Agent")//移除旧的
                                    .addHeader("User-Agent", "okhttp3.0")//添加真正的头部
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
            retrofit = new Retrofit.Builder()
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(getUrlBase())
                    .build();
        }
        return retrofit;
    }

    private <T> T create(Class<T> apiService) {
        return getRetrofit().create(apiService);
    }

    public IAppService getService() {
        return create(IAppService.class);
    }

    private String getUrlBase() {
        return API.BASE_URL;
    }
}
