package com.example.learnretrofit.LearnMVVM;

/**
 * Created by 杨豪 on 2018/6/6.
 */

/**
 *
 * 数据类
 *
 */
public class modelClass {
    private String name;
    private String level;

    public modelClass(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
