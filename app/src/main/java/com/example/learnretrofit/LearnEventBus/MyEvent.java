package com.example.learnretrofit.LearnEventBus;

/**
 * Created by 杨豪 on 2018/6/3.
 */

public class MyEvent {

    private String message;
    public MyEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
