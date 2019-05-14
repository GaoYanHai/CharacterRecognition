package com.dashang.www.characterrecognition.module;

import android.content.Context;
import android.graphics.Bitmap;

/*
*
* mvp框架中的协议类
* */
public interface MainContract {
    interface View{
        // presenter 获取数据后调用此方法将数据展示到界面中去  获取到的数据
        void updateUI(String s);
        //获取失败  展示错误信息
        void dataError(Throwable e);

    }

    interface Presenter{
        //获取数据
        void getAccessToken();
        void getRecognitionResultByImage(Bitmap bitmap, Context context);

    }
}
