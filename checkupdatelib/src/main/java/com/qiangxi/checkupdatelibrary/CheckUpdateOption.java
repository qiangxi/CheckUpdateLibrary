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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

/**
 * Created by qiangxi(任强强) on 2017/10/15.
 * CheckUpdateDialog的配置实体
 */

public class CheckUpdateOption implements Parcelable {
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
    private int notificationIconResId;//通知图标
    private String notificationTitle;//通知标题
    private String notificationSuccessContent;//下载成功通知内容
    private String notificationFailureContent;//下载失败通知内容
    private String imageUrl;//图片地址
    private int imageResId;//图片本地资源id

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

    public int getNotificationIconResId() {
        return notificationIconResId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationSuccessContent() {
        return notificationSuccessContent;
    }

    public String getNotificationFailureContent() {
        return notificationFailureContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImageResId() {
        return imageResId;
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

        public Builder setNotificationIconResId(@DrawableRes int notificationIconResId) {
            mOption.notificationIconResId = notificationIconResId;
            return this;
        }

        public Builder setNotificationTitle(String notificationTitle) {
            mOption.notificationTitle = notificationTitle;
            return this;
        }

        public Builder setNotificationSuccessContent(String notificationSuccessContent) {
            mOption.notificationSuccessContent = notificationSuccessContent;
            return this;
        }

        public Builder setNotificationFailureContent(String notificationFailureContent) {
            mOption.notificationFailureContent = notificationFailureContent;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            mOption.imageUrl = imageUrl;
            return this;
        }

        public Builder setImageResId(@DrawableRes int imageResId) {
            mOption.imageResId = imageResId;
            return this;
        }

        public CheckUpdateOption build() {
            return mOption;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appName);
        dest.writeFloat(this.newAppSize);
        dest.writeInt(this.newAppVersionCode);
        dest.writeString(this.newAppVersionName);
        dest.writeString(this.newAppUpdateDesc);
        dest.writeString(this.newAppReleaseTime);
        dest.writeString(this.newAppUrl);
        dest.writeByte(this.isForceUpdate ? (byte) 1 : (byte) 0);
        dest.writeString(this.filePath);
        dest.writeString(this.fileName);
        dest.writeInt(this.notificationIconResId);
        dest.writeString(this.notificationTitle);
        dest.writeString(this.notificationSuccessContent);
        dest.writeString(this.notificationFailureContent);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.imageResId);
    }

    protected CheckUpdateOption(Parcel in) {
        this.appName = in.readString();
        this.newAppSize = in.readFloat();
        this.newAppVersionCode = in.readInt();
        this.newAppVersionName = in.readString();
        this.newAppUpdateDesc = in.readString();
        this.newAppReleaseTime = in.readString();
        this.newAppUrl = in.readString();
        this.isForceUpdate = in.readByte() != 0;
        this.filePath = in.readString();
        this.fileName = in.readString();
        this.notificationIconResId = in.readInt();
        this.notificationTitle = in.readString();
        this.notificationSuccessContent = in.readString();
        this.notificationFailureContent = in.readString();
        this.imageUrl = in.readString();
        this.imageResId = in.readInt();
    }

    public static final Creator<CheckUpdateOption> CREATOR = new Creator<CheckUpdateOption>() {
        @Override
        public CheckUpdateOption createFromParcel(Parcel source) {
            return new CheckUpdateOption(source);
        }

        @Override
        public CheckUpdateOption[] newArray(int size) {
            return new CheckUpdateOption[size];
        }
    };
}
