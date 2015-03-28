package com.healthpush.healthpushapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
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
import com.healthpush.healthpushapp.common.BezelImageView;
import com.healthpush.healthpushapp.common.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class UserProfileActivity extends ActionBarActivity implements View.OnClickListener {

    BezelImageView user_image;
    TextView user_name;
    TextView user_score;
    TextView user_bio;
    LinearLayout user_articles;
    LinearLayout user_locations;
    TextView user_following;
    TextView user_followers;

    SharedPreferences mPrefs;
    String[] mSelectedInterests;

    CallbackManager mCallbackManager;

    ProgressDialog mDialog;
    private Button mInterestBtn;
    private Button mNetworkBtn;
    private Button mCheckInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.user_profile);

        mCallbackManager = CallbackManager.Factory.create();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mSelectedInterests = Utils.getUserInterests(mPrefs);

        initControls();

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("loading data...");
        mDialog.setCancelable(false);

        initData();
    }

    private void initControls() {
        user_image = (BezelImageView) findViewById(R.id.profile_image);
        user_name = (TextView) findViewById(R.id.profile_name);
        user_score = (TextView) findViewById(R.id.user_score);
        user_bio = (TextView) findViewById(R.id.user_bio);
        user_articles = (LinearLayout) findViewById(R.id.user_articles);
        user_locations = (LinearLayout) findViewById(R.id.user_locations);
        user_following = (TextView) findViewById(R.id.user_following);
        user_followers = (TextView) findViewById(R.id.user_followers);
        mInterestBtn = (Button) findViewById(R.id.interest_btn);
        mInterestBtn.setOnClickListener(this);
        mNetworkBtn = (Button) findViewById(R.id.network_btn);
        mNetworkBtn.setOnClickListener(this);
        mCheckInBtn = (Button) findViewById(R.id.checkin_btn);
        mCheckInBtn.setOnClickListener(this);
    }

    private void initData() {
        ArrayList<String> perms = new ArrayList<>();
        perms.add("public_profile");

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
                                try {
                                    String photo = object.getString("picture");
                                    String name = object.getString("first_name") + " "
                                            + object.getString("last_name");
                                    String bio = object.getString("bio");

                                    Picasso.with(UserProfileActivity.this)
                                            .load(photo)
                                            .placeholder(R.drawable.doctor_image)
                                            .error(R.drawable.doctor_image)
                                            .into(user_image);
                                    user_name.setText(name);
                                    user_bio.setText(bio);
                                } catch (Exception e) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.interest_btn:
                Intent interestIntent = new Intent(this,RevealCategory.class);
                startActivity(interestIntent);
                finish();
                break;

            case R.id.network_btn:

                Intent networkIntent = new Intent(this,NetworkActivity.class);
                startActivity(networkIntent);
                finish();

                break;

            case R.id.checkin_btn:

                Intent checkInIntent = new Intent(this,CheckInActivity.class);
                startActivity(checkInIntent);
                finish();

                break;
        }
    }
}
