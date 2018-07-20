package com.example.learnretrofit.LearnOkHttp;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 杨豪 on 2018/6/13.
 */

public class OkHttpEngine_yibu {
//    private volatile static Singleton

    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private static OkHttpEngine_yibu mIntance;

    public static OkHttpEngine_yibu getInstance(Context context){
        if(mIntance == null){
            synchronized (OkHttpEngine_yibu.class){
                if(mIntance == null){
                    mIntance = new OkHttpEngine_yibu(context);
                }
            }
        }
        return mIntance;
    }
    private OkHttpEngine_yibu(Context context){
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(),cacheSize));
        mOkHttpClient = builder.build();
        mHandler = new Handler();
    }


    public void getAsynHttp(String url, ResultCallBack callBack){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        dealResult(call,callBack);
    }

    private void dealResult(Call call,final ResultCallBack callBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(call.request(),e,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response,callBack);
            }
            private void sendSuccessCallback(final Response object, final ResultCallBack callBack) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack != null){
                            try{
                                callBack.onResponse(object);
                            }catch (IOException e){

                            }
                        }
                    }
                });
            }
            private void sendFailCallback(final Request request, final IOException e, final ResultCallBack callBack) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack != null){
                            callBack.onError(request,e);
                        }
                    }
                });
            }
        });
    }





}
