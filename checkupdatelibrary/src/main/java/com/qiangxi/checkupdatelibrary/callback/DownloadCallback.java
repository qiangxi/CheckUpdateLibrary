package com.qiangxi.checkupdatelibrary.callback;

import java.io.File;

/**
 * Created by qiang_xi on 2016/10/9 21:05.
 */

public interface DownloadCallback {
    void onDownloadSuccess(File file);

    void onProgress(int currentProgress, int totalProgress);

    void onDownloadFailure(String failureMessage);
}
