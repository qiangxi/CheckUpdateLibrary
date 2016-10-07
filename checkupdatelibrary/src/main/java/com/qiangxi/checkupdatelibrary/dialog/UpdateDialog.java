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
    private Button update;//开始更新
    private Button noUpdate;//暂不更新

    private String mDownloadUrl;//软件下载地址
    private String mTitle;//标题
    private String mAppTime;//发布时间
    private String mVersionName;//版本名
    private float mAppSize;//软件大小
    private String mAppDesc;//更新日志

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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 该种情况下,点击立即更新则关闭dialog,然后在后台service中进行下载
                Toast.makeText(context, "下载地址:" + mDownloadUrl, Toast.LENGTH_LONG).show();
            }
        });

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
    }

    private void initView() {
        updateTitle = (TextView) view.findViewById(R.id.updateTitle);
        updateTime = (TextView) view.findViewById(R.id.updateTime);
        updateVersion = (TextView) view.findViewById(R.id.updateVersion);
        updateSize = (TextView) view.findViewById(R.id.updateSize);
        updateDesc = (TextView) view.findViewById(R.id.updateDesc);
        update = (Button) view.findViewById(R.id.update);
        noUpdate = (Button) view.findViewById(R.id.noUpdate);
    }

    public UpdateDialog setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    public UpdateDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public UpdateDialog setReleaseTime(String releaseTime) {
        this.mAppTime = releaseTime;
        return this;
    }

    public UpdateDialog setVersionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    public UpdateDialog setUpdateDesc(String updateDesc) {
        this.mAppDesc = updateDesc;
        return this;
    }

    public UpdateDialog setAppSize(float appSize) {
        this.mAppSize = appSize;
        return this;
    }
}
