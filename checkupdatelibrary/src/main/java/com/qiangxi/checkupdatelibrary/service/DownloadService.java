package com.qiangxi.checkupdatelibrary.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.qiangxi.checkupdatelibrary.R;
import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.http.HttpRequest;
import com.qiangxi.checkupdatelibrary.utils.ApplicationUtils;

import java.io.File;

/**
 * Created by qiang_xi on 2016/10/7 13:56.
 * 后台下载服务
 */

public class DownloadService extends Service {
    private int iconResId;
    private NotificationManager manager;
    private static final int notificationId = 0;
    private String downloadUrl;
    private String filePath;
    private String fileName;
    private String appName;
    private boolean isShowProgress;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_NOT_STICKY;
        }
        downloadUrl = intent.getStringExtra("downloadUrl");
        filePath = intent.getStringExtra("filePath");
        fileName = intent.getStringExtra("fileName");
        iconResId = intent.getIntExtra("iconResId", -1);
        appName = intent.getStringExtra("appName");
        if (iconResId == -1) {
            iconResId = R.drawable.icon_downloading;
        }
        isShowProgress = intent.getBooleanExtra("isShowProgress", false);
        download(downloadUrl, filePath, fileName);
        return super.onStartCommand(intent, flags, startId);
    }

    private void download(String downloadUrl, String filePath, String fileName) {
        HttpRequest.download(downloadUrl, filePath, fileName, new DownloadCallback() {
            @Override
            public void onDownloadSuccess(File file) {
                //在通知栏显示下载完成通知,点击通知,进行安装应用
                showDownloadSuccessNotification(file);
                //同时在下载完成后,自动跳转到软件安装界面
                ApplicationUtils.installApk(DownloadService.this, file);
            }

            @Override
            public void onProgress(long currentProgress, long totalProgress) {
                //实时在通知栏显示下载进度
                if (isShowProgress) {
                    showDownloadingNotification((int) currentProgress, (int) totalProgress);
                }
            }

            @Override
            public void onDownloadFailure(String failureMessage) {
                //下载失败,弹出下载失败通知,点击该通知,重新进行下载
                showDownloadFailureNotification(failureMessage);
            }
        });
    }

    /**
     * 展示下载成功通知,该通知不可被清除
     *
     * @param file 下载的apk文件
     */
    private void showDownloadSuccessNotification(File file) {
        //该intent用来安装软件
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        //使用兼容Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //配置通知内容
        builder.setOngoing(true).setShowWhen(true).setSmallIcon(iconResId).setContentTitle(appName).setContentText("下载完成,点击安装");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;// 发起Notification后，铃声和震动均只执行一次
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示下载中的通知,该通知不可被清除
     *
     * @param currentProgress 当前进度
     * @param totalProgress   总进度
     */
    private void showDownloadingNotification(int currentProgress, int totalProgress) {
        //使用兼容Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //配置通知内容
        builder.setOngoing(true).setShowWhen(false).setSmallIcon(iconResId).setContentTitle(appName)
                .setProgress(totalProgress, currentProgress, false);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;// 发起Notification后，铃声和震动均只执行一次
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示下载失败通知,该通知可被清除
     *
     * @param failureMessage 错误信息
     */
    private void showDownloadFailureNotification(String failureMessage) {
        //该intent用来重新下载应用
        Intent reDownloadIntent = new Intent(this, DownloadService.class);
        reDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reDownloadIntent.putExtra("downloadUrl", downloadUrl);
        reDownloadIntent.putExtra("filePath", filePath);
        reDownloadIntent.putExtra("fileName", fileName);
        reDownloadIntent.putExtra("iconResId", iconResId);
        reDownloadIntent.putExtra("isShowProgress", isShowProgress);
        reDownloadIntent.putExtra("appName", appName);
        //使用兼容Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //配置通知内容
        builder.setOngoing(false).setShowWhen(true).setSmallIcon(iconResId)
                .setContentTitle(appName).setContentText("下载失败");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, reDownloadIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;// 发起Notification后，铃声和震动均只执行一次
        manager.notify(notificationId, notification);// 显示通知
    }
}
