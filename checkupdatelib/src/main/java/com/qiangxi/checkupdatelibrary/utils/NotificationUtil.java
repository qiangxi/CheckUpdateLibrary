package com.qiangxi.checkupdatelibrary.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

import static android.app.PendingIntent.getActivity;

/**
 * 作者 任强强 on 2016/10/18 11:36.
 */

public class NotificationUtil {
    private static final int notificationId = 0;

    private NotificationUtil() {
    }

    /**
     * 展示下载成功通知
     *
     * @param context               上下文
     * @param file                  下载的apk文件
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    public static void showDownloadSuccessNotification(Context context, File file, int notificationIconResId, String notificationTitle, String notificationContent, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        //当前设备系统版本在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName()+".provider.FileProvider", file);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false).setShowWhen(true).setSmallIcon(notificationIconResId).setContentTitle(notificationTitle).setContentText(notificationContent);
        PendingIntent pendingIntent = getActivity(context, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示实时下载进度通知
     *
     * @param context               上下文
     * @param currentProgress       当前进度
     * @param totalProgress         总进度
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    public static void showDownloadingNotification(Context context, int currentProgress, int totalProgress, int notificationIconResId, String notificationTitle, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false).setShowWhen(false).setSmallIcon(notificationIconResId).setContentTitle(notificationTitle)
                .setProgress(totalProgress, currentProgress, false);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }

    /**
     * 展示下载失败通知
     *
     * @param context               上下文
     * @param notificationContent   通知内容,比如:下载失败,点击重新下载
     * @param intent                该intent用来重新下载应用
     * @param notificationIconResId 通知图标资源id
     * @param notificationTitle     通知标题
     * @param isCanClear            通知是否可被清除
     */
    public static void showDownloadFailureNotification(Context context, Intent intent, int notificationIconResId, String notificationTitle, String notificationContent, boolean isCanClear) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false).setShowWhen(true).setSmallIcon(notificationIconResId)
                .setContentTitle(notificationTitle).setContentText(notificationContent);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();// 获取一个Notification
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = isCanClear ? Notification.FLAG_ONLY_ALERT_ONCE : Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(notificationId, notification);// 显示通知
    }
}
