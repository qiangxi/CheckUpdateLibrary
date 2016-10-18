package com.qiangxi.checkupdatelibrary.callback;

/**
 * 作者 任强强 on 2016/10/18 10:39.
 */

public interface CheckUpdateCallback2 {
    /**
     * 检查更新成功
     *
     * @param result 服务端返回的json信息
     */
    void onCheckUpdateSuccess(String result);

    /**
     * 检查更新失败
     *
     * @param failureMessage 失败信息
     */
    void onCheckUpdateFailure(String failureMessage);
}
