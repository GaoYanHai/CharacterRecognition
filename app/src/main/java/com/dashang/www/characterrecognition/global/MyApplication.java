package com.dashang.www.characterrecognition.global;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/*
*
* 在全局获取上下文
* */
public class MyApplication extends Application {

    /** 用来保存当前该Application的context */
    private static Context instance;
    /** 用来保存最新打开页面的context */
    private volatile static WeakReference<Context> instanceRef = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                //String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "logcat.log";
                //File file = new File(path);

//                try {
//                    PrintWriter printWriter = new PrintWriter(file);
//                    e.printStackTrace(printWriter);
//                    printWriter.close();
//                } catch (FileNotFoundException e1) {
//                    e1.printStackTrace();
//                }
                //上传至服务器中

                //结束应用
                System.exit(0);
            }
        });

    }

    //获取全局唯一上下文类
    public static Context getInstance(){
        if (instanceRef == null || instanceRef.get() == null){
            synchronized (MyApplication.class) {
                if (instanceRef == null || instanceRef.get() == null) {
                    //Context context = ActivityManager.getInstance().getActivity();
                   // if (context != null)
                        //instanceRef = new WeakReference<>(context);
                    //else {
                        instanceRef = new WeakReference<>(instance);
                        //L.w("请确保RootActivity调用setInstanceRef方法");
                   // }
                }
            }
        }
        return instanceRef.get();
    }



}
