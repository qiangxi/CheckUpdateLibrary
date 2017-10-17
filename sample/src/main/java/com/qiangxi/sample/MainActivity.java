package com.qiangxi.sample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiangxi.checkupdatelibrary.CheckUpdateOption;
import com.qiangxi.checkupdatelibrary.Q;
import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.callback.StringCallback;
import com.qiangxi.checkupdatelibrary.utils.L;

import java.io.File;

public class MainActivity extends AppCompatActivity implements StringCallback, DownloadCallback {
    private static final String API = "http://v.juhe.cn/historyWeather/province";
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib";
    private static final String NAME = "/demo.apk";
    private static final String DOWNLOAD = "http://f4.market.xiaomi.com/download/AppStore/09a8d44bf3f4925837eae2b699d36ce8bcd4169c9/com.tencent.mobileqq.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkUpdate(View view) {
//        Map<String, String> params = new HashMap<>();
//        params.put("key", "cacfae015af2ff98ce235bf4c5a4c662");
        //get请求
//        Q.get(API, params).callback(this).execute();
        //post请求
//        Q.post(API, params).callback(this).execute();
        //download
//        Q.download(DOWNLOAD, PATH, NAME).execute();
        Q.show(this, new CheckUpdateOption.Builder().build());
    }

    @Override
    public void checkUpdateStart() {
        L.e("checkUpdateStart...+Thread=" + Thread.currentThread());
    }

    @Override
    public void checkUpdateFailure(Throwable t) {
        L.e("checkUpdateFailure...+t=" + t.toString() + ",thread=" + Thread.currentThread());
    }

    @Override
    public void checkUpdateFinish() {
        L.e("checkUpdateFinish...+Thread=" + Thread.currentThread());
    }

    @Override
    public void checkUpdateSuccess(String json) {
        L.e("checkUpdateSuccess...+json=" + json + ",thread=" + Thread.currentThread());
    }

    @Override
    public void downloadProgress(long currentLength, long totalLength) {
        L.e("downloadProgress...+currentLength=" + currentLength + ",totalLength=" + totalLength + ",thread=" + Thread.currentThread());
    }

    @Override
    public void downloadSuccess(File apk) {
        L.e("downloadSuccess...+apk.size=" + apk.length() + ",thread=" + Thread.currentThread());
    }
}
