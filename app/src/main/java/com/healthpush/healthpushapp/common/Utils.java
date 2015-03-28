package com.healthpush.healthpushapp.common;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class Utils {

    public static final String BUNDLE_USER_LOGGED_IN = "is_logged_in";

    public static void updateUserInterests(SharedPreferences prefs, String[] selectedInterests) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SELECTED_INTERESTS", getInterestsCSV(selectedInterests));
        editor.commit();
    }

    public static String[] getUserInterests(SharedPreferences prefs) {
        String csv = prefs.getString("SELECTED_INTERESTS", "");
        Log.d("", "Selected Interests " + csv);
        return csv.split(";");
    }

    public static String getInterestsCSV(String[] interests) {
        String csv = "";
        for (String str : interests) {
            csv += str + ";";
        }
        csv = csv.substring(0, csv.length() - 1);
        Log.d("", "Storing selected interests " + csv);
        return csv;
    }

    public static int getUserState(SharedPreferences prefs) {
        return prefs.getInt("USER_STATE", -1);
    }

    public static void updateUserState(SharedPreferences prefs, int userState) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("USER_STATE", userState);
        editor.commit();
    }

    public static class UserState {
        public static final int INIT = 0;
        public static final int LOGGED_IN = 1;
        public static final int SELECTED_INTERESTS = 2;
    }
}
