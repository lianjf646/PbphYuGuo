package com.pbph.yuguo.util;

/**
 * Created by Administrator on 2018/2/9.
 * 支持背压，当数据量多时，能够防止报错
 */

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.pbph.yuguo.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class RxBusF {

    public static RxBusF getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static RxBusF INSTANCE = new RxBusF();
    }

    //
    private final FlowableProcessor<Object> mBus;
    private final Map<String, CompositeDisposable> map = new HashMap();

    private RxBusF() {
        mBus = PublishProcessor.create().toSerialized();
    }

    //
    public static void post0(@NonNull Object obj) {
        getInstance().post(obj);
    }

    public void post(@NonNull Object obj) {
        mBus.onNext(obj);
    }


    public static <T> void register0(BaseActivity key, Class<T> clz, Consumer<T> consumer) {

        Disposable disposable = register0(clz, consumer);

        addDisposable0(key.getClass().getName() + clz.getName(), disposable);
    }

    public static <T> void register0(Fragment key, Class<T> clz, Consumer<T> consumer) {

        Disposable disposable = register0(clz, consumer);

        addDisposable0(key.getClass().getName() + clz.getName(), disposable);
    }


    public static <T> Disposable register0(Class<T> clz, Consumer<T> consumer) {
        return register0(clz).subscribe(consumer);
    }

    public static <T> Flowable<T> register0(Class<T> clz) {
        return getInstance().register(clz);
    }

    public <T> Flowable<T> register(Class<T> tClass) {
        return register().ofType(tClass);
    }

    public Flowable<Object> register() {
        mBus.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread());
        return mBus;
    }

    //
    public boolean hasObservers() {
        return mBus.hasSubscribers();
    }

    public void unregisterAll() {
        //会将所有由mBus生成的Observable都置completed状态,后续的所有消息都收不到了
        mBus.onComplete();
    }

    //
    public static void addDisposable0(String key, Disposable value) {
        getInstance().addDisposable(key, value);
    }

    public void addDisposable(String key, Disposable value) {
        CompositeDisposable cd = getInstance().map.get(key);
        if (cd == null) {
            cd = new CompositeDisposable();
            getInstance().map.put(key, cd);
        }
        cd.add(value);
    }


    public static <T> void removeDisposable0(BaseActivity key, Class<T> clz) {
        removeDisposable0(key.getClass().getName() + clz.getName());
    }

    public static <T> void removeDisposable0(Fragment key, Class<T> clz) {
        removeDisposable0(key.getClass().getName() + clz.getName());
    }


    public static void removeDisposable0(String key) {
        getInstance().removeDisposable(key);
    }

    public void removeDisposable(String key) {
        CompositeDisposable cd = getInstance().map.get(key);
        if (cd == null) return;
        cd.clear();
    }

}