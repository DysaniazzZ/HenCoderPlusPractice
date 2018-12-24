package com.dysania.hencoderplus07.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by dysania on 12/12/18
 */

public class UIUtil {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    public static float getZForCamera() {
        return -6 * Resources.getSystem().getDisplayMetrics().density;
    }
}