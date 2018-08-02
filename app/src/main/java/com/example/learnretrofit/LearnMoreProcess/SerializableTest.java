package com.example.learnretrofit.LearnMoreProcess;

import java.io.Serializable;

/**
 * Created by 杨豪 on 2018/7/30.
 */
class SerializableTest implements Serializable {
    private static final long serialVersionUID = 1353513683568431L;

    public SerializableTest(String name, int id) {
        this.name = name;

    }
    public int sss;

    public String name ;

}
