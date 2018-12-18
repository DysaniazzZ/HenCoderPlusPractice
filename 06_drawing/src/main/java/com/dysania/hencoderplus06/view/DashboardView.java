package com.dysania.hencoderplus06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dysania.hencoderplus06.utils.UIUtil;

/**
 * Created by dysania on 12/12/18
 */

public class DashboardView extends View {
    // 半径
    private static final float RADIUS = UIUtil.dp2px(150);
    // 指针长度
    private static final float LENGTH = UIUtil.dp2px(120);
    // 开口角度
    private static final int ANGLE = 120;
    // 刻度数
    private static final int DASH_NUMBER = 30;
    // 刻度宽度
    private static final float DASH_WIDTH = UIUtil.dp2px(2);
    // 刻度高度
    private static final float DASH_HEIGHT = UIUtil.dp2px(10);
    // 进度
    private double mProgress = 0.77;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Path mDashPath = new Path();
    PathDashPathEffect mPathDashPathEffect;

    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(UIUtil.dp2px(2));

        Path arcPath = new Path();
        arcPath.addArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE);
        float arcLength = new PathMeasure(arcPath, false).getLength();
        mDashPath.addRect(0, 0, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CW);   // 顺时针方向
        mPathDashPathEffect = new PathDashPathEffect(mDashPath, (arcLength - DASH_WIDTH) / (DASH_NUMBER - 1), 0, PathDashPathEffect.Style.ROTATE);   // advance 间隔的距离，phase 第一个刻度的偏移
    }

    public DashboardView(Context context) {
        super(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // 弧
        canvas.drawArc(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, mPaint);

        // 刻度线
        mPaint.setPathEffect(mPathDashPathEffect);
        canvas.drawArc(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, mPaint);
        mPaint.setPathEffect(null);

        // 指针
        double angle = getAngleFromProgress(mProgress);
        canvas.drawLine(width / 2, height / 2, (float) (width / 2 + Math.cos(Math.toRadians(angle)) * LENGTH), (float) (height / 2 + Math.sin(Math.toRadians(angle)) * LENGTH), mPaint);
    }

    private double getAngleFromProgress(double progress) {
        return 90 + ANGLE / 2 + (360 - ANGLE) * progress;
    }
}
