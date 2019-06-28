package com.dysania.hencoderplus12;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by dysania on 2019-06-28
 */

public class ScalableImageView extends View {
    private static final float IMAGE_WIDTH = UIUtil.dp2px(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;

    Bitmap mAvatarBitmap;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mOriginalOffsetX;
    private float mOriginalOffsetY;
    private float mOffsetX;
    private float mOffsetY;

    private float mBigScale;
    private float mSmallScale;
    private boolean mIsBigScale;
    private float mCurrentScale;

    private ObjectAnimator mAnimator;
    private OverScroller mScroller;
    private GestureDetectorCompat mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    private SimpleOnGestureListener mSimpleOnGestureListener = new SimpleOnGestureListener();
    private OnScaleGestureListener mOnScaleGestureListener = new OnScaleGestureListener();
    private FlingRunnable mFlingRunnable = new FlingRunnable();

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAvatarBitmap = UIUtil.getAvatar(getResources(), (int) IMAGE_WIDTH);

        mGestureDetector = new GestureDetectorCompat(context, mSimpleOnGestureListener);
        mScaleGestureDetector = new ScaleGestureDetector(context, mOnScaleGestureListener);
        mScroller = new OverScroller(context);
    }

    public float getCurrentScale() {
        return mCurrentScale;
    }

    public void setCurrentScale(float currentScale) {
        mCurrentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        mAnimator.setFloatValues(mSmallScale, mBigScale);

        return mAnimator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mOriginalOffsetX = (getWidth() - IMAGE_WIDTH) / 2f;
        mOriginalOffsetY = (getHeight() - IMAGE_WIDTH) / 2f;

        int width = getWidth();
        int height = getHeight();
        int avatarWidth = mAvatarBitmap.getWidth();
        int avatarHeight = mAvatarBitmap.getHeight();

        if ((float) avatarWidth / avatarHeight > (float) width / height) {
            // 图片宽高比比屏幕大
            mBigScale = (float) height / avatarHeight * OVER_SCALE_FACTOR;
            mSmallScale = (float) width / avatarWidth;
        } else {
            // 图片宽高比比屏幕小
            mBigScale = (float) width / avatarWidth * OVER_SCALE_FACTOR;
            mSmallScale = (float) height / avatarHeight;
        }
        mCurrentScale = mSmallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mScaleFraction = (mCurrentScale - mSmallScale) / (mBigScale - mSmallScale);
        canvas.translate(mOffsetX * mScaleFraction, mOffsetY * mScaleFraction);
        canvas.scale(mCurrentScale, mCurrentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(mAvatarBitmap, mOriginalOffsetX, mOriginalOffsetY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);

        boolean result = mScaleGestureDetector.onTouchEvent(event);
        if (!mScaleGestureDetector.isInProgress()) {
            result = mGestureDetector.onTouchEvent(event);
        }

        return result;
    }

    class SimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
//            return super.onDown(e);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            return super.onScroll(e1, e2, distanceX, distanceY);

            if (mIsBigScale) {
                mOffsetX -= distanceX;
                mOffsetY -= distanceY;
                fixOffsets();
                invalidate();
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            return super.onFling(e1, e2, velocityX, velocityY);

            if (mIsBigScale) {
                mScroller.fling((int) mOffsetX, (int) mOffsetY, (int) velocityX, (int) velocityY,
                        (int) -(mAvatarBitmap.getWidth() * mBigScale - getWidth()) / 2,
                        (int) (mAvatarBitmap.getWidth() * mBigScale - getWidth()) / 2,
                        (int) -(mAvatarBitmap.getHeight() * mBigScale - getHeight()) / 2,
                        (int) (mAvatarBitmap.getHeight() * mBigScale - getHeight()) / 2);

                postOnAnimation(mFlingRunnable);  // 会在绘制下一帧时执行
            }

            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
//            return super.onDoubleTap(e);

            mIsBigScale = !mIsBigScale;
            if (mIsBigScale) {
                mOffsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * mBigScale / mSmallScale;
                mOffsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * mBigScale / mSmallScale;
                fixOffsets();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }
    }

    class OnScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        private float mInitScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mCurrentScale = mInitScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mInitScale = mCurrentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    class FlingRunnable implements Runnable {

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                mOffsetX = mScroller.getCurrX();
                mOffsetY = mScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    private void fixOffsets() {
        mOffsetX = Math.min(mOffsetX, (mAvatarBitmap.getWidth() * mBigScale - getWidth()) / 2);
        mOffsetX = Math.max(mOffsetX, -(mAvatarBitmap.getWidth() * mBigScale - getWidth()) / 2);
        mOffsetY = Math.min(mOffsetY, (mAvatarBitmap.getHeight() * mBigScale - getHeight()) / 2);
        mOffsetY = Math.max(mOffsetY, -(mAvatarBitmap.getHeight() * mBigScale - getHeight()) / 2);
    }
}
