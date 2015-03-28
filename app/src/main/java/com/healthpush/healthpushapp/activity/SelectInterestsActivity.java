package com.healthpush.healthpushapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.SimpleSectionedListAdapter;
import com.healthpush.healthpushapp.common.Utils;

import java.util.ArrayList;


/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class SelectInterestsActivity extends ActionBarActivity {

    private String[] mInterests = new String[]{
            "Meditation", "Spa", "Saloon",
            "Gym", "Yoga",
            "Football", "Squash", "Swimming", "Running", "Cycling", "Tennis", "Badminton", "Volleyball", "BasketBall", "Walking",
            "Weight Loss"};
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
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (savedInstanceState != null) {
            mArgs = savedInstanceState;
        } else {
            mArgs = getIntent().getExtras();
        }

        initControls();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_interests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done) {
            showInterestsActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        interest_list = (ListView) findViewById(R.id.interest_list);
        interest_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
    }

    private void populateList() {
        mHeaderPosList.add(0);
        mHeaderPosList.add(3);
        mHeaderPosList.add(5);
        mHeaderPosList.add(15);

        mAdapter = new InterestsAdapter(this);
        SimpleSectionedListAdapter.Section[] sections = new SimpleSectionedListAdapter.Section[4];
        sections[0] = new SimpleSectionedListAdapter.Section(mHeaderPosList.get(0), "WELLNESS AND BEAUTY", 0);
        sections[1] = new SimpleSectionedListAdapter.Section(mHeaderPosList.get(1), "FITNESS", 0);
        sections[2] = new SimpleSectionedListAdapter.Section(mHeaderPosList.get(2), "SPORTS", 0);
        sections[3] = new SimpleSectionedListAdapter.Section(mHeaderPosList.get(3), "PERSONAL GOALS", 0);

        mSectionedAdapter = new SimpleSectionedListAdapter(this, R.layout.section_layout, mAdapter);
        mSectionedAdapter.setSections(sections);

        interest_list.setAdapter(mSectionedAdapter);

        checkSelectedInterests();
    }

    private void checkSelectedInterests() {
        String[] interests = Utils.getUserInterests(mPrefs);
    }

    private void showInterestsActivity() {
        Bundle bundle = new Bundle();
        // Put selected items from list
        SparseBooleanArray checked = interest_list.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            if (!mHeaderPosList.contains(position + 1)) {
                // Add sport if it is checked i.e.) == TRUE!
                if (checked.valueAt(i))
                    selectedItems.add(String.valueOf(mSectionedAdapter.getItem(position)));
            }
        }

        String[] outputStrArr = new String[selectedItems.size()];
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }

        Utils.updateUserState(mPrefs, Utils.UserState.SELECTED_INTERESTS);

        Utils.updateUserInterests(mPrefs, outputStrArr);

        setResult(Activity.RESULT_OK);
        finish();
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
                convertView = mInflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            }
            ((CheckedTextView) convertView).setText(mInterests[position]);
            return convertView;
        }
    }
}
