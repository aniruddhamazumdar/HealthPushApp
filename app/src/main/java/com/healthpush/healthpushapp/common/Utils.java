package com.healthpush.healthpushapp.common;

import android.app.Activity;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class Utils {

    public static final String BUNDLE_USER_LOGGED_IN = "is_logged_in";

    public static boolean isActivityAlive(Activity activity) {
        if (null == activity || (null != activity && activity.isFinishing())) {
            return false;
        }
        return true;
    }

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

    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {

        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = now + 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = now + 30 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = cacheHitButRefreshed;
        final long ttl = cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }

    public static class UserState {
        public static final int INIT = 0;
        public static final int LOGGED_IN = 1;
        public static final int SELECTED_INTERESTS = 2;
    }
}
