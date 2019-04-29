package com.dashang.www.characterrecognition.api;

//import android.database.Observable;

import com.dashang.www.characterrecognition.bean.AccessTokenBean;
import com.dashang.www.characterrecognition.bean.RecognitionResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
*
* retrofit网络请求部分
* */
public interface BaiDuOCRService {
    /*
    *获取百度api的token
    * */

    @POST("oauth/2.0/token")
    Observable<AccessTokenBean> getAccessToken(@Query("grant_type") String grantType, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);



    /*
    * 通过图片获取文字内的信息
    * accessToKen  通过请求获取的token
    * image 图像数据base64编码后进行urlencode后的String
    * observable对象用于rxjava,从RecognitionResultBean中可以获得图片文字识别的信息
    *
    * */

    @POST("rest/2.0/ocr/v1/general_basic")
    @FormUrlEncoded
    Observable<RecognitionResultBean> getRecognitionResultByImage(@Field("access_token") String accessToken, @Field("image") String image);


}
