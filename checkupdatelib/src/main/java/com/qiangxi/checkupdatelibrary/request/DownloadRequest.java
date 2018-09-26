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

import com.qiangxi.checkupdatelibrary.callback.BaseCallback;
import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.dispatcher.CallbackDispatcher;
import com.qiangxi.checkupdatelibrary.exception.CallbackException;
import com.qiangxi.checkupdatelibrary.exception.DownloadException;
import com.qiangxi.checkupdatelibrary.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by qiangxi(任强强) on 2017/10/15.
 * download请求，下载的真正实现类
 */
public class DownloadRequest implements IRequest {
    private String mUrl;//下载地址
    private File mApk;//apk文件
    private DownloadCallback mCallback;//下载过程的回调
    private long mLastUpdateTime;

    public DownloadRequest(String url, File file, BaseCallback callback) {
        mUrl = url;
        mApk = file;
        //检查callback类型
        if (callback != null && !(callback instanceof DownloadCallback)) {
            throw new CallbackException("DownloadRequest must be use DownloadCallback");
        }
        mCallback = (DownloadCallback) callback;
    }

    @Override
    public void request() {
        CallbackDispatcher.dispatchRequestStart(mCallback);
        OkHttpClient client = new OkHttpClient();
        //构建Request
        Request request = new Request.Builder().url(mUrl).build();
        //发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                L.e(e);
                CallbackDispatcher.dispatchRequestFailure(mCallback, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                L.e(response.toString());
                final ResponseBody body = response.body();
                if (body == null) {
                    CallbackDispatcher.dispatchRequestFailure(mCallback, new DownloadException("body==null"));
                    return;
                }
                if (writeFileFromBody(mApk, body, mCallback)) {
                    CallbackDispatcher.dispatchDownloadSuccess(mCallback, mApk);
                    CallbackDispatcher.dispatchRequestFinish(mCallback);
                } else {
                    CallbackDispatcher.dispatchRequestFailure(mCallback, new DownloadException("writeFileFromBody has occur exception"));
                }
            }
        });
    }

    /**
     * 从响应体中获取InputStream并写入文件，在写入过程中获取下载进度并回调
     *
     * @param file apk 文件
     * @param body 响应体
     * @throws IOException io过程中的异常
     */
    private boolean writeFileFromBody(File file, ResponseBody body, DownloadCallback callback) throws IOException {
        final InputStream is = body.byteStream();
        final long totalLength = body.contentLength();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            final byte data[] = new byte[2048];
            int len;
            long current = 0;
            while ((len = is.read(data)) != -1) {
                fos.write(data, 0, len);
                current += len;
                //1s回调一次
                if (current < totalLength) {
                    if (System.currentTimeMillis() - mLastUpdateTime < 1000) {
                        continue;
                    }
                }
                mLastUpdateTime = System.currentTimeMillis();
                CallbackDispatcher.dispatchDownloading(callback, current, totalLength);
            }
            fos.flush();
            return current == totalLength;
        } catch (IOException e) {
            L.e(e);
            return false;
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
