package com.qiangxi.checkupdatelibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qiangxi.checkupdatelibrary.R;
import com.qiangxi.checkupdatelibrary.service.DownloadService;
import com.qiangxi.checkupdatelibrary.utils.NetWorkUtil;

/**
 * Created by qiang_xi on 2016/10/7 13:04.
 * 非强制更新对话框
 */

public class UpdateDialog extends Dialog {
    private Context context;
    private View view;
    private TextView updateTitle;//标题
    private TextView updateTime; //发布时间
    private TextView updateVersion;//版本名
    private TextView updateSize;//软件大小
    private TextView updateDesc;//更新日志
    private TextView updateNetworkState;//网络状况
    private Button update;//开始更新
    private Button noUpdate;//暂不更新

    private String mDownloadUrl;//软件下载地址
    private String mTitle;//标题
    private String mAppTime;//发布时间
    private String mVersionName;//版本名
    private float mAppSize;//软件大小
    private String mAppDesc;//更新日志
    private String mAppName;//app名称
    private String mFilePath;//文件存储路径
    private String mFileName;//自定义的文件名
    private int mIconResId;//通知栏图标id
    private boolean isShowProgress = false;//是否在通知栏显示下载进度,默认不显示
    private long timeRange;//时间间隔

    public UpdateDialog(Context context) {
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
        view = LayoutInflater.from(context).inflate(R.layout.update_dialog_layout, null);
        setContentView(view);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //更新
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防抖动,两次点击间隔小于1s都return;
                if (System.currentTimeMillis() - timeRange < 1000) {
                    return;
                }
                timeRange = System.currentTimeMillis();
                setNetWorkState();
                if (!NetWorkUtil.hasNetConnection(context)) {
                    Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, DownloadService.class);
                intent.putExtra("downloadUrl", mDownloadUrl);
                intent.putExtra("filePath", mFilePath);
                intent.putExtra("fileName", mFileName);
                intent.putExtra("iconResId", mIconResId);
                intent.putExtra("isShowProgress", isShowProgress);
                intent.putExtra("appName", mAppName);
                context.startService(intent);
                dismiss();
                Toast.makeText(context, "正在后台为您下载", Toast.LENGTH_SHORT).show();
            }
        });
        //不更新
        noUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initData() {
        updateTitle.setText(mTitle + "");
        updateTime.setText("发布时间:" + mAppTime);
        updateVersion.setText("版本:" + mVersionName);
        updateSize.setText("大小:" + mAppSize + "M");
        updateDesc.setText(mAppDesc + "");
        setNetWorkState();
    }

    /**
     * 设置网络状态
     */
    private void setNetWorkState() {
        if (NetWorkUtil.isWifiConnection(context)) {
            updateNetworkState.setText("当前为WiFi网络环境,可放心下载.");
            updateNetworkState.setTextColor(Color.parseColor("#629755"));
        } else if (NetWorkUtil.isMobileConnection(context)) {
            updateNetworkState.setText("当前为移动网络环境,下载将会消耗流量!");
            updateNetworkState.setTextColor(Color.parseColor("#BAA029"));
        } else if (!NetWorkUtil.hasNetConnection(context)) {
            updateNetworkState.setText("当前无网络连接,请打开网络后重试!");
            updateNetworkState.setTextColor(Color.RED);
        } else {
            updateNetworkState.setVisibility(View.GONE);
        }
    }

    private void initView() {
        updateTitle = (TextView) view.findViewById(R.id.updateTitle);
        updateTime = (TextView) view.findViewById(R.id.updateTime);
        updateVersion = (TextView) view.findViewById(R.id.updateVersion);
        updateSize = (TextView) view.findViewById(R.id.updateSize);
        updateDesc = (TextView) view.findViewById(R.id.updateDesc);
        updateNetworkState = (TextView) view.findViewById(R.id.updateNetworkState);
        update = (Button) view.findViewById(R.id.update);
        noUpdate = (Button) view.findViewById(R.id.noUpdate);
    }

    /**
     * 设置文件下载地址
     */
    public UpdateDialog setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    /**
     * 设置dialog显示标题
     */
    public UpdateDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置发布时间
     */
    public UpdateDialog setReleaseTime(String releaseTime) {
        this.mAppTime = releaseTime;
        return this;
    }

    /**
     * 设置版本名,如2.2.1
     */
    public UpdateDialog setVersionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    /**
     * 设置更新日志,需要自己分好段落
     */
    public UpdateDialog setUpdateDesc(String updateDesc) {
        this.mAppDesc = updateDesc;
        return this;
    }

    /**
     * 设置软件大小
     */
    public UpdateDialog setAppSize(float appSize) {
        this.mAppSize = appSize;
        return this;
    }

    /**
     * 设置文件存储路径
     */
    public UpdateDialog setFilePath(String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    /**
     * 设置下载文件名
     */
    public UpdateDialog setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    /**
     * 设置图标资源id,该图标用来显示在通知栏中
     */
    public UpdateDialog setIconResId(int iconResId) {
        this.mIconResId = iconResId;
        return this;
    }

    /**
     * 是否在通知栏显示下载进度(true:显示,false:不显示,默认不显示)
     */
    public UpdateDialog setShowProgress(boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
        return this;
    }

    /**
     * 设置软件名称
     */
    public UpdateDialog setAppName(String appName) {
        this.mAppName = appName;
        return this;
    }
}
