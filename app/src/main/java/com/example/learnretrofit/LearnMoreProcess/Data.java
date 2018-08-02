package com.example.learnretrofit.LearnMoreProcess;

import java.io.Serializable;

/**
 * Created by 杨豪 on 2018/7/29.
 */

public class Data implements Serializable{
    public int id;
    public String name;

    public Data(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
