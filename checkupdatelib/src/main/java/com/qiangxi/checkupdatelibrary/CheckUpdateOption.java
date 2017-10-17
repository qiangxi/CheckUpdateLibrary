package com.qiangxi.checkupdatelibrary;
/*
 * Copyright © qiangxi(任强强)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by qiangxi(任强强) on 2017/10/15.
 * CheckUpdateDialog的配置实体
 */

public class CheckUpdateOption {
    private String appName;//app名称
    private float newAppSize;//新app大小
    private int newAppVersionCode;//新app版本号
    private String newAppVersionName;//新app版本名
    private String newAppUpdateDesc;//新app更新描述
    private String newAppReleaseTime;//新app发布时间
    private String newAppUrl;//新app下载地址
    private boolean isForceUpdate;//是否强制更新
    private String filePath;//apk存储路径
    private String fileName;//apk名称

    private CheckUpdateOption() {
    }

    public String getAppName() {
        return appName;
    }

    public float getNewAppSize() {
        return newAppSize;
    }

    public int getNewAppVersionCode() {
        return newAppVersionCode;
    }

    public String getNewAppVersionName() {
        return newAppVersionName;
    }

    public String getNewAppUpdateDesc() {
        return newAppUpdateDesc;
    }

    public String getNewAppReleaseTime() {
        return newAppReleaseTime;
    }

    public String getNewAppUrl() {
        return newAppUrl;
    }

    public boolean isForceUpdate() {
        return isForceUpdate;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public static class Builder {
        private CheckUpdateOption mOption;

        public Builder() {
            mOption = new CheckUpdateOption();
        }

        public Builder setOption(CheckUpdateOption option) {
            mOption = option;
            return this;
        }

        public Builder setAppName(String appName) {
            mOption.appName = appName;
            return this;
        }

        public Builder setNewAppSize(float newAppSize) {
            mOption.newAppSize = newAppSize;
            return this;
        }

        public Builder setNewAppVersionCode(int newAppVersionCode) {
            mOption.newAppVersionCode = newAppVersionCode;
            return this;
        }

        public Builder setNewAppVersionName(String newAppVersionName) {
            mOption.newAppVersionName = newAppVersionName;
            return this;
        }

        public Builder setNewAppUpdateDesc(String newAppUpdateDesc) {
            mOption.newAppUpdateDesc = newAppUpdateDesc;
            return this;
        }

        public Builder setNewAppReleaseTime(String newAppReleaseTime) {
            mOption.newAppReleaseTime = newAppReleaseTime;
            return this;
        }

        public Builder setNewAppUrl(String newAppUrl) {
            mOption.newAppUrl = newAppUrl;
            return this;
        }

        public Builder setIsForceUpdate(boolean isForceUpdate) {
            mOption.isForceUpdate = isForceUpdate;
            return this;
        }

        public Builder setFilePath(String filePath) {
            mOption.filePath = filePath;
            return this;
        }

        public Builder setFileName(String fileName) {
            mOption.fileName = fileName;
            return this;
        }

        public CheckUpdateOption build() {
            return mOption;
        }
    }
}
