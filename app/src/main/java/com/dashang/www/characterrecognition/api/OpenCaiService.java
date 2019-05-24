package com.dashang.www.characterrecognition.api;

/*
*
* 开彩网api 获取彩票的中奖信息
*[彩票代码]-[返回行数].[返回格式]
* "dlt.json"
* */

import com.dashang.www.characterrecognition.bean.OpenCaiBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenCaiService {

    /*
    *
    * 获取指定的彩票的中将json数组
    * */

    @GET("{format}")
    Observable<OpenCaiBean> getOpenJson(@Path("format") String format);

}
