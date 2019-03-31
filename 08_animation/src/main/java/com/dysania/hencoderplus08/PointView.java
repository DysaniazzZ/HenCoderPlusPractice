package com.dysania.hencoderplus08;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dysania on 3/31/19
 */

public class PointView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Point mPoint = new Point(0, 0);
    
    {
        mPaint.setStrokeWidth(UIUtil.dp2px(15));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        mPoint = point;
        invalidate();
    }

    public PointView(Context context) {
        super(context);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(mPoint.x, mPoint.y, mPaint);
    }
}
