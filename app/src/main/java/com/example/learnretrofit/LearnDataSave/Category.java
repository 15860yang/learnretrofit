package com.example.learnretrofit.LearnDataSave;

/**
 * Created by 杨豪 on 2018/6/9.
 */

public class Category {
    private int id;
    private String categoryName;
    private int categroyCode;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategroyCode() {
        return categroyCode;
    }

    public void setCategroyCode(int categroyCode) {
        this.categroyCode = categroyCode;
    }
}
