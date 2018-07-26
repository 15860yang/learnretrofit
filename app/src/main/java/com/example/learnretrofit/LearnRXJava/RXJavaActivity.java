package com.example.learnretrofit.LearnRXJava;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnretrofit.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RXJavaActivity extends AppCompatActivity {

    private String TAG = "RXJavaActivity";
    private Observable<String> mObservable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

//        CreatObservable();
//        CreatObserver();
//        subscribOtherWays();
        //订阅
//        mObservable.subscribe(mObserver);
//        mObservable.subscribe();

//        CreateFuhao();

//        createBianhuanFuhao();

        zuheCaoZuoFu();
    }


    //创建被观察者
    void CreatObservable() {
        //create方式创建
        mObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //执行一些其他操作
                //.............
                //执行完毕，触发回调，通知观察者
                Log.d(TAG, "subscribe: 我要发送第一条消息啦");
                emitter.onNext("这是第一条消息");
                Log.d(TAG, "subscribe: 我要发送第二条消息啦");
                emitter.onNext("我来发射数据");
            }
        });
        //just方式创建
        mObservable = Observable.just("我是just方式创建的被观察者");
        //formLiterable方式
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("我是formIterable方式创建的第  " + i + "   条消息");
        }
        mObservable = Observable.fromIterable((Iterable<String>) list);
        //defer方式创建
        mObservable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("我是defer方式创建的被观察者", "我也是defer方式创建的被观察者");
            }
        });
        //interval方式创建
        Observable<Long> o = Observable.interval(4, TimeUnit.SECONDS);
//        o.subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "interval方式创建 onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.d(TAG, "interval方式创建 onNext: 他给我发的数字是 " + aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "interval方式创建 onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "interval方式创建 onComplete: ");
//            }
//        });
        //range方式创建
        Observable<Integer> observable = Observable.range(1, 5);
//        observable.subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, "onNext: 这次他发送的数字是 " + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        });
        //timer方式
        //Timer方式创建
        Observable<Long> observable1 = Observable.timer(2, TimeUnit.SECONDS);
