package com.tech.tle.posystemandroid.Helper;

import android.content.res.Resources;
import android.util.TypedValue;

import com.tech.tle.posystemandroid.Application;

/**
 * Created by nykbMac on 27/05/2018.
 */

public class ViewUtils {

    public static int dpToPx(int dp) {
        Resources r = Application.getInstance().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
