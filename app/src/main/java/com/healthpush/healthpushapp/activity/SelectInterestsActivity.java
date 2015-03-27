package com.healthpush.healthpushapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidsocialnetworks.lib.SocialPerson;
import com.androidsocialnetworks.lib.listener.OnRequestSocialPersonCompleteListener;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.SocialNetworkHandler;
import com.healthpush.healthpushapp.common.Utils;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class SelectInterestsActivity extends ActionBarActivity {

    private String[] mInterests = new String[]{"Gym", "Football", "Yoga", "Wellness"};

    private LayoutInflater mInflater;
    private Bundle mArgs;
    private SharedPreferences mPrefs;

    private ListView interest_list;
    private View user_layout;

    private boolean mIsLoggedIn;
    private InterestsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_interests);

        mInflater = LayoutInflater.from(this);
        mPrefs = getPreferences(MODE_PRIVATE);

        if (savedInstanceState != null) {
            mArgs = savedInstanceState;
        } else {
            mArgs = getIntent().getExtras();
        }

        initControls();
        initData();
    }

    private void initControls() {
        interest_list = (ListView) findViewById(R.id.interest_list);
        user_layout = findViewById(R.id.user_layout);
    }

    private void initData() {
        mIsLoggedIn = mPrefs.getBoolean(Utils.BUNDLE_USER_LOGGED_IN, false);

        if (mIsLoggedIn) {
            showUserData();
        }

        populateList();
    }

    private void showUserData() {
        SocialNetworkHandler.getInstance().twitterSocialNetwork.requestCurrentPerson(new OnRequestSocialPersonCompleteListener() {
            @Override
            public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {
                socialPerson.avatarURL;
                socialPerson.name;
                socialPerson.profileURL;
            }

            @Override
            public void onError(int i, String s, String s2, Object o) {

            }
        });
    }

    private void populateList() {
        mAdapter = new InterestsAdapter(this);
        interest_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private class InterestsAdapter extends BaseAdapter {

        public InterestsAdapter(Context context) {

        }

        @Override
        public int getCount() {
            return mInterests.length;
        }

        @Override
        public Object getItem(int position) {
            return mInterests[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_interests, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.tv_interest_name)).setText(mInterests[position]);
            return convertView;
        }
    }
}
