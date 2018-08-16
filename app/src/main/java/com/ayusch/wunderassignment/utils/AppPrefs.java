package com.ayusch.wunderassignment.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {
    private static SharedPreferences sharedPreferences;
    private static final String PREF_FILE = "wunder_prefs";

    private static final String SHOULD_SHOW_TUTORIAL = "should_show_tutorial";
    private static final String IS_PERSISTING_DATA = "is_persisting_data";

    public static void initPref(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    public static void setShouldShowTutorial(boolean shouldShowTutorial) {
        sharedPreferences.edit().putBoolean(SHOULD_SHOW_TUTORIAL, shouldShowTutorial).apply();
    }

    public static Boolean getShouldShowTutorial() {
        return sharedPreferences.getBoolean(SHOULD_SHOW_TUTORIAL, true);
    }


    public static void setIsPersistingData(boolean isPersistingData) {
        sharedPreferences.edit().putBoolean(IS_PERSISTING_DATA, isPersistingData).apply();
    }

    public static Boolean getIsPersistingData() {
        // not saving data by default
        return sharedPreferences.getBoolean(IS_PERSISTING_DATA, false);
    }

}
