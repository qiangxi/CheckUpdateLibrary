package com.qiangxi.checkupdatelibrary.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qiangxi.checkupdatelibrary.CheckUpdateOption;

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
 * dialog基类，开发者可继承该类实现自己的dialog样式与逻辑
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected Context mContext;
    protected Activity mActivity;
    protected CheckUpdateOption mOption;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @NonNull
    @Override
    public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    protected abstract void agreeStoragePermission();

    protected abstract void rejectStoragePermission();


    public void applyOption(CheckUpdateOption option) {
        mOption = option;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!manager.isStateSaved()) {
            super.show(manager, tag);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 0x007) {
                agreeStoragePermission();
            }
        } else {
            rejectStoragePermission();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }
}
