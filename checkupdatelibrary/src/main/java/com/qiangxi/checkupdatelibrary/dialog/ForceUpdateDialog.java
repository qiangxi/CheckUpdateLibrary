package com.qiangxi.checkupdatelibrary.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qiangxi.checkupdatelibrary.R;
import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.http.HttpRequest;
import com.qiangxi.checkupdatelibrary.utils.ApplicationUtil;
import com.qiangxi.checkupdatelibrary.utils.NetWorkUtil;
import com.qiangxi.checkupdatelibrary.views.NumberProgressBar;

import java.io.File;

/**
 * Created by qiang_xi on 2016/10/6 14:34.
 * 强制更新对话框
 */

public class ForceUpdateDialog extends Dialog {
    private Context context;
    private View view;
    private TextView forceUpdateTitle;//标题
    private TextView forceUpdateTime; //发布时间
    private TextView forceUpdateVersion;//版本名
    private TextView forceUpdateSize;//软件大小
    private TextView forceUpdateDesc;//更新日志
    private TextView forceUpdateNetworkState;//网络状况
    private NumberProgressBar forceUpdateProgress;//下载进度
    private Button forceUpdate;//开始更新
    private Button exitApp;//退出应用

    private String mDownloadUrl;//软件下载地址
    private String mTitle;//标题
    private String mAppTime;//发布时间
    private String mVersionName;//版本名
    private float mAppSize;//软件大小
    private String mAppDesc;//更新日志
    private String mFilePath;//文件存储路径
    private String mFileName;//自定义的文件名
    private long timeRange;//时间间隔

    public static final int FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE = 1;//权限请求码

    public ForceUpdateDialog(Context context) {
        super(context);
        setDialogTheme();
        setCanceledOnTouchOutside(false);
        this.context = context;
    }

    /**
     * set dialog theme(设置对话框主题)
     */
    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.force_update_dialog_layout, null);
        setContentView(view);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        forceUpdateTitle.setText(mTitle + "");
        forceUpdateTime.setText("发布时间:" + mAppTime);
        forceUpdateVersion.setText("版本:" + mVersionName);
        forceUpdateSize.setText("大小:" + mAppSize + "M");
        forceUpdateDesc.setText(mAppDesc + "");
        setNetWorkState();
    }

    /**
     * 设置网络状态
     */
    private void setNetWorkState() {
        if (NetWorkUtil.isWifiConnection(context)) {
            forceUpdateNetworkState.setText("当前为WiFi网络环境,可放心下载.");
            forceUpdateNetworkState.setTextColor(Color.parseColor("#629755"));
        } else if (NetWorkUtil.isMobileConnection(context)) {
            forceUpdateNetworkState.setText("当前为移动网络环境,下载将会消耗流量!");
            forceUpdateNetworkState.setTextColor(Color.parseColor("#BAA029"));
        } else if (!NetWorkUtil.hasNetConnection(context)) {
            forceUpdateNetworkState.setText("当前无网络连接,请打开网络后重试!");
            forceUpdateNetworkState.setTextColor(Color.RED);
        } else {
            forceUpdateNetworkState.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        forceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int permissionStatus = ActivityCompat.checkSelfPermission(context, Manifest.permission_group.STORAGE);
                    if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.
                                WRITE_EXTERNAL_STORAGE}, FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE);
                    }
                } else {
                    download();
                }
            }
        });
    }

    public void download() {
        //防抖动,两次点击间隔小于500ms都return;
        if (System.currentTimeMillis() - timeRange < 500) {
            return;
        }
        timeRange = System.currentTimeMillis();
        setNetWorkState();
        if (!NetWorkUtil.hasNetConnection(context)) {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("点击安装".equals(forceUpdate.getText().toString().trim())) {
            File file = new File(mFilePath, mFileName);
            if (file.exists()) {
                ApplicationUtil.installApk(context, file);
            } else {
                download();
            }
            return;
        }
        forceUpdateProgress.setVisibility(View.VISIBLE);
        HttpRequest.download(mDownloadUrl, mFilePath, mFileName, new DownloadCallback() {
            @Override
            public void onDownloadSuccess(File file) {
                forceUpdate.setEnabled(true);
                forceUpdate.setText("点击安装");
                ApplicationUtil.installApk(context, file);
            }

            @Override
            public void onProgress(long currentProgress, long totalProgress) {
                forceUpdate.setEnabled(false);
                forceUpdate.setText("正在下载");
                forceUpdateProgress.setProgress((int) (currentProgress));
                forceUpdateProgress.setMax((int) (totalProgress));
            }

            @Override
            public void onDownloadFailure(String failureMessage) {
                forceUpdate.setEnabled(true);
                forceUpdate.setText("重新下载");
            }
        });
    }

    private void initView() {
        forceUpdateTitle = (TextView) view.findViewById(R.id.forceUpdateTitle);
        forceUpdateTime = (TextView) view.findViewById(R.id.forceUpdateTime);
        forceUpdateVersion = (TextView) view.findViewById(R.id.forceUpdateVersion);
        forceUpdateSize = (TextView) view.findViewById(R.id.forceUpdateSize);
        forceUpdateDesc = (TextView) view.findViewById(R.id.forceUpdateDesc);
        forceUpdateNetworkState = (TextView) view.findViewById(R.id.forceUpdateNetworkState);
        forceUpdateProgress = (NumberProgressBar) view.findViewById(R.id.forceUpdateProgress);
        exitApp = (Button) view.findViewById(R.id.exitApp);
        forceUpdate = (Button) view.findViewById(R.id.forceUpdate);
    }

    @Override
    public void onBackPressed() {
        //强制更新时,按返回键不隐藏dialog,方法体置空即可
    }

    /**
     * 设置文件下载地址
     */
    public ForceUpdateDialog setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    /**
     * 设置dialog显示标题
     */
    public ForceUpdateDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置发布时间
     */
    public ForceUpdateDialog setReleaseTime(String releaseTime) {
        this.mAppTime = releaseTime;
        return this;
    }

    /**
     * 设置版本名,如2.2.1
     */
    public ForceUpdateDialog setVersionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    /**
     * 设置更新日志,需要自己分好段落
     */
    public ForceUpdateDialog setUpdateDesc(String updateDesc) {
        this.mAppDesc = updateDesc;
        return this;
    }

    /**
     * 设置软件大小
     */
    public ForceUpdateDialog setAppSize(float appSize) {
        this.mAppSize = appSize;
        return this;
    }

    /**
     * 设置文件存储路径
     */
    public ForceUpdateDialog setFilePath(String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    /**
     * 设置下载文件名
     */
    public ForceUpdateDialog setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }
}
