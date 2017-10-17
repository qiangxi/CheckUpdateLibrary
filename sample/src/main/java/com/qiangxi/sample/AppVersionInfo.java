package com.qiangxi.sample;

import com.qiangxi.annotation.CheckUpdateEntity;

/**
 * Created by qiangxi(任强强) on 2017/9/15.
 */
@CheckUpdateEntity
public class AppVersionInfo {
    private String appName;
    private String appNewVersion;
    private float appSize;
    private boolean appHasUpdate;

    public AppVersionInfo() {
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppNewVersion() {
        return appNewVersion;
    }

    public void setAppNewVersion(String appNewVersion) {
        this.appNewVersion = appNewVersion;
    }

    public float getAppSize() {
        return appSize;
    }

    public void setAppSize(float appSize) {
        this.appSize = appSize;
    }

    public boolean isAppHasUpdate() {
        return appHasUpdate;
    }

    public void setAppHasUpdate(boolean appHasUpdate) {
        this.appHasUpdate = appHasUpdate;
    }
}
