package com.cnrylmz.challengemobilist.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Caner on 08.05.2019.
 */

public final class AppUtils {

    private static AppUtils appUtils;

    public static AppUtils getInstance() {
        if (appUtils == null) {
            appUtils = new AppUtils();
        }

        return appUtils;
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
