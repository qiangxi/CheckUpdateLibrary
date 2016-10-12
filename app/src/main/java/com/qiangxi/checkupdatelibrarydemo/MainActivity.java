package com.qiangxi.checkupdatelibrarydemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiangxi.checkupdatelibrary.bean.CheckUpdateInfo;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;

public class MainActivity extends AppCompatActivity {
    private CheckUpdateInfo mCheckUpdateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }


    private void initData() {
        mCheckUpdateInfo = new CheckUpdateInfo();
        mCheckUpdateInfo.setAppName("益民约车").setIsForceUpdate(0).setNewAppReleaseTime("2016-10-12 09:52")
                .setNewAppSize(12.3f).setNewAppUrl("http://shouji.360tpcdn.com/160914/c5164dfbbf98a443f72f32da936e1379/com.tencent.mobileqq_410.apk").setNewAppVersionCode(20).setOldAppVersionCode(19).
                setNewAppVersionName("2.1.2")
                .setNewAppUpdateDesc("1,发发发发发沙发\n2,法师法师法师法非法\n3,挨个发放过萨嘎梵蒂冈如何哈时间的考拉还是大号的拉动哈伦裤");
    }

    /**
     * 强制更新
     */
    public void forceUpdateDialogClick(View view) {
        if (mCheckUpdateInfo.getIsForceUpdate() == 0) {
            ForceUpdateDialog dialog = new ForceUpdateDialog(MainActivity.this);
            dialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib").show();
        }
    }

    /**
     * 非强制更新
     */
    public void UpdateDialogClick(View view) {
        if (mCheckUpdateInfo.getIsForceUpdate() == 1) {
            UpdateDialog dialog = new UpdateDialog(MainActivity.this);
            dialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                    .setShowProgress(true)
                    .setIconResId(R.mipmap.ic_launcher).show();
        }

    }
}
