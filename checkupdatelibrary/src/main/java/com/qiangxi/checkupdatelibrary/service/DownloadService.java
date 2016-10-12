package com.qiangxi.checkupdatelibrary.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

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
    private RemoteViews remoteViews;
    private NotificationManager manager;
    private Notification notification;
    private static  final int notificationId = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.checkupdatelibrary_notification_downloading_layout);
        remoteViews.setImageViewResource(R.id.icon, iconResId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(null==intent){
            return START_NOT_STICKY;
        }
        String downloadUrl = intent.getStringExtra("downloadUrl");
        String filePath = intent.getStringExtra("filePath");
        String fileName = intent.getStringExtra("fileName");
        iconResId = intent.getIntExtra("iconResId", -1);
        boolean isShowProgress = intent.getBooleanExtra("isShowProgress", false);
        download(downloadUrl, filePath, fileName);
        if (isShowProgress) {
            //showNotification();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void download(String downloadUrl, String filePath, String fileName) {
        HttpRequest.download(downloadUrl, filePath, fileName, new DownloadCallback() {
            @Override
            public void onDownloadSuccess(File file) {
                ApplicationUtils.installApk(DownloadService.this, file);
            }

            @Override
            public void onProgress(long currentProgress, long totalProgress) {
//                remoteViews.setProgressBar(R.id.progress, (int) currentProgress, (int) totalProgress, false);
//                manager.notify(0, notification);// 显示通知
            }

            @Override
            public void onDownloadFailure(String failureMessage) {
                Log.e("tag", failureMessage);
            }
        });
    }

    private void showNotification() {
        if (iconResId == -1) {
            iconResId = R.drawable.icon_downloading;
        }
        Intent dataIntent = new Intent(this, DownloadService.class);
        dataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, dataIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        //若下载完毕,点击通知栏,则安装应用
//        remoteViews.setOnClickPendingIntent(R.id.rootLayout, pendingIntent);
//        //配置通知参数(采用兼容方式)
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(DownloadService.this);
//        builder.setAutoCancel(false).setShowWhen(false).setCustomContentView(remoteViews);
//        Notification notification = builder.build();// 获取一个Notification
//        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
//        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;// 发起Notification后，铃声和震动均只执行一次
//        manager.notify(0, notification);// 显示通知
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(DownloadService.this);
//        builder.setAutoCancel(false).setCustomContentView(remoteViews).setContentIntent(pendingIntent);
//        builder.setContentTitle("通知标题")// 通知标题
//                .setContentText("通知内容")// 通知内容
//                .setSmallIcon(iconResId)// 设置状态栏里面的图标（小图标）
//                .setAutoCancel(true);// 设置可以清除
//        notification = builder.build();// 获取一个Notification
//        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
//        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;// 发起Notification后，铃声和震动均只执行一次
//        manager.notify(notificationId, notification);// 显示通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_notification_type_0);
//        remoteViews.setTextViewText(R.id.title_tv, title);
//        remoteViews.setTextViewText(R.id.content_tv, content);
//        remoteViews.setTextViewText(R.id.time_tv, getTime());
//        remoteViews.setImageViewResource(R.id.icon_iv, R.drawable.logo);
//        remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());
        builder.setSmallIcon(iconResId);
        notification = builder.build();
        notification.contentView = remoteViews;
        manager.notify(notificationId, notification);
    }
}
