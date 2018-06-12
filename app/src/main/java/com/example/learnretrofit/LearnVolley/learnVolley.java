package com.example.learnretrofit.LearnVolley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by 杨豪 on 2018/6/11.
 */

public class learnVolley {

    private static final String TAG = "learnVolley";

    public static void useVolley(Context appContext){

        RequestQueue queue = Volley.newRequestQueue(appContext);



        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://tieba.baidu.com/index.html"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, "onResponse: 请求成功" + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "onErrorResponse: 请求失败");
            }
        });

        queue.add(stringRequest);
    }


}
