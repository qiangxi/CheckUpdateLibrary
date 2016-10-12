package com.qiangxi.checkupdatelibrary;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback;
import com.qiangxi.checkupdatelibrary.http.HttpRequest;

/**
 * Created by qiang_xi on 2016/10/6 13:07.
 * 检查更新
 */

public class Q {

    private Q() {
    }

    /**
     * 检查更新
     *
     * @param requestMethod 请求方式:目前仅支持post或get,
     * @param urlPath       请求地址
     * @param callback      请求回调
     */
    public static void checkUpdate(@NonNull String requestMethod, @NonNull String urlPath, @NonNull CheckUpdateCallback callback) {
        String rm = requestMethod.toUpperCase();
        if (TextUtils.equals(rm, "POST")) {
            HttpRequest.post(urlPath, callback);
        } else if (TextUtils.equals(rm, "GET")) {
            // TODO: 2016/10/12  get请求以后提供,目前之后post请求
        } else {
            HttpRequest.post(urlPath, callback);
        }
    }
}
