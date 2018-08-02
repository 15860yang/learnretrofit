package com.example.learnretrofit.AIDLTest;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 * Created by 杨豪 on 2018/7/30.
 */

public abstract class BookManagerImpl extends Binder implements IBookManager {
    //运行在服务端
    private final static String TAG = "BookManagerImpl";
    //构造器是固定格式
    public BookManagerImpl(){
        this.attachInterface(this,DESCRIPTOR);
    }
    //这个方法是我们直接暴露给外部，去使用的
    public static IBookManager asInterface(IBinder obj){
        if(obj == null){
            return  null;
        }
        IInterface lin = obj.queryLocalInterface(DESCRIPTOR);
        if((lin != null) && lin instanceof IBookManager){
            Log.d(TAG, "asInterface: 准备处理binder对象，返回给同进程操作句柄");
            return (IBookManager) lin;
        }
        Log.d(TAG, "asInterface: 准备处理binder对象，返回给不同进程操作句柄");
        return new BookManagerImpl.Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            //根据这个常量值我们可以很明确的定位到需要调用哪个方法
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;

            case TRANSACTION_getBookList:
                //第一句话是固定格式
                data.enforceInterface(DESCRIPTOR);
                Log.d(TAG, "onTransact: 来到进程 二 执行getBookList方法");

                List<Book> result = this.getBookList();
                reply.writeNoException();
                //这里将我们得到的结果写进reply参数，然后相应的会在客户端读取到我们在这里写进去的数据
                reply.writeTypedList(result);
                Log.d(TAG, "onTransact: 准备从进程 一 中返回");
                return true;
            case TRANSACTION_addBook:
                //第一句话是固定格式
                data.enforceInterface(DESCRIPTOR);
                Book args;
                if( (0 != data.readInt()) ){
                    //0和1表示我们是否往data中写入了序列化的对象数据，比如说这里的对象就是Book类的对象
                    args = Book.CREATOR.createFromParcel(data);
                }else {
                    args = null;
                }
                //拿到具体对象之后，再根据我们自己实现的方法逻辑对参数进行操作，比如我们这里拿到了args这个Book的实
                // 例对象，我们为他执行运行在服务端的addBook方法
                this.addBook(args);
                reply.writeNoException();
                return true;
        }
        return super.onTransact(code,data,reply,flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    //内部代理类的实现
    private static class Proxy implements IBookManager{
        //运行在客户端
        private IBinder mRemote;

        public Proxy(IBinder remote) {
            //这里实例化一个存在于本进程的Binder
            Log.d(TAG, "Proxy: 这里实例化一个存在于本进程的Binder");
            mRemote = remote;
        }

        public String getInterfaceDescriptor(){
            return DESCRIPTOR;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            List<Book> result;
            try{
                Log.d(TAG, "getBookList: 在进程 一 的getBookList方法中准备去往进程 二 的getBookList方法");
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList,data,reply,0);
                //
                Log.d(TAG, "getBookList: 重新回到进程 一 ");
                reply.readException();
                result = reply.createTypedArrayList(Book.CREATOR);
            }finally {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try{
                //这里执行的主要是将数据写进参数，并调用mRemote.transact方法来发送远程请求
                data.writeInterfaceToken(DESCRIPTOR);
                if( book != null){
                    data.writeInt(1);
                    book.writeToParcel(data,0);
                }else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook,data,reply,0);
                reply.readException();
            }finally {
                reply.recycle();
                data.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
