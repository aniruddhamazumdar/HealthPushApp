package com.healthpush.healthpushapp.activity;

import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.Utils;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class ShowInterestsActivity extends ActionBarActivity {

    private Bundle mArgs;
    private SharedPreferences mPrefs;

    private ListView list_interests_seleceted;

    private LayoutInflater mInflater;
    private String[] mSelectedInterests;
    private InterestsAdapter mAdapter;

    private final int REQ_SELECT_INTERESTS = 4242;
    private final int REQ_LOGIN = 2424;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_interests);

        mArgs = getIntent().getExtras();
        mInflater = LayoutInflater.from(this);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP |
                ActionBar.DISPLAY_SHOW_TITLE);

        initControls();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        list_interests_seleceted = (ListView) findViewById(R.id.list_interests_selected);
    }

    private void initData() {
        switch (Utils.getUserState(mPrefs)) {
            case Utils.UserState.INIT:
                showLoginScreen();
                return;
            case Utils.UserState.LOGGED_IN:
                showInterestSelection();
                return;
            case Utils.UserState.SELECTED_INTERESTS:
                showSelectedInterests();
                return;
            default:
                showLoginScreen();
        }
    }

    private void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQ_LOGIN);
    }

    private void showInterestSelection() {
        Intent intent = new Intent(this, SelectInterestsActivity.class);
        startActivityForResult(intent, REQ_SELECT_INTERESTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SELECT_INTERESTS && resultCode == Activity.RESULT_OK) {
            showSelectedInterests();
        }
        else if (requestCode == REQ_LOGIN && resultCode == Activity.RESULT_OK) {
            showInterestSelection();
        }
    }

    private void startInterestDetails(String value) {
        Bundle bundle = new Bundle();
        bundle.putString("category",value);
        Intent intent = new Intent(this, RevealCategory.class);
        intent.putExtra("category_bundle", bundle);
        startActivity(intent);
    }

    private void showSelectedInterests() {
        mSelectedInterests = Utils.getUserInterests(mPrefs);
        // Show user selected interests screen
        mAdapter = new InterestsAdapter();
        list_interests_seleceted.setAdapter(mAdapter);
        list_interests_seleceted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startInterestDetails(mSelectedInterests[position]);
            }
        });
    }



    private class InterestsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSelectedInterests.length;
        }

        @Override
        public Object getItem(int position) {
            return mSelectedInterests[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_selected_interests, parent, false);
            }
            TextView header = (TextView) convertView.findViewById(R.id.item_header);
            TextView text = (TextView) convertView.findViewById(R.id.item_text);

            header.setText(mSelectedInterests[position]);
            return convertView;
        }
    }
}
