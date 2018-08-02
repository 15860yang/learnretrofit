package com.example.learnretrofit.LearnMoreProcess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.learnretrofit.AIDLTest.Book;
import com.example.learnretrofit.AIDLTest.BookManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨豪 on 2018/7/30.
 */

public class process1Service extends Service {

    private static final String TAG = "process1Service";

    List<Book> mBooks = new ArrayList<>();

    BookManagerImpl mBookManager = new BookManagerImpl() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "getBookList: ");
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: 返回给操作Service的Binder");
        return mBookManager;
    }
}
