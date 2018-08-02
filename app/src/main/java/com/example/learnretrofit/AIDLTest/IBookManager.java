package com.example.learnretrofit.AIDLTest;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by 杨豪 on 2018/7/30.
 */

public interface IBookManager extends IInterface {

    static final String DESCRIPTOR = "com.example.learnretrofit.AIDLTest.IBookManager";
    //这个常量的规则是"包名.接口名"

    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;
    //以上的变量规则是，我们声明了几个方法就生命几个变量，格式类似，变量值依次递加


    public List<Book> getBookList()throws RemoteException;
    public void addBook(Book book)throws RemoteException;
}
