package com.healthpush.healthpushapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.com.healthpush.healthpushapp.entity.FeedResponse;
import com.healthpush.healthpushapp.common.PractoGsonRequest;
import com.healthpush.healthpushapp.common.SocialNetworkHandler;
import com.healthpush.healthpushapp.common.Utils;
import com.healthpush.healthpushapp.model.TokenResponse;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private static String SOCIAL_NETWORK_TAG = "tag";
    private String TWITTER_API_TOKEN = "WhtfkzyrRkmYF8kXRx8hm7xUR";
    private String TWITTER_API_SECRET = "TkdfEg5vtElhyGdY4I4gBIwbUQ33Sd9El15EzSHOZvSwocs0wm";

    private SharedPreferences mPrefs;

    private ProgressDialog mProgressDialog;
    private boolean mIsLoggedIn = false;

    private CallbackManager mCallbackManager;

    private LoginButton facebook_login;

    private static final int REQ_FB_LOGIN = 5050;
    private String name;
    private String picture;
    private String bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCallbackManager = CallbackManager.Factory.create();

        initControls();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("signing in...");
        mProgressDialog.setCancelable(false);

        initData();
    }

    private void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        facebook_login = (LoginButton) findViewById(R.id.facebook_login);
        facebook_login.setReadPermissions("user_friends,public_profile");

        facebook_login.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // Read fb related data here and store locally
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(response.getRawResponse());
                                    name = obj.getString("name");
                                    bio = obj.getString("bio");
                                    JSONObject pic = obj.getJSONObject("picture");
                                    JSONObject data = pic.getJSONObject("data");
                                    picture = data.getString("url");

                                    Log.d("PICTURE ", "FROM JSON OBJECT " + picture);
                                } catch(Exception e) {

                                }

                                // Make an API call to get and store APP TOKEN here
                                sendLoginData(loginResult.getAccessToken().getToken());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,picture,bio");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void startInterestsActivity() {
        Utils.updateUserState(mPrefs, Utils.UserState.LOGGED_IN);
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void sendLoginData(String accessToken) {
        Log.d("TOKEN ", accessToken);
        mProgressDialog.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fb_access_token", accessToken);

        PractoGsonRequest<TokenResponse> request = new PractoGsonRequest<TokenResponse>(Request.Method.POST,
                "https://6caa58a5.ngrok.com/fb_login", TokenResponse.class, "", params,
                new Response.Listener<TokenResponse>() {
                    @Override
                    public void onResponse(TokenResponse object) {
                        mProgressDialog.dismiss();

                        try {

                            Log.d("PICTURE", "PICTURE WHILE LOGGED IN " + picture);

                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("ACCESS_TOKEN", object.getFbId());
                            editor.putString("X_AUTH", object.getXAuthToken());
                            editor.putString("NAME", name);
                            editor.putString("PROFILE_PIC", picture);
                            editor.putString("BIO", bio);
                            editor.commit();
                            HealthPushApplication.X_AUTH_TOKEN = object.getXAuthToken();

                            startInterestsActivity();
                        } catch(Exception e) {
                            Toast.makeText(LoginActivity.this, "Oops! Something's not right here, please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Oops! Something's not right here, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        HealthPushApplication.getInstance().addToRequestQueue(request, "FEEDS");
    }
}
