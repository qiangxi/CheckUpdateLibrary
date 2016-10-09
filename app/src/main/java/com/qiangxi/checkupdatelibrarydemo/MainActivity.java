package com.qiangxi.checkupdatelibrarydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qiangxi.checkupdatelibrary.Q;
import com.qiangxi.checkupdatelibrary.callback.CheckUpdateCallback;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void forceUpdateDialogClick(View view) {
        Q.checkUpdate("post", "", new CheckUpdateCallback() {
            @Override
            public void onCheckUpdateSuccess(String result, boolean hasUpdate) {
                if (hasUpdate) {
//                    if (checkUpdateInfo.getIsForceUpdate() == 0) {
//                        ForceUpdateDialog dialog = new ForceUpdateDialog(MainActivity.this);
//                        dialog.setAppSize(checkUpdateInfo.getNewAppSize())
//                                .setDownloadUrl(checkUpdateInfo.getNewAppUrl())
//                                .setTitle(checkUpdateInfo.getAppName() + "有更新啦")
//                                .setReleaseTime(checkUpdateInfo.getNewAppReleaseTime())
//                                .setVersionName(checkUpdateInfo.getNewAppVersionName())
//                                .setUpdateDesc(checkUpdateInfo.getNewAppUpdateDesc());
//                        dialog.show();
//                    }
                } else {
                    Toast.makeText(MainActivity.this, "当前已经是最新版本啦", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCheckUpdateFailure(String failureMessage, int errorCode) {
                Toast.makeText(MainActivity.this, "啊哦,遇到问题了,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
        ForceUpdateDialog dialog = new ForceUpdateDialog(this);
        dialog.setAppSize(12.35f)
                .setDownloadUrl("http://123.56.66.24:8080")
                .setTitle("益民约车有更新啦~~")
                .setReleaseTime("2016-10-12 12:32")
                .setVersionName("V1.0.3")
                .setUpdateDesc("1,发发发发发沙发\n2,法师法师法师法非法\n3,挨个发放过萨嘎梵蒂冈如何哈时间的考拉还是大号的拉动哈伦裤");
        dialog.show();
    }

    public void UpdateDialogClick(View view) {
        Q.checkUpdate("post", "", new CheckUpdateCallback() {
            @Override
            public void onCheckUpdateSuccess(String result, boolean hasUpdate) {
                if (hasUpdate) {
//                    if (checkUpdateInfo.getIsForceUpdate() == 1) {
//                        UpdateDialog dialog = new UpdateDialog(MainActivity.this);
//                        dialog.setAppSize(checkUpdateInfo.getNewAppSize())
//                                .setDownloadUrl(checkUpdateInfo.getNewAppUrl())
//                                .setTitle(checkUpdateInfo.getAppName() + "有更新啦")
//                                .setReleaseTime(checkUpdateInfo.getNewAppReleaseTime())
//                                .setVersionName(checkUpdateInfo.getNewAppVersionName())
//                                .setUpdateDesc(checkUpdateInfo.getNewAppUpdateDesc());
//                        dialog.show();
//                    }
                } else {
                    Toast.makeText(MainActivity.this, "当前已经是最新版本啦", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCheckUpdateFailure(String failureMessage, int errorCode) {
                Toast.makeText(MainActivity.this, "啊哦,遇到问题了,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
        UpdateDialog dialog = new UpdateDialog(this);
        dialog.setAppSize(14.35f)
                .setDownloadUrl("http://123.56.66.24")
                .setTitle("失物招领有更新啦~~")
                .setReleaseTime("2016-01-12 12:32")
                .setVersionName("V1.2.3")
                .setUpdateDesc("1,发发发发发沙发\n2,法师法师法师法非法");
        dialog.show();
    }
}
