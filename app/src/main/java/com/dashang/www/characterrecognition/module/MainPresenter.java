package com.dashang.www.characterrecognition.module;

import android.graphics.Bitmap;

import com.dashang.www.characterrecognition.api.BaiDuOCRService;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* mvp 中的Presenter  处理数据请求
* */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private final BaiDuOCRService mBaiDuOCRService;
    private String CLIENT_CREDENTIALS = "client_credentials";
    private String API_KEY = "b0Ckzc8ZPY4xl9lYBklDrAW9";
    private String SECRET_KEY = "B0pG23lTQMxhiOHUUxGTeupBHFti1F1R";
    private String ACCESS_TOKEN = "client_credentials";

    public MainPresenter(MainContract.View mView){
        this.mView = mView;

         Retrofit retrofit =new Retrofit.Builder()
                 .baseUrl("https://api.baidubce.com/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .build();
        mBaiDuOCRService = retrofit.create(BaiDuOCRService.class);


    }


    /*
     * 实现网络请求的逻辑  使用RxJava+Retrofit进行实现
     * */

    @Override
    public void getAccessToken() {
//        mBaiDuOCRService.getAccessToken(CLIENT_CREDENTIALS,API_KEY,SECRET_KEY)




    }

    @Override
    public void getRecognitionResultByImage(Bitmap bitmap) {

    }


}
