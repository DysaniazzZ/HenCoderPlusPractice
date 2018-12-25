package com.dysania.hencoderplus06.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dysania.hencoderplus06.R;
import com.dysania.hencoderplus06.utils.UIUtil;

/**
 * Created by dysania on 12/16/18
 */

public class AvatarView extends View {
    private static final float AVATAR_RADIUS = UIUtil.dp2px(150);
    private static final float EDGE_SIZE = UIUtil.dp2px(10);

    private float mLeft;
    private float mTop;
    private Bitmap mAvatarBitmap;
    private RectF mSavedArea = new RectF();
    private RectF mEdgeArea = new RectF();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    {
        mAvatarBitmap = getAvatar((int) AVATAR_RADIUS * 2);
//        setLayerType(LAYER_TYPE_HARDWARE, null);    // 开启硬件加速及离屏缓冲（会把整个 View 区域作为缓冲区，建议当只有叠加绘制时使用）
    }

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        mLeft = centerX - AVATAR_RADIUS;
        mTop = centerY - AVATAR_RADIUS;
        float right = centerX + AVATAR_RADIUS;
        float bottom = centerY + AVATAR_RADIUS;

        mSavedArea.set(mLeft, mTop, right, bottom);
        mEdgeArea.set(mLeft - EDGE_SIZE, mTop - EDGE_SIZE, right + EDGE_SIZE, bottom + EDGE_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(mEdgeArea, mPaint);

        int savedLayer = canvas.saveLayer(mSavedArea, mPaint);  // 离屏缓冲
        canvas.drawOval(mSavedArea, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mAvatarBitmap, mLeft, mTop, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(savedLayer);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_dysaniazzz, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_dysaniazzz, options);
    }
}
