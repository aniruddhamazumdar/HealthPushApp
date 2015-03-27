package com.healthpush.healthpushapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.healthpush.healthpushapp.ui.SimpleSectionedListAdapter;

import java.util.ArrayList;


/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class SelectInterestsActivity extends ActionBarActivity {

    private String[] mInterests = new String[]{
            "Football", "Swimming", "Running",
            "Yoga", "Gym", "Aerobics"};
    private ArrayList<Integer> mHeaderPosList = new ArrayList<>();

    private LayoutInflater mInflater;
    private Bundle mArgs;
    private SharedPreferences mPrefs;

    private ListView interest_list;
    private View user_layout;

    private boolean mIsLoggedIn;
    private InterestsAdapter mAdapter;
    private SimpleSectionedListAdapter mSectionedAdapter;

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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE
                | ActionBar.DISPLAY_HOME_AS_UP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_interests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        if (item.getItemId() == R.id.done) {
            showInterestsActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        interest_list = (ListView) findViewById(R.id.interest_list);
        interest_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

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
//                socialPerson.avatarURL;
//                socialPerson.name;
//                socialPerson.profileURL;
            }

            @Override
            public void onError(int i, String s, String s2, Object o) {

            }
        });
    }

    private void populateList() {
        mHeaderPosList.add(0);
        mHeaderPosList.add(3);

        mAdapter = new InterestsAdapter(this);
        SimpleSectionedListAdapter.Section[] sections = new SimpleSectionedListAdapter.Section[2];
        sections[0] = new SimpleSectionedListAdapter.Section(mHeaderPosList.get(0), "SPORTS", 0);
        sections[1] = new SimpleSectionedListAdapter.Section(mHeaderPosList.get(1), "WELLNESS", 0);

        mSectionedAdapter = new SimpleSectionedListAdapter(this, R.layout.section_layout, mAdapter);
        mSectionedAdapter.setSections(sections);

        interest_list.setAdapter(mSectionedAdapter);
    }

    private void showInterestsActivity() {
        Bundle bundle = new Bundle();
        // Put selected items from list
        SparseBooleanArray checked = interest_list.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            if (!mHeaderPosList.contains(position)) {
                // Add sport if it is checked i.e.) == TRUE!
                if (checked.valueAt(i))
                    selectedItems.add(String.valueOf(mSectionedAdapter.getItem(position)));
            }
        }

        String[] outputStrArr = new String[selectedItems.size()];
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }

        bundle.putStringArray("SELECTED_INTERESTS", outputStrArr);

        Intent intent = new Intent(this, ShowInterestsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
