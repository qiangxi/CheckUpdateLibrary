package com.qiangxi.checkupdatelibrary.task;
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

import com.qiangxi.checkupdatelibrary.request.DownloadRequest;
import com.qiangxi.checkupdatelibrary.request.IRequest;

import java.io.File;

/**
 * Created by qiangxi(任强强) on 2017/10/15.
 * Download任务
 */

public class DownloadTask extends BaseTask<DownloadTask> {
    private File mApk;//下载的apk文件

    public DownloadTask(String url, String filePath, String fileName) {
        super(url);
        mApk = createFile(filePath, fileName);
    }

    @Override
    public void execute() {
        IRequest request = new DownloadRequest(mUrl, mApk, mCallback);
        request.request();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File createFile(String filePath, String fileName) {
        createDir(filePath);
        File file = new File(filePath, fileName);
        //如果存在该文件,则删除
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDir(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}
