package com.mccollins.shishir.mccollins.splash.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

public final class Utility {
    public static String LOGIN_URL = "http://mccollinsmedia.com/myproject/service/checklogin";
    public static String REGISTER_URL = "http://mccollinsmedia.com/myproject/service/registerUser";
    public static String TOUR_LIST = "http://mccollinsmedia.com/myproject/service/listAttractions";
    public static String UPDATE_USER = "http://mccollinsmedia.com/myproject/service/updateUser";

    private static Utility utility;

    public static Utility getInstance() {
        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }

    private SharedPreferences prefs;

    public void setPrefs(String key, String data, Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, data).apply();
    }

    public String getPrefs(String key, Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, null);
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (cm != null ? cm.getActiveNetworkInfo() : null) != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
