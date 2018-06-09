package com.example.learnretrofit.LearnMVVM.Mvvm000;

/**
 * Created by 杨豪 on 2018/6/6.
 */

public class modelClass {
    private String namnamee;
    private String levellevel;

    public modelClass(String namnamee, String levellevel) {
        this.namnamee = namnamee;
        this.levellevel = levellevel;
    }

    public String getNamnamee() {
        return namnamee;
    }

    public void setNamnamee(String namnamee) {
        this.namnamee = namnamee;
    }

    public String getLevellevel() {
        return levellevel;
    }

    public void setLevellevel(String levellevel) {
        this.levellevel = levellevel;
    }
}