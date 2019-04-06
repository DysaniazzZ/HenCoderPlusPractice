package com.dysania.hencodeplus09;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * Created by dysania on 4/6/19
 */

public class MaterialEditText extends AppCompatEditText {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float TEXT_SIZE = UIUtil.sp2px(12);
    private static final float TEXT_MARGIN = UIUtil.dp2px(8);
    private static final int TEXT_HORIZONTAL_OFFSET = (int) UIUtil.dp2px(5);
    private static final int TEXT_VERTICAL_OFFSET = (int) UIUtil.dp2px(18);
    private static final int TEXT_ANIMATION_OFFSET = (int) UIUtil.dp2px(16);

    private float floatingLabelFraction = 0;

    private boolean mUseFloatingLabel;
    private boolean mFloatingLabelShown;
    private ObjectAnimator mObjectAnimator;
    private Rect mBackgroundPadding = new Rect();

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        mUseFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true);
        typedArray.recycle();

        mPaint.setTextSize(TEXT_SIZE);
        onUseFloatingLabelChange();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mUseFloatingLabel) {
                    return;
                }

                if (mFloatingLabelShown && TextUtils.isEmpty(s)) {
                    getAnimator().reverse();
                    mFloatingLabelShown = false;
                } else if (!mFloatingLabelShown && !TextUtils.isEmpty(s)) {
                    getAnimator().start();
                    mFloatingLabelShown = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    private ObjectAnimator getAnimator() {
        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0, 1);
        }

        return mObjectAnimator;
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.mUseFloatingLabel != useFloatingLabel) {
            this.mUseFloatingLabel = useFloatingLabel;
            onUseFloatingLabelChange();
        }
    }

    private void onUseFloatingLabelChange() {
        getBackground().getPadding(mBackgroundPadding);
        if (mUseFloatingLabel) {
            setPadding(mBackgroundPadding.left, (int) (mBackgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), mBackgroundPadding.right, mBackgroundPadding.bottom);
        } else {
            setPadding(mBackgroundPadding.left, mBackgroundPadding.top, mBackgroundPadding.right, mBackgroundPadding.bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + extraOffset, mPaint);
    }
}
