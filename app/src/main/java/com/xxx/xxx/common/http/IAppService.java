package com.xxx.xxx.common.http;

import com.google.gson.JsonElement;
import com.hongyu.zorelib.bean.BaseEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface IAppService {


    @POST()
    @FormUrlEncoded
    Observable<BaseEntity<JsonElement>> postCommon(@Url String url, @FieldMap Map<String, Object> params);

    @POST()
    Observable<BaseEntity<JsonElement>> postCommon(@Url String url, @Body RequestBody requestBody);

    @GET()
    Observable<BaseEntity<JsonElement>> getCommon(@Url String url, @QueryMap Map<String, Object> params);

    @POST()
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    Observable<BaseEntity<JsonElement>> postJson(@Url String url, @Body RequestBody body);


    @POST()
    @FormUrlEncoded
    Observable<String> pullSmzr(@Url String url, @FieldMap Map<String, String> map);

    @Multipart
    @POST()
    Observable<BaseEntity<JsonElement>> putVideo(@Url String url, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST()
    Observable<BaseEntity<JsonElement>> putVideo(@Url String url, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);
}
