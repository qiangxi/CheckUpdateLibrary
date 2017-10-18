package com.qiangxi.checkupdatelibrary;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.qiangxi.checkupdatelibrary.dialog.CheckUpdateDialog;
import com.qiangxi.checkupdatelibrary.imageloader.ImageLoader;
import com.qiangxi.checkupdatelibrary.task.DownloadTask;
import com.qiangxi.checkupdatelibrary.task.GetTask;
import com.qiangxi.checkupdatelibrary.task.PostTask;

import java.util.Map;

/**
 * Created by qiang_xi on 2016/10/6 13:07.
 * 检查更新入口类
 */

public class Q {
    public static String TAG_NAME = "checkUpdate";//log日志tag
    public static boolean isDebug = false;//是否为debug模式，调试时设为true

    /**
     * get请求
     *
     * @param url 请求地址
     * @return get请求包装类
     */
    public static GetTask get(String url) {
        return new GetTask(url);
    }

    /**
     * get请求
     *
     * @param url    请求地址
     * @param params 附加参数
     * @return get请求包装类
     */
    public static GetTask get(String url, Map<String, String> params) {
        return new GetTask(url, params);
    }

    /**
     * post请求
     *
     * @param url 请求地址
     * @return post请求包装类
     */
    public static PostTask post(String url) {
        return new PostTask(url);
    }

    /**
     * post请求
     *
     * @param url    请求地址
     * @param params 附加参数
     * @return post请求包装类
     */
    public static PostTask post(String url, Map<String, String> params) {
        return new PostTask(url, params);
    }

    /**
     * 下载任务
     *
     * @param url      下载地址
     * @param filePath apk存储路径
     * @param fileName apk存储名称
     * @return 下载任务包装类
     */
    public static DownloadTask download(String url, String filePath, String fileName) {
        if (TextUtils.isEmpty(filePath)) {
            filePath = Environment.getExternalStorageDirectory().getPath();
        }
        if (TextUtils.isEmpty(fileName)) {
            filePath = "checkUpdate.apk";
        }
        return new DownloadTask(url, filePath, fileName);
    }

    /**
     * 在使用lib自带的CheckUpdateDialog时，可使用该方法展示一个dialogFragment
     *
     * @param context must be FragmentActivity
     * @param option  the CheckUpdateOption,用于设置lib自带的CheckUpdateDialog的一些属性
     * @return CheckUpdateDialog
     */
    public static CheckUpdateDialog show(FragmentActivity context, CheckUpdateOption option) {
        FragmentManager manager = context.getSupportFragmentManager();
        CheckUpdateDialog dialog = new CheckUpdateDialog();
        dialog.applyOption(option);
        dialog.show(manager, "CheckUpdateDialog");
        return dialog;
    }

    /**
     * 在使用lib自带的CheckUpdateDialog时，可使用该方法展示一个dialogFragment
     *
     * @param context must be FragmentActivity
     * @param option  the CheckUpdateOption,用于设置lib自带的CheckUpdateDialog的一些属性
     * @param loader  从网络加载图片的imageLoader,从本地加载图片的话，可不用ImageLoader
     * @return CheckUpdateDialog
     */
    public static CheckUpdateDialog show(FragmentActivity context, CheckUpdateOption option, ImageLoader loader) {
        FragmentManager manager = context.getSupportFragmentManager();
        CheckUpdateDialog dialog = new CheckUpdateDialog();
        dialog.applyOption(option);
        dialog.setImageLoader(loader);
        dialog.show(manager, "CheckUpdateDialog");
        return dialog;
    }

    /**
     * 用于设置debug模式以及tag标签,debug模式下会打印log
     * 在application类中初始化
     *
     * @param tag   过滤log时的标签名
     * @param debug 是否为debug模式，在测试时为true，发布时为false【默认为false，即不打log】
     */
    public static void debug(@NonNull String tag, boolean debug) {
        TAG_NAME = tag;
        isDebug = debug;
    }

    /**
     * 用于设置debug模式以及tag标签,debug模式下会打印log
     * 在application类中初始化
     *
     * @param debug 是否为debug模式，在测试时为true，发布时为false【默认为false，即不打log】
     */
    public static void debug(boolean debug) {
        isDebug = debug;
    }
}
