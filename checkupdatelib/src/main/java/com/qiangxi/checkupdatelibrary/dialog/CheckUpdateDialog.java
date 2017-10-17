package com.qiangxi.checkupdatelibrary.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qiangxi.checkupdatelibrary.CheckUpdateOption;
import com.qiangxi.checkupdatelibrary.Q;
import com.qiangxi.checkupdatelibrary.R;
import com.qiangxi.checkupdatelibrary.callback.DownloadCallback;
import com.qiangxi.checkupdatelibrary.utils.AppUtil;

import java.io.File;

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
 * CheckUpdateDialog
 */
public class CheckUpdateDialog extends BaseDialogFragment {
    private InternalDialog mDialog;

    @Override
    public InternalDialog getDialog() {
        return mDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new InternalDialog(mActivity);
        mDialog.apply(mOption);
        return mDialog;
    }
}

class InternalDialog extends Dialog implements DownloadCallback {
    private CheckUpdateOption mOption;
    private ImageView checkUpdateImage;
    private TextView checkUpdateVersionCode;
    private TextView checkUpdateVersionSize;
    private TextView checkUpdateVersionLog;
    private TextView checkUpdateNegative;
    private TextView checkUpdatePositive;
    private ProgressBar checkUpdateProgressBar;

    InternalDialog(@NonNull Context context) {
        super(context, R.style.checkUpdateDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_check_update_layout, null);
        setContentView(layout);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        checkUpdateImage = (ImageView) findViewById(R.id.check_update_image);
        checkUpdateVersionCode = (TextView) findViewById(R.id.check_update_version_code);
        checkUpdateVersionSize = (TextView) findViewById(R.id.check_update_version_size);
        checkUpdateVersionLog = (TextView) findViewById(R.id.check_update_version_log);
        checkUpdateProgressBar = (ProgressBar) findViewById(R.id.check_update_progress);
        checkUpdateNegative = (TextView) findViewById(R.id.check_update_negative);
        checkUpdatePositive = (TextView) findViewById(R.id.check_update_positive);
    }

    private void initEvent() {
        //暂不更新 or 退出应用
        checkUpdateNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissIfNeeded();
            }
        });
        //立即更新
        checkUpdatePositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadInBackgroundIfNeeded();
            }
        });
    }

    private void downloadInBackgroundIfNeeded() {
        if (mOption.isForceUpdate()) {
            Q.download(mOption.getNewAppUrl(), mOption.getFilePath(), mOption.getFileName())
                    .callback(this).execute();
        } else {
            // TODO: 2017/10/17 do in background
        }
    }

    private void dismissIfNeeded() {
        if (mOption.isForceUpdate()) {
            System.exit(0);
            Process.killProcess(Process.myPid());
        } else {
            dismiss();
        }
    }


    @SuppressLint("SetTextI18n")
    private void initData() {
        if (mOption.isForceUpdate()) {
            setCancelable(false);
            checkUpdateNegative.setText("退出应用");
        }
        if (TextUtils.isEmpty(mOption.getNewAppVersionName())) {
            checkUpdateVersionCode.setVisibility(View.GONE);
        } else {
            checkUpdateVersionCode.setText("版本号：" + mOption.getNewAppVersionName());
        }
        if (mOption.getNewAppSize() == 0) {
            checkUpdateVersionSize.setVisibility(View.GONE);
        } else {
            checkUpdateVersionSize.setText("新版大小：" + mOption.getNewAppSize() + "M");
        }
        if (TextUtils.isEmpty(mOption.getNewAppUpdateDesc())) {
            checkUpdateVersionLog.setVisibility(View.GONE);
        } else {
            checkUpdateVersionLog.setText(mOption.getNewAppUpdateDesc());
        }
    }


    void apply(CheckUpdateOption option) {
        if (option == null) throw new NullPointerException("option==null");
        mOption = option;
    }

    @Override
    public void checkUpdateStart() {

    }

    @Override
    public void checkUpdateFailure(Throwable t) {

    }

    @Override
    public void checkUpdateFinish() {

    }

    @Override
    public void downloadProgress(long currentLength, long totalLength) {
        checkUpdateProgressBar.setMax((int) totalLength);
        checkUpdateProgressBar.setProgress((int) currentLength);
    }

    @Override
    public void downloadSuccess(File apk) {
        AppUtil.installApk(getContext(), apk);
    }
}
