package com.qiangxi.checkupdatelibrary;

import android.content.Context;

import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qiang_xi on 2016/10/6 13:07.
 */

public class Q {

    private Q() {
    }

    public static void checkUpdate(Context context, String urlPath, CheckUpdateCallback<?> callback) {
        URL url = null;
        try {
            url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
