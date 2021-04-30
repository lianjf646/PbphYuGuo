package com.pbph.yuguo.http;

/**
 * Created by Administrator on 2016/12/26.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
//    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);

    private ThreadUtil() {
    }

    public static ThreadUtil getInstance() {
        return ThreadUtilHolder.mInstance;
    }

    private static class ThreadUtilHolder {
        static ThreadUtil mInstance = new ThreadUtil();
    }

    public static void runMainThread(Runnable runnable) {
        (new Handler(Looper.getMainLooper())).post(runnable);
    }

    public void execute(Runnable runnable) {
        this.fixedThreadPool.execute(runnable);
    }

    public void shutdown() {
        this.fixedThreadPool.shutdown();
    }

    public void shutdownNow() {
        this.fixedThreadPool.shutdownNow();
    }
}

