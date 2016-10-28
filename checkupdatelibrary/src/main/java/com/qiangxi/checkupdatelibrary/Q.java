package com.qiangxi.checkupdatelibrary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback;
import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback2;
import com.qiangxi.checkupdatelibrary.http.HttpRequest;

import java.util.Map;

/**
 * Created by qiang_xi on 2016/10/6 13:07.
 * 检查更新
 */

public class Q {

    public static final String GET = "GET";//GEI请求
    public static final String POST = "POST";//POST请求

    private Q() {
    }

    /**
     * 检查更新,,实体类中必须要有newAppVersionCode字段,不然检查不到更新
     *
     * @param requestMethod      请求方式:目前仅支持post或get
     * @param currentVersionCode 当前应用版本号
     * @param urlPath            请求地址
     * @param params             请求参数,不需要传参时,置为null即可
     * @param callback           请求回调
     */
    public static void checkUpdate(@NonNull String requestMethod, @NonNull int currentVersionCode, @NonNull String urlPath, @Nullable Map<String, String> params, @NonNull CheckUpdateCallback callback) {
        if (TextUtils.equals(requestMethod.toUpperCase(), "GET")) {
            HttpRequest.get(currentVersionCode, urlPath, params, callback);
        } else {
            HttpRequest.post(currentVersionCode, urlPath, params, callback);
        }
    }

    /**
     * 检查更新,实体类可以任意自定义,不需要有newAppVersionCode字段,所以扩展性更强,但是需要自己进行是否有更新的判断
     *
     * @param requestMethod 请求方式:目前仅支持post或get
     * @param urlPath       请求地址
     * @param params        请求参数,不需要传参时,置为null即可
     * @param callback      请求回调
     */
    public static void checkUpdate(@NonNull String requestMethod, @NonNull String urlPath, @Nullable Map<String, String> params, @NonNull CheckUpdateCallback2 callback) {
        if (TextUtils.equals(requestMethod.toUpperCase(), "GET")) {
            HttpRequest.get(urlPath, params, callback);
        } else {
            HttpRequest.post(urlPath, params, callback);
        }
    }
}
