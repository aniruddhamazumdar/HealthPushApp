package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
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

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class ShowInterestsActivity extends ActionBarActivity {

    private Bundle mArgs;
    private ListView list_interests_seleceted;
    private LayoutInflater mInflater;
    private String[] mSelectedInterests;
    private InterestsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_interests);

        mArgs = getIntent().getExtras();
        mInflater = LayoutInflater.from(this);

        mSelectedInterests = mArgs.getStringArray("SELECTED_INTERESTS");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP |
                ActionBar.DISPLAY_SHOW_TITLE);

        initControls();
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
        mAdapter = new InterestsAdapter();
        list_interests_seleceted.setAdapter(mAdapter);
        list_interests_seleceted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startInterestDetails(mSelectedInterests[position]);
            }
        });
    }

    private void initData() {

    }

    private void startInterestDetails(String selectedInterest) {
        // Start interest details activity here.
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
