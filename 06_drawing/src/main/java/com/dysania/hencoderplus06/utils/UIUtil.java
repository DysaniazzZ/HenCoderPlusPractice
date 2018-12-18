package com.dysania.hencoderplus06.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by dysania on 12/12/18
 */

public class UIUtil {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}