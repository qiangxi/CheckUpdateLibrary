package com.qiangxi.checkupdatelibrary.dialog;

import android.app.Dialog;
import android.content.Context;
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
import com.qiangxi.checkupdatelibrary.views.NumberProgressBar;

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
    private NumberProgressBar forceUpdateProgress;//下载进度
    private Button forceUpdate;//开始更新

    private String mDownloadUrl;//软件下载地址
    private String mTitle;//标题
    private String mAppTime;//发布时间
    private String mVersionName;//版本名
    private float mAppSize;//软件大小
    private String mAppDesc;//更新日志

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
    }

    private void initEvent() {
        forceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forceUpdateProgress.setVisibility(View.VISIBLE);
                Toast.makeText(context, "下载地址:" + mDownloadUrl, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        forceUpdateTitle = (TextView) view.findViewById(R.id.forceUpdateTitle);
        forceUpdateTime = (TextView) view.findViewById(R.id.forceUpdateTime);
        forceUpdateVersion = (TextView) view.findViewById(R.id.forceUpdateVersion);
        forceUpdateSize = (TextView) view.findViewById(R.id.forceUpdateSize);
        forceUpdateDesc = (TextView) view.findViewById(R.id.forceUpdateDesc);
        forceUpdateProgress = (NumberProgressBar) view.findViewById(R.id.forceUpdateProgress);
        forceUpdate = (Button) view.findViewById(R.id.forceUpdate);
    }

    @Override
    public void onBackPressed() {
        //强制更新时,按返回键不隐藏dialog,方法体置空即可
    }

    public ForceUpdateDialog setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    public ForceUpdateDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public ForceUpdateDialog setReleaseTime(String releaseTime) {
        this.mAppTime = releaseTime;
        return this;
    }

    public ForceUpdateDialog setVersionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    public ForceUpdateDialog setUpdateDesc(String updateDesc) {
        this.mAppDesc = updateDesc;
        return this;
    }

    public ForceUpdateDialog setAppSize(float appSize) {
        this.mAppSize = appSize;
        return this;
    }

}
