package com.pbph.yuguo.http;
import android.util.Log;

import com.pbph.yuguo.BuildConfig;
import com.pbph.yuguo.constant.ConstantData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class HttpClient {
    //    http://123.57.151.98:9980/api/User/ImageValidateCode/15590858773.jpg
//    final static String HOST = "http://123.57.151.98:9980/api/";
    //    http://123.57.151.98:9980/api/Task/GetTaskNoticeSignConfig
//    final static String HOST = "http://123.56.251.83:96/api/values/";
    private static String token = "";

    public static void setToken(String token) {
        HttpClient.token = token;
    }

    /**
     * 构建OkHttp
     */
    private static final class OKHttpHolder {

        //新建一个文件用来缓存网络请求
//        private static File cacheDirectory = new File(AppContext.getApplicationContext()
//                .getCacheDir().getAbsolutePath(), "HttpCache");

        private static final int TIME_OUT = 60;

        private static final OkHttpClient OK_HTTP_CLIENT = createOkHttpClientBuilder().build();

        private static final OkHttpClient.Builder createOkHttpClientBuilder() {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置连接超时
            builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            //设置从主机读信息超时
            builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            //设置写信息超时
            builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
            //设置缓存文件
            // .cache(new Cache(cacheDirectory, 10 * 1024 * 1024))
            if (BuildConfig.LOG) {
                builder.addInterceptor(new HttpLoggingInterceptor(message -> {
//                    if (BuildConfig.LOG)
                        Log.e("===>", message);
                }).setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            return builder;
        }

    }

    // 构建全局Retrofit客户端
    private static final class RetrofitHolder {

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder().baseUrl
                (ConstantData.HOST).client(OKHttpHolder.OK_HTTP_CLIENT)
                //如果网络访问返回的字符串，而不是json数据格式，可以使用ScalarsConverterFactory转换器
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //为了支持rxjava,需要添加下面这个把 Retrofit 转成RxJava可用的适配类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    /**
     * HttpService
     */
    private static final class HttpServiceHolder {
        private static final HttpService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create
                (HttpService.class);
    }

    public static HttpService getHttpService() {

        return HttpServiceHolder.REST_SERVICE;
    }

}

