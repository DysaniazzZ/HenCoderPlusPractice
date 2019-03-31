package com.dysania.hencoderplus08;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dysania on 12/24/18
 */

public class CameraView extends View {
    private static final float AVATAR_SIZE = UIUtil.dp2px(200);

    private Bitmap mBitmap;
    private float mTopFlip = 0;
    private float mBottomFlip = 0;
    private float mFlipRotation = 0;

    private Camera mCamera = new Camera();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mBitmap = getAvatar((int) AVATAR_SIZE);
        mCamera.setLocation(0, 0, UIUtil.getZForCamera());
    }

    public float getTopFlip() {
        return mTopFlip;
    }

    public void setTopFlip(float topFlip) {
        mTopFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return mBottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        mBottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return mFlipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        mFlipRotation = flipRotation;
        invalidate();
    }

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        float leftOffset = cx - AVATAR_SIZE / 2;
        float topOffset = cy - AVATAR_SIZE / 2;

        // 实际绘制是双坐标系，写法比较复杂。所以为了简单就用单坐标系倒序写。

        // 绘制上半部分
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-mFlipRotation);
        mCamera.save();
        mCamera.rotateX(mTopFlip);
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
//        canvas.clipRect(-AVATAR_SIZE / 2, -AVATAR_SIZE / 2, AVATAR_SIZE / 2, 0);
        canvas.clipRect(-AVATAR_SIZE, -AVATAR_SIZE, AVATAR_SIZE, 0);    // 因为涉及到旋转，所以这里需要把切割的范围扩大，原则上不大于原来的 √2 倍，这里为了简便，统一扩大为 2 倍
        canvas.rotate(mFlipRotation);
        canvas.translate(-cx, -cy);
        canvas.drawBitmap(mBitmap, leftOffset, topOffset, mPaint);
        canvas.restore();

        // 绘制下半部分
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-mFlipRotation);
        mCamera.save();
        mCamera.rotateX(mBottomFlip);
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
//        canvas.clipRect(-AVATAR_SIZE / 2, 0, AVATAR_SIZE / 2, AVATAR_SIZE / 2);
        canvas.clipRect(-AVATAR_SIZE, 0, AVATAR_SIZE, AVATAR_SIZE);     // 因为涉及到旋转，所以这里需要把切割的范围扩大，原则上不大于原来的 √2 倍，这里为了简便，统一扩大为 2 倍
        canvas.rotate(mFlipRotation);
        canvas.translate(-cx, -cy);
        canvas.drawBitmap(mBitmap, leftOffset, topOffset, mPaint);
        canvas.restore();
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
