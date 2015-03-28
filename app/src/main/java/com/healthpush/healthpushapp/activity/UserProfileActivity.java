package com.healthpush.healthpushapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class UserProfileActivity extends ActionBarActivity {

    ImageView user_image;
    TextView user_name;
    TextView user_score;
    LinearLayout user_articles;
    LinearLayout user_locations;
    LinearLayout user_following;

    SharedPreferences mPrefs;

    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.user_profile);

        mCallbackManager = CallbackManager.Factory.create();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        initControls();
    }

    private void initControls() {
        user_image = (ImageView) findViewById(R.id.profile_image);
        user_name = (TextView) findViewById(R.id.profile_name);
        user_score = (TextView) findViewById(R.id.user_score);
        user_articles = (LinearLayout) findViewById(R.id.user_articles);
        user_locations = (LinearLayout) findViewById(R.id.user_locations);
        user_following = (LinearLayout) findViewById(R.id.user_following);
    }

    private void initData() {
        ArrayList<String> perms = new ArrayList<>();
        perms.add("user_profile");

        LoginManager.getInstance().logInWithReadPermissions(this, perms);
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    String photo = object.getString("photo");
                                    String name = object.getString("first_name") + " "
                                            + object.getString("last_name");
                                    

                                } catch(Exception e) {

                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link, photo");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
