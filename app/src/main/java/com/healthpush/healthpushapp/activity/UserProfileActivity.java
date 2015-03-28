package com.healthpush.healthpushapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.BezelImageView;
import com.healthpush.healthpushapp.common.PractoGsonRequest;
import com.healthpush.healthpushapp.common.Utils;
import com.healthpush.healthpushapp.model.ProfileResponse;
import com.squareup.picasso.Picasso;


/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class UserProfileActivity extends ActionBarActivity implements View.OnClickListener {

    BezelImageView user_image;
    TextView user_name;
    TextView user_score;
    TextView user_bio;
    TextView user_interests;
    LinearLayout user_articles;
    LinearLayout user_locations;
    TextView user_following;
    TextView user_followers;

    SharedPreferences mPrefs;
    String[] mSelectedInterests;

    ProgressDialog mDialog;
    private Button mInterestBtn;
    private Button mNetworkBtn;
    private Button mCheckInBtn;

    String picture;
    String bio;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        picture = mPrefs.getString("PROFILE_PIC", "");
        name = mPrefs.getString("NAME", "");
        bio = mPrefs.getString("BIO", "");

        mSelectedInterests = Utils.getUserInterests(mPrefs);

        Log.d("PICTURE", "Picture in Profile " + picture);

        initControls();

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("loading profile...");
        mDialog.setCancelable(false);

        initData();
    }

    private void initControls() {
        user_image = (BezelImageView) findViewById(R.id.profile_image);
        user_name = (TextView) findViewById(R.id.profile_name);
        user_score = (TextView) findViewById(R.id.user_score);
        user_bio = (TextView) findViewById(R.id.user_bio);
        user_interests = (TextView) findViewById(R.id.user_interests);
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
        user_name.setText(name);
        if (!TextUtils.isEmpty(picture)) {
            Picasso.with(this)
                    .load(picture)
                    .placeholder(R.drawable.doctor_image)
                    .error(R.drawable.doctor_image)
                    .into(user_image);
        }
        user_bio.setText("Software Engg. at Practo");

        mDialog.show();

        PractoGsonRequest<ProfileResponse> request = new PractoGsonRequest<ProfileResponse>(Request.Method.GET,
                "https://6caa58a5.ngrok.com/profile", ProfileResponse.class, "", null,
                new Response.Listener<ProfileResponse>() {
                    @Override
                    public void onResponse(ProfileResponse profile) {
                        mDialog.dismiss();

                        setProfile(profile);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDialog.dismiss();
                Toast.makeText(UserProfileActivity.this, "Oops! Something's not right here, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        HealthPushApplication.getInstance().addToRequestQueue(request, "FEEDS");
    }

    private void setProfile(ProfileResponse profile) {
        if (profile != null) {
            String str = "";
            for (String intr : profile.getInterests()) {
                str += intr + " - ";
            }
            if (str.length() > 0) {
                str = str.substring(0, str.length() - 3);
            }
            user_interests.setText(str);

            user_score.setText(profile.getPoints());
            user_name.setText(profile.getName());

            ((TextView) findViewById(R.id.title1)).setText(profile.getActivities().get(0).getActivity());
            ((TextView) findViewById(R.id.subtitle1)).setText(profile.getActivities().get(0).getPlaceName());

            ((TextView) findViewById(R.id.title2)).setText(profile.getActivities().get(1).getActivity());
            ((TextView) findViewById(R.id.subtitle2)).setText(profile.getActivities().get(1).getPlaceName());
        }
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
