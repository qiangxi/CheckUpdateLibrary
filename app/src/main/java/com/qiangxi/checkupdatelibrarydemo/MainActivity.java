package com.qiangxi.checkupdatelibrarydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void forceUpdateDialogClick(View view) {
        ForceUpdateDialog dialog = new ForceUpdateDialog(this);
        dialog.setAppSize(12.35f)
                .setDownloadUrl("http://123.56.66.24:8080")
                .setTitle("益民约车有更新啦~~")
                .setReleaseTime("2016-10-12 12:32")
                .setVersionName("V1.0.3")
                .setUpdateDesc("1,发发发发发沙发\n2,法师法师法师法非法\n3,挨个发放过萨嘎梵蒂冈如何哈时间的考拉还是大号的拉动哈伦裤");
        dialog.show();
    }
}
