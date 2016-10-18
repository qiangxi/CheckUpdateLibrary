package com.qiangxi.checkupdatelibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.http.HttpRequest;
import com.qiangxi.checkupdatelibrary.utils.ApplicationUtil;

import java.io.File;

/**
 * 作者 qiang_xi on 2016/10/18 11:21.
 * 扩展用Service(一般用在非强制更新的情况下,然后使用自定义的Dialog)
 */

public abstract class BaseService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 进行下载
     *
     * @param downloadUrl 文件下载地址
     */
    public void download(String downloadUrl) {
        download(downloadUrl, Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + "", true);
    }

    /**
     * 进行下载
     *
     * @param downloadUrl 文件下载地址
     * @param autoInstall 下载完成之后是否自动安装
     */
    public void download(String downloadUrl, final boolean autoInstall) {
        download(downloadUrl, Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + "", autoInstall);
    }

    /**
     * 进行下载
     *
     * @param downloadUrl 文件下载地址
     * @param filePath    文件存储路径
     * @param fileName    文件名
     * @param autoInstall 下载完成之后是否自动安装
     */
    public void download(String downloadUrl, String filePath, String fileName, final boolean autoInstall) {
        HttpRequest.download(downloadUrl, filePath, fileName, new DownloadCallback() {
            @Override
            public void onDownloadSuccess(File file) {
                downloadSuccess(file);
                if (autoInstall) {
                    ApplicationUtil.installApk(BaseService.this, file);
                }
            }

            @Override
            public void onProgress(long currentProgress, long totalProgress) {
                downloading((int) currentProgress, (int) totalProgress);
            }

            @Override
            public void onDownloadFailure(String failureMessage) {
                //下载失败,弹出下载失败通知,点击该通知,重新进行下载
                downloadFailure(failureMessage);
            }
        });
    }

    /**
     * 实时下载进度回调
     *
     * @param currentProgress 当前下载进度
     * @param totalProgress   总进度
     */
    public abstract void downloading(int currentProgress, int totalProgress);

    /**
     * 下载完成回调
     *
     * @param file 下载完成的apk文件
     */
    public abstract void downloadSuccess(File file);

    /**
     * 下载失败
     *
     * @param failureMessage 失败信息
     */
    public abstract void downloadFailure(String failureMessage);
}
