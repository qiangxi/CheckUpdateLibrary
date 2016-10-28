package com.qiangxi.checkupdatelibrarydemo;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qiangxi.checkupdatelibrary.bean.CheckUpdateInfo;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;
import com.qiangxi.checkupdatelibrary.dialog.UpdateDialog;

import static com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog.FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE;
import static com.qiangxi.checkupdatelibrary.dialog.UpdateDialog.UPDATE_DIALOG_PERMISSION_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {
    private CheckUpdateInfo mCheckUpdateInfo;
    private UpdateDialog mUpdateDialog;
    private ForceUpdateDialog mForceUpdateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        //被注释的代码用来进行检查更新,这里为了方便,模拟一些假数据
        //第一种方式,不需要传参时,param参数置为null即可
//        Map<String,String> param = new HashMap<>();
//        param.put("key1","value1");
//        ...
//        params.put("keyN", "valueN");
//        Q.checkUpdate("post", getVersionCode(), "检查更新地址", param,new CheckUpdateCallback() {
//            @Override
//            public void onCheckUpdateSuccess(String result, boolean hasUpdate) {
//                Log.e("tag",result);
//                //result:服务端返回的json,解析成自己的实体类,当然您也可以使用checkupdatelibrary中自带的实体类CheckUpdateInfo
//                CheckUpdateInfo checkUpdateInfo = new Gson().fromJson(result, CheckUpdateInfo.class);
//                //有更新,显示dialog等
//                if (hasUpdate) {
//                    //强制更新,这里用0表示强制更新,实际情况中可与服务端商定什么数字代表强制更新即可
//                    if (checkUpdateInfo.getIsForceUpdate() == 0) {
//                        //show ForceUpdateDialog
//                    }
//                    //非强制更新
//                    else {
//                        //show UpdateDialog
//                    }
//                } else {
//                    //无更新,提示已是最新版等
//                }
//            }
//
//            @Override
//            public void onCheckUpdateFailure(String failureMessage, int errorCode) {
//                //failureMessage:一般为try{}catch(){}捕获的异常信息
//                //errorCode:暂时没什么用
//            }
//        });
        //第二种方式,不需要传参时,params参数置为null即可
//        Map<String, String> params = new HashMap<>();
//        params.put("key1", "value1");
//        //...
//        params.put("keyN", "valueN");
//        Q.checkUpdate(Q.GET, "检查更新地址", params, new CheckUpdateCallback2() {
//            @Override
//            public void onCheckUpdateSuccess(String result) {
//                Log.e("tag", result);
//                //result:服务端返回的json,需要自己判断有无更新,解析成自己的实体类进行判断是否有版本更新
//            }
//
//            @Override
//            public void onCheckUpdateFailure(String failureMessage) {
//                Log.e("tag", failureMessage);
//            }
//        });

        //模拟一些假数据
        mCheckUpdateInfo = new CheckUpdateInfo();
        mCheckUpdateInfo.setAppName("android检查更新库")
                .setIsForceUpdate(1)//设置是否强制更新,该方法的参数只要和服务端商定好什么数字代表强制更新即可
                .setNewAppReleaseTime("2016-10-14 12:37")//软件发布时间
                .setNewAppSize(12.3f)//单位为M
                .setNewAppUrl("http://shouji.360tpcdn.com/160914/c5164dfbbf98a443f72f32da936e1379/com.tencent.mobileqq_410.apk")
                .setNewAppVersionCode(20)//新app的VersionCode
                .setNewAppVersionName("1.0.2")
                .setNewAppUpdateDesc("1,优化下载逻辑\n2,修复一些bug\n3,完全实现强制更新与非强制更新逻辑\n4,非强制更新状态下进行下载,默认在后台进行下载\n5,当下载成功时,会在通知栏显示一个通知,点击该通知,进入安装应用界面\n6,当下载失败时,会在通知栏显示一个通知,点击该通知,会重新下载该应用\n7,当下载中,会在通知栏显示实时下载进度,但前提要dialog.setShowProgress(true).");
    }

    /**
     * 强制更新,checkupdatelibrary中提供的默认强制更新Dialog,您完全可以自定义自己的Dialog,
     */
    public void forceUpdateDialogClick(View view) {
        mCheckUpdateInfo.setIsForceUpdate(0);
        if (mCheckUpdateInfo.getIsForceUpdate() == 0) {
            mForceUpdateDialog = new ForceUpdateDialog(MainActivity.this);
            mForceUpdateDialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
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
     * 非强制更新,checkupdatelibrary中提供的默认非强制更新Dialog,您完全可以自定义自己的Dialog
     */
    public void UpdateDialogClick(View view) {
        mCheckUpdateInfo.setIsForceUpdate(1);
        if (mCheckUpdateInfo.getIsForceUpdate() == 1) {
            mUpdateDialog = new UpdateDialog(MainActivity.this);
            mUpdateDialog.setAppSize(mCheckUpdateInfo.getNewAppSize())
                    .setDownloadUrl(mCheckUpdateInfo.getNewAppUrl())
                    .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                    .setReleaseTime(mCheckUpdateInfo.getNewAppReleaseTime())
                    .setVersionName(mCheckUpdateInfo.getNewAppVersionName())
                    .setUpdateDesc(mCheckUpdateInfo.getNewAppUpdateDesc())
                    .setFileName("这是QQ.apk")
                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib")
                    //该方法需设为true,才会在通知栏显示下载进度,默认为false,即不显示
                    //该方法只会控制下载进度的展示,当下载完成或下载失败时展示的通知不受该方法影响
                    //即不管该方法是置为false还是true,当下载完成或下载失败时都会在通知栏展示一个通知
                    .setShowProgress(true)
                    .setIconResId(R.mipmap.ic_launcher)
                    .setAppName(mCheckUpdateInfo.getAppName()).show();
        }
    }

    /**
     * 获取当前应用版本号
     */
    private int getVersionCode() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 6.0系统需要重写此方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                mUpdateDialog.download();
            } else if (requestCode == FORCE_UPDATE_DIALOG_PERMISSION_REQUEST_CODE) {
                mForceUpdateDialog.download();
            }
        } else {
            //用户不同意,提示用户,如下载失败,因为您拒绝了相关权限
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.e("tag", "false.请开启读写sd卡权限,不然无法正常工作");
            } else {
                Log.e("tag", "true.请开启读写sd卡权限,不然无法正常工作");
            }
            Toast.makeText(this, "some description...", Toast.LENGTH_SHORT).show();
        }
    }
}