//        observable1.subscribe(mLongObserver);
        //repeat方式创建
        Observable<Long> observable2 = Observable.timer(3, TimeUnit.SECONDS).repeat();
        observable2.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Long integer) {
                Log.d(TAG, "onNext: "  + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
    void CreatObserver(){
        //创建观察者
        Observer<String> mObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: 我是张三,他给我发的消息是 message  = " +s );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        Observer<String> mObserver11 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: 我是王五他给我发的消息是 message  = " +s );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observer<Long> mLongObserver = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: "+aLong);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }
    void subscribOtherWays(){
        //subscribe()表示观察者不对被观察者发送的事件作出任何响应（但被观察者还是可以继续发送事件）
        Observable<String> observable = Observable.just("我是消息1","我是消息2","我是消息3","我是消息4");
//        observable.subscribe(new Observer<String>() {
//            //这是拦截器
//            private Disposable mDisposable;
//            private int cnt = 0;
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: 拦截器赋值");
//                mDisposable = d;
//            }
//
//            @Override
//            public void onNext(String s) {
//                cnt++;
//                Log.d(TAG, "onNext: 我收到了第 "+ cnt +" 条消息 == "+s);
//                if(cnt  >  2){
//                    Log.d(TAG, "onNext: 好了，我已经中断连接了");
//                    mDisposable.dispose();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        };
        Consumer<Throwable> consumer1 = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "accept: Error");
            }
        };
        Action action = new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG, "run: complete");
            }
        };
        Consumer consumer2 = new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.d(TAG, "accept: Subscriber");
            }
        };
        observable.subscribe(consumer,consumer1,action,consumer2 );
    }
    //操作符入门--创建
    void CreateFuhao(){
        // 一，create操作符
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
        Observable<Integer> integerObservable;
        integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                }
                emitter.onComplete();
            }
        });
        //just操作符
        integerObservable = Observable.just(1,2,3,4,5);
        //formArray
        Integer[] list = new Integer[]{1,2,3,4};
        integerObservable = Observable.fromArray(list);
        //formIterable
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);list1.add(2);list1.add(3);list1.add(4);list1.add(5);
        integerObservable = Observable.fromIterable(list1);
        //延迟创建操作符defer
        integerObservable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(Calendar.getInstance().get(Calendar.SECOND));
            }
        });
        //延迟操作符timer
        Observable<Long> longObservable = Observable.timer(2,TimeUnit.SECONDS);
        //延迟操作符interval
        longObservable = Observable.interval(4,3,TimeUnit.SECONDS);
        //延迟操作符intervalRange
        longObservable = Observable.intervalRange(1,5,3,2,TimeUnit.SECONDS);
        //延迟操作符range
        longObservable = Observable.rangeLong(1,10);

        integerObservable.subscribe(observer);



    }
    //变换操作符
    void createBianhuanFuhao(){
        //Map操作符
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//            }
//        }).map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return "使用map操作符将Integer类型的事件 "+integer +" 变成String类型";
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d(TAG, "accept: 消息是String类型的 == " + s );
//            }
//        });

        //FlatMap操作符
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);emitter.onNext(2);emitter.onNext(3);}
//        }).flatMap(new Function<Integer, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                List<String> list = new ArrayList<>();
//                Log.d(TAG, "apply: hello "+ integer + " --" + list.hashCode());
//                list.add("我是事件 " + integer);
//                return Observable.fromIterable(list);
//            }
//        }).subscribe(new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d){Log.d(TAG, "onSubscribe: 调用了");}
//            @Override
//            public void onNext(Object o) {Log.d(TAG, "accept: == " + (String) o) ;}
//            @Override
//            public void onError(Throwable e) {}
//            @Override
//            public void onComplete() {}
//        });

        //Buffer变换操作符
        Observable.just(1,2,3,4,5,6,7)
                .buffer(4,4)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d(TAG, "accept: " + integers.toString());
                    }
                });
    }
    //组合，合并操作符
    void zuheCaoZuoFu(){
        //组合操作符: concat，concatArray
//        Observable.concatArray(Observable.just(1,2),
//                Observable.just(3,4),
//                Observable.just(5,6))
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "accept: === " + integer);
//                    }
//                });
        //组合操作符：merge ， mergeArray
//        Observable.mergeArray(Observable.intervalRange(1,3,1,4,TimeUnit.SECONDS),
//                Observable.intervalRange(4,3,1,2,TimeUnit.SECONDS),
//                Observable.intervalRange(7,3,1,2,TimeUnit.SECONDS))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long integer) throws Exception {
//                        Log.d(TAG, "accept: === " + integer);
//                    }
//                });
        //操作符：concatArrayDelayError ， mergeArrayDelayError
//        Observable.mergeArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                        emitter.onNext(7);
//                        emitter.onNext(8);
//                        emitter.onError(new NullPointerException());
//                        emitter.onNext(9);
//                        emitter.onComplete();
//                    }
//                }),Observable.just(1,2,3))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: ");
//                    }
//
//                    @Override
//                    public void onNext(Integer number) {
//                        Log.d(TAG, "onNext: " + number);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
//                    }
//                });
        //操作符：Zip
//        Observable<Integer> o = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "被观察者1发送了事件1");
//                emitter.onNext(1);
//                emitter.onComplete();
//                // 为了方便展示效果，所以在发送事件后加入2s的延迟 Thread.sleep(1000);
//                Log.d(TAG, "被观察者1发送了事件2");
//                emitter.onNext(2);
//
//                Log.d(TAG, "被观察者1发送了事件3");
//                emitter.onNext(3);
//
//            }
//        });
//        Observable<String> o1 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.d(TAG, "被观察者2发送了事件A");emitter.onNext("A");
//
//                Log.d(TAG, "被观察者2发送了事件B");emitter.onNext("B");
//
//                Log.d(TAG, "被观察者2发送了事件C");emitter.onNext("C");
//
//                Thread.sleep(1000);
//                Log.d(TAG, "被观察者2发送了事件D");emitter.onNext("D");
//
//                emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.newThread());
//        Observable.zip(o, o1, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return  integer + s;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onNext: "+ s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        });
        //操作符combineLatest
