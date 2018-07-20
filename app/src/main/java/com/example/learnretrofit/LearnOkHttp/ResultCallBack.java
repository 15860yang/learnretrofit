package com.example.learnretrofit.LearnOkHttp;

/**
 * Created by 杨豪 on 2018/6/13.
 */

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 的简单封装，抽象回调类
 *
 */

public abstract class ResultCallBack {
    public abstract void onError(Request request,Exception e);
    public abstract void onResponse(Response response)throws IOException;
}
