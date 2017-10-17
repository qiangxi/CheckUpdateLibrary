package com.qiangxi.checkupdatelibrary.utils;
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

import android.util.Log;

import com.qiangxi.checkupdatelibrary.Q;

/**
 * Created by qiangxi(任强强) on 2017/9/25.
 * 简易日志类
 */

public class L {
    /**
     * tag标签可通过#Q.debug(String tag, boolean debug)方法设置
     *
     * @param msg log信息
     */
    public static void e(String msg) {
        if (Q.isDebug) Log.e(Q.TAG_NAME, msg);
    }

    /**
     * 打印Throwable日志
     *
     * @param t the Throwable
     */
    public static void e(Throwable t) {
        if (t == null || !Q.isDebug) return;
        StackTraceElement[] stackTrace = t.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append("\nExceptionMsg：").append(t.getMessage());
        for (StackTraceElement element : stackTrace) {
            sb.append("\nClassName：").append(element.getClassName())
                    .append("\nMethodName：").append(element.getMethodName())
                    .append("\nLineNumber：").append(element.getLineNumber());
        }
        Log.e(Q.TAG_NAME, sb.toString());
    }


}