//        Observable.combineLatest(Observable.just(1L, 2L, 3L)
//                , Observable.intervalRange(7, 3, 2, 2, TimeUnit.SECONDS),
//                new BiFunction<Long, Long, String>() {
//                    @Override
//                    public String apply(Long aLong, Long aLong2) throws Exception {
//                        return aLong + " + " + aLong2 +" = " +(aLong + aLong2);
//                    }
//                }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d(TAG, "accept: "+s);
//            }
//        });
//        //操作符reduce
//        Observable.just(1,2,3,2).reduce(new BiFunction<Integer, Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer, Integer integer2) throws Exception {
//                return  integer + integer2;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept: " + integer);
//            }
//        });
        //操作符collect
//        Observable.just(1,2,3).collect(new Callable<ArrayList<Integer>>() {
//            @Override
//            public ArrayList<Integer> call() throws Exception {
//                return new ArrayList<>();
//            }
//        }, new BiConsumer<ArrayList<Integer>, Integer>() {
//            @Override
//            public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
//                integers.add(integer);
//            }
//        }).subscribe(new Consumer<ArrayList<Integer>>() {
//            @Override
//            public void accept(ArrayList<Integer> integers) throws Exception {
//                Log.d(TAG, "accept: " + integers.toString());
//            }
//        });
        //操作符startWith（） / startWithArray（）
//        Observable.just(1,2,3)
//                .startWith(4)
//                .startWithArray(5,6)
//                .startWith(Observable.just(7,8))
//                .subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept: "+ integer);
//            }
//        });
        //操作符count
//        Observable.just(1,2,3,4).count().subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                Log.d(TAG, "accept: " + aLong);
//            }
//        });
        //线程调度subscribeOn ， observeOn
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "subscribe: 被观察者的线程 == " + Thread.currentThread().getName());
//                emitter.onNext(1);
//            }
//        }).subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "onNext: " + integer);
//                        Log.d(TAG, "onNext: 观察者的线程 == " + Thread.currentThread().getName());
//                    }
//                });
        //延迟：delay
//        Observable.just(1).delay(1,TimeUnit.SECONDS).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept: " + integer);
//            }
//        });

        //do操作符
//        Observable.just(1).doAfterNext(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        }).doOnEach(new Consumer<Notification<Integer>>() {
//            @Override
//            public void accept(Notification<Integer> integerNotification) throws Exception {
//
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        });
        //onErrorReturn
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onError(new Exception("这是错误"));
//                emitter.onNext(2);
//                emitter.onComplete();
//            }
//        }).onExceptionResumeNext(new Observable<Integer>() {
//            @Override
//            protected void subscribeActual(Observer<? super Integer> observer) {
//                observer.onNext(1);
//                observer.onNext(2);
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, "onNext: " + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        });
        //retry操作符
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onError(new Exception("这是错误"));
//                emitter.onNext(2);
//                emitter.onComplete();
//            }
//        }).retry(new BiPredicate<Integer, Throwable>() {
//            @Override
//            public boolean test(Integer integer, Throwable throwable) throws Exception {
//                if(integer < 5){
//                    return true;//返回true表示重新尝试
//                }
//                return false;//返回false表示不再尝试
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept: " + integer);
//            }
//        });

        //retryWhen操作符
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(2);
                emitter.onError(new Exception("这是错误"));
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {

                //当发生错误的时候，本来错误是从被观察者的subscribe方法传递到观察者的onError方法，
                // 但是到这里却被拦截，并将错误交给这个throwableObservable，然后调用FlatMap方法，
                //FlatMap方法是用来拦截事件并处理，处理完成还是要走到观察者的onError方法的，那么我们在这里通过FlatMap
                // 将错误包装成一个错误被观察者事件，然后发送，这样，因为我们发送的还是一个错误事件，所以onError还是被调用，
                //当然我们也可以在这里返回一个正常的被观察者
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        return Observable.error(new Throwable("retryWhen终止啦"));
                        //返回这种格式，代表回调我们观察者的onError方法
//                        return Observable.just(1);
                        //返回这种格式，代表原始的被观察者重新发送消息
                    }
                });
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });


    }
}
