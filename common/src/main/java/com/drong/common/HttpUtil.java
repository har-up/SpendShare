package com.drong.common;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author drong
 * @description http工具类
 */
public class HttpUtil{

    private static HttpUtil instance;
    private OkHttpClient okHttpClient;
    private Handler handler;

    @SuppressLint("NewApi")
    private HttpUtil() {

        okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 通过单例模式构造对象
     * @return AnyChatHttpUtil
     */
    private static HttpUtil getInstance() {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if(instance == null) {
                    instance = new HttpUtil();
                }
            }

        }
        return instance;
    }

    /**
     * 构造Get请求
     * @param url  请求的url
     * @param callback  结果回调的方法
     */
    @SuppressWarnings("rawtypes")
    private void getRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        call(callback, request);
    }

    /**
     * 构造Get请求
     * @param headers 请求头
     * @param url  请求的url
     * @param callback  结果回调的方法
     */
    @SuppressWarnings("rawtypes")
    private void getRequest(String url, List<ParamPair> headers, final ResultCallback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        if(headers != null && headers.size() > 0) {
            for(ParamPair header : headers) {
                builder.addHeader(header.key, header.value);
            }
        }
        final Request request = builder.build();
        call(callback,request);
    }

    /**
     * 构造post 请求
     * @param url 请求的url
     * @param callback 结果回调的方法
     * @param params 请求参数
     */
    @SuppressWarnings("rawtypes")
    private void postRequest(String url, final ResultCallback callback, List<ParamPair> params) {
        Request request = buildPostRequest(url, new Gson().toJson(params));
        call(callback, request);
    }

    /**
     * 构造post 请求
     * @param url 请求url
     * @param callback 结果回调
     * @param headers 请求头
     * @param params 请求参数
     */
    @SuppressWarnings("rawtypes")
    private void postRequest(String url, final ResultCallback callback, List<ParamPair> headers, List<ParamPair> params) {
        Request request = buildPostRequest(url,headers,new Gson().toJson(params));
        call(callback,request);
    }
    /**
     * 处理请求结果的回调
     * @param callback
     * @param request
     */
    @SuppressWarnings("rawtypes")
    private void call(final ResultCallback callback, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dispatchFailEvent(callback,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    dispatchSuccessEvent(callback, str);

                } catch (final Exception e) {
                    dispatchFailEvent(callback, e);
                }
            }
        });
    }

    /**
     * 发送失败的回调
     * @param callback
     * @param e
     */
    @SuppressWarnings("rawtypes")
    private void dispatchFailEvent(final ResultCallback callback, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    /**
     * 发送成功的调
     * @param callback
     * @param obj
     */
    @SuppressWarnings("rawtypes")
    private void dispatchSuccessEvent(final ResultCallback callback, final Object obj) {
        handler.post(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    /**
     * 构造post请求
     * @param url  请求url
     * @param json
     * @return 返回 Request
     */
    private Request buildPostRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return new Request.Builder().url(url).post(requestBody).build();
    }


    /**
     *  构造post请求
     * @param url
     * @param headers
     * @param json
     * @return
     */
    private Request buildPostRequest(String url,List<ParamPair> headers,String json) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder builder = new Request.Builder();
        if(headers != null && headers.size() > 0) {
            for(ParamPair header : headers) {
                builder.addHeader(header.key, header.value);
            }
        }
        return builder.post(requestBody).build();
    }


    /**
     * get请求
     * @param url  请求url
     * @param callback  请求回调
     */
    @SuppressWarnings("rawtypes")
    public static void get(String url, ResultCallback callback) {
        getInstance().getRequest(url, callback);
    }

    /**
     * get请求
     * @param url 请求url
     * @param headers 请求头
     * @param callback 请求回调
     */
    @SuppressWarnings("rawtypes")
    public static void get(String url,List<ParamPair> headers, ResultCallback callback) {
        getInstance().getRequest(url, headers, callback);
    }



    /**
     * post请求
     * @param url       请求url
     * @param callback  请求回调
     * @param params    请求参数
     */
    @SuppressWarnings("rawtypes")
    public static void post(String url, final ResultCallback callback, List<ParamPair> params) {
        getInstance().postRequest(url, callback, params);
    }

    @SuppressWarnings("rawtypes")
    public static void post(String url, final ResultCallback callback, List<ParamPair> headers,List<ParamPair> params) {
        getInstance().postRequest(url, callback, headers,params);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        /**
         * 请求成功回调
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class ParamPair {

        public String key;//请求的参数
        public String value;//参数的值

        public ParamPair(String key, String value) {
            this.key = key;
            this.value = value;
        }


    }
}
