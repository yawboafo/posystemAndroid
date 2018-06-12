package com.tech.tle.posystemandroid.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;

import com.tech.tle.posystemandroid.Application;
import com.tech.tle.posystemandroid.R;

/**
 * Created by nykbMac on 27/05/2018.
 */

public class ViewUtils {

    public static int dpToPx(int dp) {
        Resources r = Application.getInstance().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static  void loadFragment(Context context, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment,fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getTag());
        transaction.commit();
    }


    public static  void refreshFragment(Context context, Fragment fragment){

        Fragment currentFragment =  ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());


        FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }


}
