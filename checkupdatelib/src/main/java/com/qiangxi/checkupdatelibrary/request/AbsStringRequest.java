package com.qiangxi.checkupdatelibrary.request;
/*
 * Copyright © qiangxi(任强强)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qiangxi.checkupdatelibrary.annotation.RequestType;
import com.qiangxi.checkupdatelibrary.callback.BaseCallback;
import com.qiangxi.checkupdatelibrary.callback.StringCallback;
import com.qiangxi.checkupdatelibrary.constants.Const;
import com.qiangxi.checkupdatelibrary.dispatcher.CallbackDispatcher;
import com.qiangxi.checkupdatelibrary.exception.CallbackException;
import com.qiangxi.checkupdatelibrary.utils.L;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qiangxi(任强强) on 2017/10/15.
 * get、post请求的基类
 */
abstract class AbsStringRequest implements IRequest {

    private String mUrl;//请求地址
    private Map<String, String> mParams;//附加参数
    private StringCallback mCallback;//请求回调

    AbsStringRequest(@NonNull String url) {
        mUrl = url;
    }

    AbsStringRequest(@NonNull String url, @Nullable Map<String, String> params) {
        mUrl = url;
        mParams = params;
    }

    AbsStringRequest(@NonNull String url, @Nullable Map<String, String> params, @Nullable BaseCallback callback) {
        mUrl = url;
        mParams = params;
        //检查callback类型
        if (callback != null && !(callback instanceof StringCallback))
            throw new CallbackException("get or post request must be use StringCallback");
        mCallback = (StringCallback) callback;
    }

    /**
     * 由子类复写，返回请求的方式，必须使用RequestType注解允许的值
     *
     * @return 请求方式，Const.GET or Const.POST
     */
    @RequestType
    abstract String requestMethod();

    @Override
    public void request() {
        CallbackDispatcher.dispatchRequestStart(mCallback);
        String requestMethod = requestMethod();
        OkHttpClient client = new OkHttpClient();
        //构建Request
        Request.Builder builder = new Request.Builder();
        //拼接参数
        addParams(requestMethod, builder);
        Request request = builder.build();
        //发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                L.e(e);
                CallbackDispatcher.dispatchRequestFailure(mCallback, e);
                CallbackDispatcher.dispatchRequestFinish(mCallback);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                L.e(response.toString());
                CallbackDispatcher.dispatchRequestSuccess(mCallback, response);
                CallbackDispatcher.dispatchRequestFinish(mCallback);
            }
        });
    }

    private void addParams(String requestMethod, Request.Builder builder) {
        if (mParams != null) {
            Set<Map.Entry<String, String>> entries = mParams.entrySet();
            if (Const.GET.equals(requestMethod)) {
                StringBuilder sb = new StringBuilder();
                sb.append(mUrl).append("?");
                for (Map.Entry<String, String> param : entries)
                    sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
                mUrl = sb.toString();
            } else if (Const.POST.equals(requestMethod)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                for (Map.Entry<String, String> param : entries)
                    formBodyBuilder.add(param.getKey(), param.getValue());
                builder.method(requestMethod, formBodyBuilder.build());
            }
        }
        L.e(mUrl);
        builder.url(mUrl);
    }
}
