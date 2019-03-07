package com.drong.common;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.picasso.Picasso;
import okhttp3.*;
import okhttp3.MultipartBody.Builder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OKHttpUtil {
    private static OKHttpUtil mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;
    private Handler mHandler;

    private OKHttpUtil() {
        mHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = mHttpClient.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        mGson = new Gson();
        mHandler = new Handler();
    }

    public static OKHttpUtil getInstance() {
        if (null == mInstance) {
            synchronized (OKHttpUtil.class) {
                if (null == mInstance) {
                    mInstance = new OKHttpUtil();
                }
            }
        }
        return mInstance;
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types
                .canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public static void loadImg(Context context, String url, ImageView imageView, int defaultImg) {
        Picasso.with(context).load(url).placeholder(defaultImg).into(imageView);
    }

    /**
     *  get请求
     * @param url
     * @param callback
     */
    public void get(String url, BaseCallback callback) {
        // 获取request对象
        Request request = buildRequest(url, HttpMethodType.GET, null);
        // 开始请求
        doRequest(request, callback);
    }

    /**
     *  post 请求
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, String> params,
                     BaseCallback callback) {
        // 获取request对象
        Request request = buildRequest(url, HttpMethodType.POST, params);
        // 开始请求
        doRequest(request, callback);
    }

    /**
     * 上次文件
     * @param url
     * @param fileByte
     * @param params
     * @param baseCallback
     */
    public void uploadFile(String url, byte[] fileByte, Map<String, String> params, BaseCallback baseCallback) {
        Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        builder.addFormDataPart("file", "test.jpg",
                RequestBody.create(MediaType.parse("multipart/form-data"), fileByte));

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        // 开始请求
        doRequest(request, baseCallback);
    }


    /**
     * 构建request对象
     * @param url
     * @param methodType
     * @param params
     * @return
     */
    private Request buildRequest(String url, HttpMethodType methodType,
                                 Map<String, String> params) {
        Request.Builder builder = new Request.Builder().url(url);

        if (methodType == HttpMethodType.POST) {// post请求
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {// get请求
            builder.get();
        } else if (methodType == HttpMethodType.UPLOAD_FILE) {// 上传文件

        }
        return builder.build();
    }


    /**
     * 通过params获取requestbody
     * @param params
     * @return
     */
    private RequestBody builderFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }


    /**
     * 添加请求到请求队列进行请求
     * @param request
     * @param baseCallback
     */
    private void doRequest(final Request request,
                           final BaseCallback baseCallback) {
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackFailure(baseCallback, e);
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    if (baseCallback.mType == String.class) {
                        /* baseCallback.onSuccess(response,resultStr); */
                        callbackSuccess(baseCallback, resultStr);
                    } else {
                        try {
                            Object obj = mGson.fromJson(resultStr,
                                    baseCallback.mType);
                            // Object obj = resultStr;
                            /* baseCallback.onSuccess(response,obj); */
                            callbackSuccess(baseCallback, obj);
                        } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                            /* baseCallback.onError(response,response.code(),e); */
                            callbackFailure(baseCallback, e);
                        }
                    }

                } else {
                    callbackFailure(baseCallback, new Exception("errorcode="
                            + response.code()));
                    /* baseCallback.onError(response,response.code(),null); */
                }

            }
        });
    }

    /**
     * 连接成功回调
     * @param callback
     * @param obj
     */
    private void callbackSuccess(final BaseCallback callback, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(obj);
            }
        });
    }

    /**
     * 连接失败回调
     * @param callback
     * @param e
     */
    private void callbackFailure(final BaseCallback callback, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {// mHandler在哪个线程new的，run就在那个线程运行
                callback.onFailure(e);
            }
        });
    }

    // 枚举，区分get与post
    enum HttpMethodType {
        GET, POST, UPLOAD_FILE
    }

    // 回调
    public static abstract class BaseCallback<T> {
        Type mType = getSuperclassTypeParameter(getClass());

        // // 加载网络数据成功前，进度条等显示
        // public abstract void onBeforeRequest(Request request);
        // 请求成功时调用此方法
        // public abstract void onResponse(Response response);
        // 状态码大于200，小于300 时调用此方法
        public abstract void onSuccess(T t);

        // 请求失败时调用此方法
        public abstract void onFailure(Exception e);

        // 状态码400，404，403，500等时调用此方法
        // public abstract void onError(Response response, int code, Exception
        // e);

        // Token 验证失败。状态码401,402,403 等时调用此方法
        // public abstract void onTokenError(Response response, int code);

    }

}
