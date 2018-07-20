package com.example.learnretrofit.LearnOkHttp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 杨豪 on 2018/6/12.
 */

public class LearnOkHttp {
    private String TAG = "learnOkHttp";
    private Context context;

    //异步GET请求
    public void RequestGET(){
        OkHttpClient client = new OkHttpClient.Builder().build();

        OkHttpClient client1 = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                Log.d(TAG, "RequestGET: "+response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //异步POST请求
    public void RequestPOST(){
        RequestBody requestBody = new FormBody.Builder()
                .add("","")
                .build();
        Request request = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onResponse: post请求失败："+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: post请求成功："+response.body().string());
            }
        });
    }

    //异步上传文件
    public void RequestUpLoadFile(){
        MediaType TYPE = null;
        File file = null;
        Request request = new Request.Builder()
                .url("")
                .post(RequestBody.create(TYPE,file))
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onResponse: 上传文件请求失败："+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: 上传文件请求成功："+response.body().string());
            }
        });
    }

    //异步下载文件
    public void RequestDownLoadFile(){
        String url = null;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOuputStream = null;
                String filePath = "";
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    }else {
                    }
                    File file = new File(filePath,"asdas.ipg");
                    if(file != null){
                        fileOuputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[2048];
                        int len = 0;
                        while ( (len = inputStream.read(buffer)) != 0 ){
                            fileOuputStream.write(buffer,0,len);
                        }
                        fileOuputStream.flush();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    //异步上传Multipart文件
    public void RequestUpLoadMultipartFile(){
        MediaType MEDIA_TYPE_PNG = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(new Cache(new File("").getAbsoluteFile(),1))
                .build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MEDIA_TYPE_PNG)
                .addFormDataPart("","")
                .addFormDataPart("","",
                        RequestBody.create(MEDIA_TYPE_PNG,new File("这里是路径")))
                .build();

        Request request = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        //线程池的用法
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                call.cancel();
            }
        },100,TimeUnit.MILLISECONDS);
    }
    

    //使用封装好的OkHttp请求网络
    public void RequestUseMyOkHttp(){
        OkHttpEngine_yibu.getInstance(context).getAsynHttp("",new ResultCallBack(){
            @Override
            public void onResponse(Response response) throws IOException {
                String str = response.body().string();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

}
