/*
 * Copyright (C) 2015 tyrantgit
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
package com.d.lib.ui.layout.heartlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.d.lib.ui.layout.R;


/**
 * 飘心动画心形界面类
 * 一个TCHeartView代表一个心形，由heart.png组成
 */
public class TCHeartView extends AppCompatImageView {

    private static final Paint sPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private static final Canvas sCanvas = new Canvas();
    private static Bitmap sHeart;
    private static Bitmap sHeartBorder;
    private int mHeartResId = R.drawable.lib_ui_layout_hl_ic_heart0;
    private int mHeartBorderResId = R.drawable.lib_ui_layout_hl_ic_heart1;

    public TCHeartView(Context context) {
        super(context);
    }

    public TCHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TCHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static Bitmap createBitmapSafely(int width, int height) {
        try {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return null;
    }

    public void setDrawable(BitmapDrawable bitmap) {
        setImageDrawable(bitmap);
    }

    public void setColor(int color) {
        Bitmap heart = createHeart(color);
        setImageDrawable(new BitmapDrawable(getResources(), heart));
    }

    public void setColorAndDrawables(int color, int heartResId, int heartBorderResId) {
        if (heartResId != mHeartResId) {
            sHeart = null;
        }
        if (heartBorderResId != mHeartBorderResId) {
            sHeartBorder = null;
        }
        mHeartResId = heartResId;
        mHeartBorderResId = heartBorderResId;
        setColor(color);
    }

    public Bitmap createHeart(int color) {
        if (sHeart == null) {
            sHeart = BitmapFactory.decodeResource(getResources(), mHeartResId);
        }
        if (sHeartBorder == null) {
            sHeartBorder = BitmapFactory.decodeResource(getResources(), mHeartBorderResId);
        }
        Bitmap heart = sHeart;
        Bitmap heartBorder = sHeartBorder;
        Bitmap bm = createBitmapSafely(heartBorder.getWidth(), heartBorder.getHeight());
        if (bm == null) {
            return null;
        }
        Canvas canvas = sCanvas;
        canvas.setBitmap(bm);
        Paint p = sPaint;
        canvas.drawBitmap(heartBorder, 0, 0, p);
        p.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        float dx = (heartBorder.getWidth() - heart.getWidth()) / 2f;
        float dy = (heartBorder.getHeight() - heart.getHeight()) / 2f;
        canvas.drawBitmap(heart, dx, dy, p);
        p.setColorFilter(null);
        canvas.setBitmap(null);
        return bm;
    }
}
