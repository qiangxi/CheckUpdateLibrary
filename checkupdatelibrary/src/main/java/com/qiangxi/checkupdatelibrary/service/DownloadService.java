package com.qiangxi.checkupdatelibrary.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.qiangxi.checkupdatelibrary.R;
import com.qiangxi.checkupdatelibrary.utils.NotificationUtil;

import java.io.File;

/**
 * Created by qiang_xi on 2016/10/7 13:56.
 * 后台下载服务
 */

public class DownloadService extends BaseService {
    private int iconResId;
    private String appName;
    private Intent mIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_NOT_STICKY;
        }
        mIntent = intent;
        appName = intent.getStringExtra("appName");
        iconResId = intent.getIntExtra("iconResId", -1);
        if (iconResId == -1) {
            iconResId = R.drawable.icon_downloading;
        }
        download(intent.getStringExtra("downloadUrl"), intent.getStringExtra("filePath"), intent.getStringExtra("fileName"), true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void downloadFailure(String failureMessage) {
        NotificationUtil.showDownloadFailureNotification(this, mIntent, iconResId, appName, "下载失败,点击重新下载", true);
    }

    @Override
    public void downloadSuccess(File file) {
        NotificationUtil.showDownloadSuccessNotification(this, file, iconResId, appName, "下载完成,点击安装", false);
    }

    @Override
    public void downloading(int currentProgress, int totalProgress) {
        NotificationUtil.showDownloadingNotification(this, currentProgress, totalProgress, iconResId, appName, false);
    }
}
