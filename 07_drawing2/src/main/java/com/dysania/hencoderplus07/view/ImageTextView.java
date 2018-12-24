package com.dysania.hencoderplus07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dysania.hencoderplus07.R;
import com.dysania.hencoderplus07.utils.UIUtil;

/**
 * Created by dysania on 12/24/18
 */

public class ImageTextView extends View {
    private static final float AVATAR_SIZE = UIUtil.dp2px(100);
    private static final float AVATAR_OFFSET = UIUtil.dp2px(60);
    private static final String TEXT = "Never in all their history have men been able truly to conceive of the world as one: a single sphere, a globe, having the qualities of a globe, a round earth in which all the directions eventually meet, in which there is no center because every point, or none, is center — an equal earth which all men occupy as equals. The airman's earth, if free men make it, will be truly round: a globe in practice, not in theory. Science cuts two ways, of course; its products can be used for both good and evil. But there's no turning back from science. The early warnings about technological dangers also come from science.";

    private Bitmap mBitmap;
    private Paint.FontMetrics mFontMetrics;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mBitmap = getAvatar((int) AVATAR_SIZE);
        mPaint.setTextSize(UIUtil.sp2px(16));
        mFontMetrics = mPaint.getFontMetrics();
    }

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();

        canvas.drawBitmap(mBitmap, viewWidth - AVATAR_SIZE, AVATAR_OFFSET, mPaint);

        int textLength = TEXT.length();
        float verticalOffset = -mFontMetrics.top;
        for (int textStart = 0; textStart < textLength; ) {
            float textWidth;
            float textTop = verticalOffset + mFontMetrics.top;
            float textBottom = verticalOffset + mFontMetrics.bottom;

            if (textTop > AVATAR_OFFSET && textTop < AVATAR_OFFSET + AVATAR_SIZE
                    || textBottom > AVATAR_OFFSET && textBottom < AVATAR_OFFSET + AVATAR_SIZE) {
                // 有图片
                textWidth = viewWidth - AVATAR_SIZE;
            } else {
                // 没有图片
                textWidth = viewWidth;
            }

            int count = mPaint.breakText(TEXT, textStart, textLength, true, textWidth, new float[1]);
            canvas.drawText(TEXT, textStart, textStart + count, 0, verticalOffset, mPaint);
            textStart += count;
            verticalOffset += mPaint.getFontSpacing();
        }
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
