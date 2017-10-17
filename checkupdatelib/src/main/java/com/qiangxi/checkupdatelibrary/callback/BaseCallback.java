package com.qiangxi.checkupdatelibrary.callback;
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
 * Created by qiangxi(任强强) on 2017/9/25.<p>
 * 基类回调接口，所有回调接口全部被分发到了主线程，可以放心使用
 * </p>
 */

public interface BaseCallback {
    /**
     * 开始检查更新或开始下载更新
     */
    void checkUpdateStart();

    /**
     * 检查更新失败或下载更新失败
     *
     * @param t 把Throwable返回，并且内部也打印了log，方便开发时调试，
     */
    void checkUpdateFailure(Throwable t);

    /**
     * 检查更新结束或下载更新结束
     */
    void checkUpdateFinish();

}
