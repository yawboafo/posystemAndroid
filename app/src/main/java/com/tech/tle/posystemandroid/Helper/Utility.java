package com.tech.tle.posystemandroid.Helper;

import java.util.Locale;

public class Utility {

    public static String formatDecimal(Double number) {

            return String.format(Locale.getDefault(), "%,.2f", number);
        }

}
