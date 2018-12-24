package com.dysania.hencoderplus07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dysania.hencoderplus07.R;
import com.dysania.hencoderplus07.utils.UIUtil;

/**
 * Created by dysania on 12/24/18
 */

public class SportView extends View {
    private static final float RADIUS = UIUtil.dp2px(150);

    private float mProgress = 0.66f;
    private String mProgressText = "DysaniazzZ";
    
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mPaint.setStrokeWidth(UIUtil.dp2px(20));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(UIUtil.sp2px(50));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public SportView(Context context) {
        super(context);
    }

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawCircle(cx, cy, RADIUS, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        RectF rectF = new RectF(cx - RADIUS, cy - RADIUS, cx + RADIUS, cy + RADIUS);
        float sweepAngle = 360 * mProgress;
        canvas.drawArc(rectF, -90, sweepAngle, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);

        // 第一种文字居中方式，绝对居中，适合静态文字，如果文字变动会出现上下跳动的现象，因为 bounds 在变
//        Rect bounds = new Rect();
//        mPaint.getTextBounds(mProgressText, 0, mProgressText.length(), bounds);
//        float offset = (bounds.top + bounds.bottom) / 2;

        // 第二种文字居中方式，不是绝对居中，适合动态文字，文字变动时不会出现上下跳动
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;

        canvas.drawText(mProgressText, 0, mProgressText.length(), cx, cy - offset, mPaint);
    }
}
