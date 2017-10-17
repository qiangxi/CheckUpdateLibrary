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
 * get/post的请求回调
 * </p>
 */
public interface StringCallback extends BaseCallback {
    /**
     * 检查更新接口请求成功回调
     *
     * @param result 接口返回的数据，考虑到返回结果可能为json也可能为xml，故交给开发者自己去解析处理
     */
    void checkUpdateSuccess(String result);

}
