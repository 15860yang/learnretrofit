package com.example.learnretrofit.LearnMoreProcess;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.learnretrofit.AIDLTest.*;
import com.example.learnretrofit.AIDLTest.Book;
import com.example.learnretrofit.LearnMoreProcess.BinderPool.BinderPoolActivity;
import com.example.learnretrofit.LearnMoreProcess.MyContentProvider.ProviderActivity;
import com.example.learnretrofit.LearnMoreProcess.SocketTest.SocketActivity;
import com.example.learnretrofit.LearnService.LocalService;
import com.example.learnretrofit.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class LearnMoreProcessActivity extends AppCompatActivity {

    private static final String TAG = "LearnMoreProcess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more_process);

        initView();
//        bindMyAppleService();
    }

    void initView(){
        findViewById(R.id.toActivity2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LearnMoreProcessActivity.this,LearnMoreProcess2Activity.class);
                startActivity(i);
            }
        });

        final EditText editText = findViewById(R.id.process1ET);

        findViewById(R.id.process1AddBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if(mIBookManager != null){
                    try {
                        mIBookManager.addBook(new Book(1,s));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        findViewById(R.id.process1GetBookList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Book> books = (ArrayList<Book>) mIBookManager.getBookList();
                    Log.d(TAG, "onClick: 获取booklist成功，开始输出");
                    for(Book book : books){
                        Log.d(TAG, "onClick: book.bookName = " + book.bookName);
                    }
                    Log.d(TAG, "onClick: 输出完毕");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.process1SaveData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = new Data(10,"这是主进程写的数据");
                try {
                    ObjectOutputStream out = new ObjectOutputStream(
                            new FileOutputStream("/storage/emulated/0/1/sina/sdadas.txt"));
                    out.writeObject(data);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "ssss: 序列化的时候失败" + e.toString());
                }
            }
        });

//        Log.d(TAG, "onCreate: Activity 开始准备绑定");
//        if(bindService(new Intent(this,process1Service.class),mConnection, Context.BIND_AUTO_CREATE))
//            Log.d(TAG, "onCreate: 绑定成功");

        findViewById(R.id.process1UnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    appleManager.unRegisterListener(mInewDataListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.process1ToContentProviderActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnMoreProcessActivity.this, BinderPoolActivity.class));
            }
        });



    }

    IBookManager mIBookManager = null;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 准备返回操作的Binder");
            mIBookManager = BookManagerImpl.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBookManager = null;
        }
    };

    void ssss(){
        //序列化过程
        SerializableTest test = new SerializableTest("测试",100);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/storage/emulated/0/1/sina/ssss.txt"));
//            out.writeObject(test);
            out.writeBytes("wsadsafasfasdassasaa");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "ssss: 序列化的时候失败" + e.toString());
        }

        //反序列化过程
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("/storage/emulated/0/1/sina/ssss.txt"));
            SerializableTest t = (SerializableTest) in.readObject();
            Log.d(TAG, "ssss: 反序列化得到的对象 name = " + t.name + " s = " + t.sss);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "ssss: 反序列化的时候失败" + e.toString());
        }
        Log.d(TAG, "ssss: 序列完成");
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mIBookManager == null) return;
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mIBookManager = null;
            //这里重新绑定Service
            bindService(new Intent(LearnMoreProcessActivity.this,process1Service.class),
                    mConnection, Context.BIND_AUTO_CREATE);
        }
    };


    private InewDataListener mInewDataListener = new InewDataListener.Stub() {
        @Override
        public void DataUpdata(Apple apple) throws RemoteException {
            mHandler.obtainMessage(10,apple).sendToTarget();
        }
    };

    IAppleManager appleManager = null;

    void bindMyAppleService(){
        ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                appleManager = IAppleManager.Stub.asInterface(service);
                try {
                    List<Apple> list = appleManager.getAppleList();
                    Log.d(TAG, "onServiceConnected: query apple list ,list type is " + list.getClass().getCanonicalName());
                    for(Apple apple : list){
                        Log.d(TAG, "onServiceConnected: apple.color = " + apple.color + ",apple.size = " + apple.size);
                    }
                    Apple apple = new Apple(10,"绿色的苹果");

                    appleManager.addApple(apple);
                    list = appleManager.getAppleList();
                    Log.d(TAG, "onServiceConnected: 重新接收");
                    for(Apple apple1 : list){
                        Log.d(TAG, "onServiceConnected: apple.color = " + apple1.color + ",apple.size = " + apple1.size);
                    }
                    appleManager.registerListener(mInewDataListener);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };
        bindService(new Intent(this,AppleManagerService.class),mConnection,Context.BIND_AUTO_CREATE);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Apple apple = (Apple) msg.obj;
                    Log.d(TAG, "handleMessage: receive The new Data " + apple.color);
            }
            super.handleMessage(msg);
        }
    };

}
