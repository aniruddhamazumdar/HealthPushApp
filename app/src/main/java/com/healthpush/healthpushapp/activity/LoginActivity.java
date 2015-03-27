package com.healthpush.healthpushapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.impl.TwitterSocialNetwork;
import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.SocialNetworkHandler;
import com.healthpush.healthpushapp.common.Utils;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private static String SOCIAL_NETWORK_TAG = "tag";
    private String TWITTER_API_TOKEN = "WhtfkzyrRkmYF8kXRx8hm7xUR";
    private String TWITTER_API_SECRET = "TkdfEg5vtElhyGdY4I4gBIwbUQ33Sd9El15EzSHOZvSwocs0wm";

    private SharedPreferences mPrefs;

    private Button btn_twitter;
    private TextView tv_skip_login;

    private SocialNetworkManager mSocialNetworkManager;

    private ProgressDialog mProgressDialog;
    private boolean mIsLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPrefs = getPreferences(MODE_PRIVATE);

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
        btn_twitter = (Button) findViewById(R.id.btn_twitter);
        btn_twitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_twitter:
                loginWithTwitter();
                break;
            case R.id.btn_facebook:
                loginWithTwitter();
                break;
            case R.id.btn_quora:
                loginWithTwitter();
                break;
            case R.id.tv_skip_login:
                loginWithTwitter();
                break;
        }
    }

    private void loginWithTwitter() {
        mProgressDialog.show();

        mSocialNetworkManager = (SocialNetworkManager) getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

        if (mSocialNetworkManager == null) {

            mSocialNetworkManager = SocialNetworkManager.Builder.from(this)
                .twitter(TWITTER_API_TOKEN, TWITTER_API_SECRET)
//            .linkedIn(<< LINKED_IN  API TOKEN  >>, << LINKED_IN API TOKEN  >>, "r_basicprofile+rw_nus+r_network+w_messages")
//            .facebook()
//                    .googlePlus()
                .build();
            getSupportFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();

            mSocialNetworkManager.setOnInitializationCompleteListener(new SocialNetworkManager.OnInitializationCompleteListener() {
                @Override
                public void onSocialNetworkManagerInitialized() {

                    SocialNetworkHandler.getInstance().twitterSocialNetwork = mSocialNetworkManager.getTwitterSocialNetwork();

                    if (SocialNetworkHandler.getInstance().twitterSocialNetwork != null
                            && SocialNetworkHandler.getInstance().twitterSocialNetwork.isConnected()) {


                        mProgressDialog.dismiss();
                        // Move to profile page and show data
                        startInterestsActivity();


                    } else {

                        mSocialNetworkManager.getTwitterSocialNetwork().requestLogin(new OnLoginCompleteListener() {
                            @Override
                            public void onLoginSuccess(int socialNetworkID) {
                                mProgressDialog.dismiss();

                                SharedPreferences.Editor editor = mPrefs.edit();
                                editor.putBoolean(Utils.BUNDLE_USER_LOGGED_IN, true);
                                startInterestsActivity();
                            }

                            @Override
                            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                                mProgressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private void loginWithFaceBook() {

    }

    private void loginWithQuora() {

    }

    private void startInterestsActivity() {
        Intent intent = new Intent(this, SelectInterestsActivity.class);
        startActivity(intent);
    }
}
