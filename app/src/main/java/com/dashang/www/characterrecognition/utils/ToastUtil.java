package com.dashang.www.characterrecognition.utils;

/*
* 工具 土司
* */

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static boolean isShow = true;  //默认显示
    private static Toast mToast = null;  //全局唯一

    // 不能被实例化
    private ToastUtil(){
        throw new UnsupportedOperationException("不能被实例化");
    }

    //全局控制是否显示toast
    public static void controlShow(boolean isShowToast){
        isShow = isShowToast;
    }

    //取消显示toast
    public void cancelToast(){
        if (isShow&&mToast!=null){
            mToast.cancel();
        }
    }

    //短时间显示
    public static void showShort(Context context,CharSequence message){
        if (isShow){
            if (mToast==null){
                mToast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
            }else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    //长时间显示
    public static void showLong(Context context,CharSequence message){
        if (isShow){
            if (mToast==null){
                mToast = Toast.makeText(context,message,Toast.LENGTH_LONG);
            }else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }


}
