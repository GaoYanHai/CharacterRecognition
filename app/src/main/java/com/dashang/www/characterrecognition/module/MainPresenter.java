package com.dashang.www.characterrecognition.module;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.dashang.www.characterrecognition.api.BaiDuOCRService;
import com.dashang.www.characterrecognition.bean.AccessTokenBean;
import com.dashang.www.characterrecognition.bean.RecognitionResultBean;
import com.dashang.www.characterrecognition.utils.RegexUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
* mvp 中的Presenter  处理数据请求
* */
public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
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
        mBaiDuOCRService.getAccessToken(CLIENT_CREDENTIALS, API_KEY, SECRET_KEY)
                .subscribeOn(Schedulers.io())      //io 线程中执行
                .observeOn(AndroidSchedulers.mainThread())     //返回ui线程中执行
                .subscribe(new Observer<AccessTokenBean>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AccessTokenBean accessTokenBean) {
                        Log.e(TAG, "onNext: "+accessTokenBean.getAccess_token() );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });





    }

    @Override
    public void getRecognitionResultByImage(Bitmap bitmap) {
        String encodeResult = bitmapToString(bitmap);
        mBaiDuOCRService.getRecognitionResultByImage(ACCESS_TOKEN,encodeResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecognitionResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecognitionResultBean recognitionResultBean) {
                        ArrayList<String> wordList = new ArrayList<>();
                        List<RecognitionResultBean.WorldsBean> worldsResult = recognitionResultBean.getWords_result();
                        for (RecognitionResultBean.WorldsBean words : worldsResult){
                            wordList.add(words.getWorlds());
                        }

                        ArrayList<String> numbs = RegexUtil.getNumbs(wordList);
                        StringBuilder s = new StringBuilder();
                        for (String numb:numbs){
                            s.append(numb+"\n");
                        }
                        mView.updateUI(s.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    //bitmap  转字符串
    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap!=null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return "";

    }


}
