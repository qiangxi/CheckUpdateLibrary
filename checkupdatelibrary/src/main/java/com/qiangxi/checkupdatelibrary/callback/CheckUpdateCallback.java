package com.qiangxi.checkupdatelibrary.callback;

/**
 * Created by qiang_xi on 2016/10/6 13:10.
 * 检查更新回调
 */

public interface CheckUpdateCallback {
    /**
     * 检查更新成功
     *
     * @param result    服务端返回的json信息
     * @param hasUpdate 是否有更新,true:有更新,false:无更新
     */
    void onCheckUpdateSuccess(String result, boolean hasUpdate);

    /**
     * 检查更新失败
     *
     * @param failureMessage 失败信息
     * @param errorCode      错误码
     */
    void onCheckUpdateFailure(String failureMessage, int errorCode);
}
