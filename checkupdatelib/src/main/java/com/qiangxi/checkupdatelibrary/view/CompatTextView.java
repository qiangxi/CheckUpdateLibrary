package com.qiangxi.checkupdatelibrary.view;
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qiangxi.checkupdatelibrary.R;

/**
 * Created by 任强强 on 2017/7/30.<br/>
 * 兼容TextView:<br/>
 * - 在5.0及以上版本会默认有新特性-水波纹效果<br/>
 * - 在5.0以下版本,会自动切换成selector方式的Drawable<br/><br/>
 * <strong>注意:</strong>使用该控件后,不要再使用android:background=""属性<br/>
 * <br/>
 * 使用如下方式代替:
 * <p>
 * <code>
 * app:contentDrawable="" <br/>
 * app:maskDrawable=""<br/>
 * app:rippleColor=""<br/>
 * app:selectorDrawable=""<br/>
 * </code>
 * </p>
 */

@SuppressLint("AppCompatCustomView")
public class CompatTextView extends TextView {

    private Drawable mContentDrawable;//必填，TextView的背景,可见
    private Drawable mMaskDrawable;//按需，RippleColor扩散的区域,不可见
    private Drawable mSelectorDrawable;//必填，兼容5.0以下版本的TextView背景
    private ColorStateList mRippleColor;//必填，水波纹扩散的颜色

    public CompatTextView(Context context) {
        this(context, null);
    }

    public CompatTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompatTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CompatTextView);
        int rippleColor = a.getColor(R.styleable.CompatTextView_rippleColor, Color.BLUE);
        mRippleColor = ColorStateList.valueOf(rippleColor);
        mContentDrawable = a.getDrawable(R.styleable.CompatTextView_contentDrawable);
        mMaskDrawable = a.getDrawable(R.styleable.CompatTextView_maskDrawable);
        mSelectorDrawable = a.getDrawable(R.styleable.CompatTextView_selectorDrawable);
        a.recycle();
        init();
    }

    private void init() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable drawable = new RippleDrawable(mRippleColor, mContentDrawable, mMaskDrawable);
            setBackground(drawable);
        } else {
            setBackground(mSelectorDrawable);
        }
    }
}
