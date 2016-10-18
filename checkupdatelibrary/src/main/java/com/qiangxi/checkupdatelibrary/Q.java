package com.qiangxi.checkupdatelibrary;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback;
import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback2;
import com.qiangxi.checkupdatelibrary.http.HttpRequest;

/**
 * Created by qiang_xi on 2016/10/6 13:07.
 * 检查更新
 */

public class Q {

    private Q() {
    }

    /**
     * 检查更新,,实体类中必须要有newAppVersionCode字段,不然检查不到更新
     *
     * @param requestMethod      请求方式:目前仅支持post或get
     * @param currentVersionCode 当前应用版本号
     * @param urlPath            请求地址
     * @param callback           请求回调
     */
    public static void checkUpdate(@NonNull String requestMethod, @NonNull int currentVersionCode, @NonNull String urlPath, @NonNull CheckUpdateCallback callback) {
        if (TextUtils.equals(requestMethod.toUpperCase(), "GET")) {
            HttpRequest.get(currentVersionCode, urlPath, callback);
        } else {
            HttpRequest.post(currentVersionCode, urlPath, callback);
        }
    }

    /**
     * 检查更新,实体类可以任意自定义,不需要有newAppVersionCode字段,所以扩展性更强,但是需要自己进行是否有更新的判断
     *
     * @param requestMethod 请求方式:目前仅支持post或get
     * @param urlPath       请求地址
     * @param callback      请求回调
     */
    public static void checkUpdate(@NonNull String requestMethod, @NonNull String urlPath, @NonNull CheckUpdateCallback2 callback) {
        if (TextUtils.equals(requestMethod.toUpperCase(), "GET")) {
            HttpRequest.get(urlPath, callback);
        } else {
            HttpRequest.post(urlPath, callback);
        }
    }
}
