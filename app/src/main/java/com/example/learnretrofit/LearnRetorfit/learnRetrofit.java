package com.example.learnretrofit.LearnRetorfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.learnretrofit.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class learnRetrofit extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main01);


        requestNetByRetrofitTest04();


    }


    /**
     * 动态，静态,消息报头Header
     */
    public interface IpServiceTest08{
        @GET("这里写请求网址")
        @Headers({
                "Accept-Encoding:application/json",
                "User-Agent:MoonRetrofit"
        })
        Call<RequestBody> getCarType();
    }
    public interface IpServiceTest09{
        @GET("这里写请求网址")
        Call<RequestBody> getCarType(

                @Header("Accept-Encoding") String s
        );
    }


    /**
     * 上传多文件
     */
    public interface IpServiceTest07{
        @Multipart
        @POST("这里写请求网址")
        Call<MyData> updataMyData(@PartMap Map<String,MultipartBody.Part> datas, @Part("description")RequestBody description);
    }



    /**
     * 上传单个文件
     */
    public interface IpServiceTest06{
        @Multipart
        @POST("这里写请求网址")
        Call<MyData> updataMyData(@Part MultipartBody.Part data, @Part("description")RequestBody description);
    }



    /**
     * 传输数据为字符串的接口
     */
    public interface IpServiceTest05{
        @POST("femaleNameApi")
        Call<NetNameApiDate> getcall(@Body MyData da);
    }
    class MyData{
        String i;
        public MyData(String i){
            this.i = i;
        }
    }



    /**
     * POST 请求
     */
    public void requestNetByRetrofitTest04(){
        String url = "https://www.apiopen.top/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpServiceTest04 ipServiceTest04 = retrofit.create(IpServiceTest04.class);
        Call<NetNameApiDate> call = ipServiceTest04.getcall("1");
        call.enqueue(new Callback<NetNameApiDate>() {
            @Override
            public void onResponse(Call<NetNameApiDate> call, Response<NetNameApiDate> response) {
                NetNameApiDate.Name[] dates = response.body().data;

                for (int i = 0;i < dates.length ; i++){
                    Log.d(TAG, "onResponse: "+dates[i].femalename);
                }

            }

            @Override
            public void onFailure(Call<NetNameApiDate> call, Throwable t) {

            }
        });
    }
    public interface IpServiceTest04{
        @FormUrlEncoded
        @POST("femaleNameApi")
        Call<NetNameApiDate> getcall(@Field("page") String page);
    }


    /**
     * 动态指定地址，动态改变查询条件
     */
    public void requestNetByRetrofitTest03(){
        String url = "https://www.apiopen.top/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpServiceTest03 ipServiceTest03 = retrofit.create(IpServiceTest03.class);
        Call<NetNameApiDate> call = ipServiceTest03.getcall("femaleNameApi","1");
        call.enqueue(new Callback<NetNameApiDate>() {
            @Override
            public void onResponse(Call<NetNameApiDate> call, Response<NetNameApiDate> response) {
                NetNameApiDate.Name[] dates = response.body().data;

                for (int i = 0;i < dates.length ; i++){
                    Log.d(TAG, "onResponse: "+dates[i].femalename);
                }

            }

            @Override
            public void onFailure(Call<NetNameApiDate> call, Throwable t) {

            }
        });
    }
    public interface IpServiceTest03{
        @GET("{path}")
        Call<NetNameApiDate> getcall(@Path("path") String path, @Query("page") String  page);
    }




    /**
     * 展示retrofit包装数据效果
     */
    public void requestNetByRetrofitTest02(){
        String url = "https://www.apiopen.top/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpServiceTest02 ipServiceTest02 = retrofit.create(IpServiceTest02.class);
        Call<NetNameApiDate> call = ipServiceTest02.getcall();
        call.enqueue(new Callback<NetNameApiDate>() {
            @Override
            public void onResponse(Call<NetNameApiDate> call, Response<NetNameApiDate> response) {
                NetNameApiDate.Name[] dates = response.body().data;

                for (int i = 0;i < dates.length ; i++){
                    Log.d(TAG, "onResponse: "+dates[i].femalename);
                }

            }

            @Override
            public void onFailure(Call<NetNameApiDate> call, Throwable t) {

            }
        });
    }
    public interface IpServiceTest02{
        @GET("femaleNameApi?page=1")
        Call<NetNameApiDate> getcall();
    }





    /**
     * 通过retrofit简单的访问网络
     */
    public void requestNetByRetrofitTest01(){
        String url = "https://tieba.baidu.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        IpServiceTest01 ipService = retrofit.create(IpServiceTest01.class);
        Call<ResponseBody> call = ipService.getIpMsg();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
                try {
                    s = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: "+s);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public interface IpServiceTest01 {
        @GET("index.html?traceid=")
        Call<ResponseBody> getIpMsg();
    }

}
