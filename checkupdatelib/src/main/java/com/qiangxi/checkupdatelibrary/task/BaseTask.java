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

import com.qiangxi.checkupdatelibrary.callback.BaseCallback;

import java.util.Map;

/**
 * Created by qiangxi(任强强) on 2017/10/15.
 * 任务基类
 */

abstract class BaseTask<T extends BaseTask> {
    protected String mUrl;//接口地址or下载地址
    protected Map<String, String> mParams;
    protected BaseCallback mCallback;

    BaseTask(String url) {
        mUrl = url;
    }

    BaseTask(String url, Map<String, String> params) {
        mUrl = url;
        mParams = params;
    }

    public T callback(BaseCallback callback) {
        mCallback = callback;
        return (T) this;
    }

    /**
     * 执行任务
     */
    public abstract void execute();

}
