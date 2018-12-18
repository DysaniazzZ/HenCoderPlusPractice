package com.dysania.hencoderplus06.model;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

/**
 * Created by dysania on 12/12/18
 */

public class PageModel {
    @StringRes
    private int titleRes;
    @LayoutRes
    private int layoutRes;

    public PageModel(@StringRes int titleRes, @LayoutRes int layoutRes) {
        this.titleRes = titleRes;
        this.layoutRes = layoutRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getLayoutRes() {
        return layoutRes;
    }
}
