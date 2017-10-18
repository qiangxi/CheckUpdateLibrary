package com.qiangxi.checkupdatelibrary.imageloader;
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

import android.widget.ImageView;

/**
 * Created by qiangxi(任强强) on 2017/10/18.
 * 为顶部ImageView设置图片，可从本地或网络加载图片，这里为网络加载图片
 */

public interface ImageLoader {
    /**
     * 从网络加载图片
     *
     * @param view     the ImageView
     * @param imageUrl 图片URL
     */
    void loadImage(ImageView view, String imageUrl);
}
