package com.dysania.hencoderplus06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dysania.hencoderplus06.utils.UIUtil;

/**
 * Created by dysania on 12/12/18
 */

public class PieChartView extends View {
    // 半径
    private static final float RADIUS = UIUtil.dp2px(150);
    // 偏移
    private static final float OFFSET = UIUtil.dp2px(20);
    // 角度
    private static final int[] ANGLES = {60, 100, 120, 80};
    // 颜色
    private static final int[] COLORS = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.YELLOW};
    // 偏移索引
    private static final int PULLED_OUT_INDEX = 2;

    private RectF mBounds = new RectF();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        mBounds.set(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngel = 0;
        for (int i = 0; i < ANGLES.length; i++) {
            mPaint.setColor(COLORS[i]);
            canvas.save();
            if (i == PULLED_OUT_INDEX) {
                double calcAngel = Math.toRadians(currentAngel + ANGLES[i] / 2);
                canvas.translate((float) Math.cos(calcAngel) * OFFSET, (float) Math.sin(calcAngel) * OFFSET);
            }
            canvas.drawArc(mBounds, currentAngel, ANGLES[i], true, mPaint);
            canvas.restore();
            currentAngel += ANGLES[i];
        }
    }
}
