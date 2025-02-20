package com.my.judge_in_proxy;

import android.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkHttp {

    private static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    public static OkHttpClient client;

    private MyOkHttp() {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)
                .proxy(Proxy.NO_PROXY)//不使用代理,网络抓不到包
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.7", 8888)))
                .build();
    }

    //单例
    private static class HttpOkHttpInstance {
        private final static MyOkHttp INSTANCE = new MyOkHttp();
    }

    public static MyOkHttp getInstance() {
        return HttpOkHttpInstance.INSTANCE;
    }

    /**
     * GET请求
     *
     * @param url            请求地址
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的Class
     * @param <T>            返回结果类型
     */
    public <T> void requestGet(@NotNull String url, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                               @NotNull final Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    /**
     * POST请求
     *
     * @param url            请求地址
     * @param params         请求参数 Map<String,String> 格式
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的class
     * @param <T>            请求返回的类型
     */
    public <T> void requestPost(@NotNull String url, @NotNull ArrayMap<String, Object> params, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                                @NotNull final Class<T> clazz) {
        //获取到参数 params 类型为Map<String,String>,转化为发网络请求所需的JSON字符串格式
        JSONObject param_json = new JSONObject(params);
        String json = param_json.toString();

        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    /**
     * POST请求 raw text原生格式
     *
     * @param url            请求地址
     * @param params         请求参数 String 格式
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的class
     * @param <T>            请求返回的类型
     */
    public <T> void doPost(@NotNull String url, @NotNull String params, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                           @NotNull final Class<T> clazz) {
        final MediaType mediaType = MediaType.get("application/x-www-form-urlencoded;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }


    /**
     * POST提交Form-data表单
     *
     * @param url            请求地址
     * @param params         请求参数 Map<String,String> 类型
     * @param okHttpCallBack 请求回调
     * @param clazz          返回结果的Class
     * @param <T>            返回结果类型
     */
    public <T> void submitFormdata(@NotNull String url, @NotNull ArrayMap<String, String> params, @NotNull final OkHttpCallBack<T> okHttpCallBack, @NotNull final Class<T> clazz) {
        //把传进来的Map<String,String> 类型 prams 参数 拼接到RequestBody body里
        FormBody.Builder builder = new FormBody.Builder();
        for (ArrayMap.Entry<String, String> entity : params.entrySet()) {
            builder.add(entity.getKey(), entity.getValue());
        }
        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    private <T> void requestResult(String result, OkHttpCallBack<T> callBack, @NotNull Class<T> clazz) {
        if ("java.lang.String".equals(clazz.getName())) {
            callBack.requestSuccess((T) result);
        } else {
            Gson gson = new GsonBuilder().create();
            callBack.requestSuccess(gson.fromJson(result, clazz));
        }
    }


    public interface OkHttpCallBack<T> {
        /**
         * 请求成功回调
         *
         * @param t 回调返回成功结果输出
         */
        void requestSuccess(T t);

        /**
         * 请求失败回调
         *
         * @param message 回调返回失败消息
         */
        void requestFailure(String message);
    }

}
