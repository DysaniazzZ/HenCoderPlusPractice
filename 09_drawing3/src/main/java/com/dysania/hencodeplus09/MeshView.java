package com.dysania.hencodeplus09;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dysania on 4/6/19
 */

public class MeshView extends View {
    private Drawable mDrawable;

    {
        mDrawable = new MeshDrawable();
    }

    public MeshView(Context context) {
        super(context);
    }

    public MeshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable.setBounds((int) UIUtil.dp2px(10), (int) UIUtil.dp2px(10), getWidth(), getHeight());
        mDrawable.draw(canvas);
    }
}
