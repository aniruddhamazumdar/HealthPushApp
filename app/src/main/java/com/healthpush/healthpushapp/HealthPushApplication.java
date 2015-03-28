package com.healthpush.healthpushapp;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestTickle;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.VolleyTickle;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

import dev.dworks.libs.actionbarplus.app.ActionBarApplication;

public class HealthPushApplication extends ActionBarApplication {

    public static final String AUTH_HEADER = "X-AUTH-TOKEN";
    public static final String PROFILE_TOKEN_HEADER = "X-PROFILE-TOKEN";
    public static final String FABRIC_TOKEN_HEADER = "X-FABRIC-API-TOKEN";
    public static final String DROID_HEADER = "X-DROID-VERSION";
    public static final String API_VERSION = "API-Version";
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_TYPE_JSON = "application/json";
    public static final String TAG = "FabricVolley";

    public static String APP_VERSION;
    public static int APP_VERSION_CODE;
    private static HealthPushApplication sInstance;

    private RequestTickle mRequestTickle;

    private RequestQueue mRequestQueue;
    private Locale current;

    // Google client reference for enabling auto-suggest
    private GoogleApiClient mClient;

    public static String FB_AUTH_TOKEN = "";
    public static String X_AUTH_TOKEN = "";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        try {
            final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            APP_VERSION = info.versionName;
            APP_VERSION_CODE = info.versionCode;

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            X_AUTH_TOKEN = preferences.getString("X_AUTH", "");

        } catch (NameNotFoundException e) {
            APP_VERSION = "Unknown";
            APP_VERSION_CODE = 0;
            e.printStackTrace();
        }
    }


	public Locale getLocale() {
		if (current == null) {
			current = Locale.US;
		}
		return current;
	}

	public static synchronized HealthPushApplication getInstance() {
		return sInstance;
	}

	public RequestTickle getRequestTickle() {
		if (mRequestTickle == null) {
			mRequestTickle = VolleyTickle.newRequestTickle(getApplicationContext());
		}

		return mRequestTickle;
	}


    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onLowMemory() {
        Runtime.getRuntime().gc();
        super.onLowMemory();
    }

//    public GoogleApiClient getGoogleApiClient() {
//        if (mClient == null) {
//            mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.APP_INDEX_API).build();
//        }
//        return mClient;
//    }

//    // End record page view
//    public void endAppIndexPageView (Activity activity, String appIndexUrl, final String appIndexTitle) {
//        final Uri APP_URI = Utils.BASE_APP_URI.buildUpon().appendPath(appIndexUrl).build();
//        PendingResult<Status> result = AppIndex.AppIndexApi.viewEnd(HealthPushApplication.getInstance().getGoogleApiClient(),
//                activity, APP_URI);
//
//        result.setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(Status status) {
//                if (status.isSuccess()) {
//                    Utils.log(TAG, "App Indexing API: Recorded "
//                            + appIndexTitle + " view end successfully.");
//                } else {
//                    Utils.log(TAG, "App Indexing API: There was an error recording the view."
//                            + status.toString());
//                }
//            }
//        });
//
//        HealthPushApplication.getInstance().getGoogleApiClient().disconnect();
//    }
//
//    // Method to tell google about App's page views
//    // This would enable google auto-suggest to identify our app's pages for suggestions
//    public void recordAppIndexPageView(Activity activity, final String title, String urlPath, String webUrl) {
//        Utils.log("Recording view for " + title + ", " + urlPath + ", " + webUrl);
//        // Connect your client
//        HealthPushApplication.getInstance().getGoogleApiClient().connect();
//
//        // Define a title for your current page, shown in autocompletion UI
//        final Uri APP_URI = Utils.BASE_APP_URI.buildUpon().appendPath(urlPath).build();
//        final Uri WEB_URL = Uri.parse(webUrl);
//
//        // Call the App Indexing API view method
//        PendingResult<Status> result = AppIndex.AppIndexApi.view(HealthPushApplication.getInstance().getGoogleApiClient(), activity,
//                APP_URI, title, WEB_URL, null);
//
//        result.setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(Status status) {
//                if (status.isSuccess()) {
//                    Utils.log(TAG, "App Indexing API: Recorded page view "
//                            + title + " successfully.");
//                } else {
//                    Utils.log(TAG, "App Indexing API: There was an error recording the page view."
//                            + status.toString());
//                }
//            }
//        });
//    }
}