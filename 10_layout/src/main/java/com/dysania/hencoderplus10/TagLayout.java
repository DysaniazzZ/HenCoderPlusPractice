package com.dysania.hencoderplus10;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dysania on 4/21/19
 */

public class TagLayout extends ViewGroup {
    private List<Rect> mChildBounds = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthUsed = 0;
        int heightUsed = 0;
        int lineWidthUsed = 0;
        int lineMaxHeight = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
//            measureChildWithMargins(childView, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed); // 测量的时候先不限制宽度，而是测量完后自己计算是否超过 ParentView 的宽度
            if(specMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + childView.getMeasuredWidth() > specWidth) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            
            Rect childBound;
            if(mChildBounds.size() <= i) {
                childBound = new Rect();
                mChildBounds.add(childBound);
            }else {
                childBound = mChildBounds.get(i);
            }
            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + childView.getMeasuredWidth(), heightUsed + childView.getMeasuredHeight());
            
            lineWidthUsed += childView.getMeasuredWidth();
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, childView.getMeasuredHeight());
        }
        
        int width = widthUsed;
        int height = heightUsed + lineMaxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect childBound = mChildBounds.get(i);
            childView.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
