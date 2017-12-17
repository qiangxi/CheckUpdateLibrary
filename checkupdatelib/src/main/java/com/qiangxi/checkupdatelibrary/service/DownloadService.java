package com.qiangxi.checkupdatelibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.qiangxi.checkupdatelibrary.CheckUpdateOption;
import com.qiangxi.checkupdatelibrary.Q;
import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.utils.AppUtil;
import com.qiangxi.checkupdatelibrary.utils.NotificationUtil;

import java.io.File;

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

/**
 * Created by qiangxi(任强强) on 2017/10/18.
 * 后台下载service
 */

public class DownloadService extends Service implements DownloadCallback {
    private CheckUpdateOption mOption;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;
        mOption = intent.getParcelableExtra("CheckUpdateOption");
        Q.download(mOption.getNewAppUrl(), mOption.getFilePath(), mOption.getFileName())
                .callback(this).execute();
        return START_STICKY;
    }

    @Override
    public void checkUpdateStart() {

    }

    @Override
    public void checkUpdateFailure(Throwable t) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("CheckUpdateOption", mOption);
        startService(intent);
        NotificationUtil.showDownloadFailureNotification(this, intent, mOption.getNotificationIconResId(),
                mOption.getNotificationTitle(), mOption.getNotificationFailureContent(), true);
    }

    @Override
    public void checkUpdateFinish() {

    }

    @Override
    public void downloadProgress(long currentLength, long totalLength) {
        NotificationUtil.showDownloadingNotification(this, (int) currentLength, (int) totalLength,
                mOption.getNotificationIconResId(), mOption.getNotificationTitle(), false);
    }

    @Override
    public void downloadSuccess(File apk) {
        AppUtil.installApk(this, apk);
        NotificationUtil.showDownloadSuccessNotification(this, apk, mOption.getNotificationIconResId(),
                mOption.getNotificationTitle(), mOption.getNotificationSuccessContent(), false);
    }
}
