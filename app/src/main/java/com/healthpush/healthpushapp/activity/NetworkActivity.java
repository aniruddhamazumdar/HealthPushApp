package com.healthpush.healthpushapp.activity;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.com.healthpush.healthpushapp.entity.FeedResponse;
import com.healthpush.healthpushapp.common.PractoGsonRequest;
import com.healthpush.healthpushapp.common.Utils;
import com.healthpush.healthpushapp.model.NetworkFeedItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class NetworkActivity extends ActionBarActivity {

    private Bundle mArgs;
    private SharedPreferences mPrefs;

    private ListView list_interests_seleceted;

    private LayoutInflater mInflater;
    private String[] mSelectedInterests;
    private InterestsAdapter mAdapter;

    private final int REQ_SELECT_INTERESTS = 4242;
    private final int REQ_LOGIN = 2424;

    private ProgressDialog mDialog;

    private ArrayList<NetworkFeedItem> mList = new ArrayList<>();

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

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("loading articles...");
        mDialog.setCancelable(false);

        fillPlaceHolderData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_interests, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        if (item.getItemId() == R.id.action_profile) {
            startProfileActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startProfileActivity() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
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
        } else if (requestCode == REQ_LOGIN && resultCode == Activity.RESULT_OK) {
            showInterestSelection();
        }
    }

    private void startInterestDetails(String value) {
        Bundle bundle = new Bundle();
        bundle.putString("category", value);
        Intent intent = new Intent(this, RevealCategory.class);
        intent.putExtra("category_bundle", bundle);
        startActivity(intent);

//        Intent intent = new Intent(this, ArticlesActivity.class);
//        intent.putExtra("CATEGORY", value);
//        startActivity(intent);
    }

    private void showSelectedInterests() {
//        mSelectedInterests = Utils.getUserInterests(mPrefs);
//        // Show user selected interests screen
//        mAdapter = new InterestsAdapter();
//        list_interests_seleceted.setAdapter(mAdapter);
//        list_interests_seleceted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startInterestDetails(mSelectedInterests[position]);
//            }
//        });

        //API call for networks
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", "articles");

        PractoGsonRequest<FeedResponse> request = new PractoGsonRequest<FeedResponse>(Request.Method.GET,
                "https://6caa58a5.ngrok.com/api/feed", FeedResponse.class, "", params,
                new Response.Listener<FeedResponse>() {
                    @Override
                    public void onResponse(FeedResponse feed) {
                        mDialog.dismiss();

                        mAdapter = new InterestsAdapter();
                        list_interests_seleceted.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDialog.dismiss();
                Toast.makeText(NetworkActivity.this, "Oops! Something's not right here, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        HealthPushApplication.getInstance().addToRequestQueue(request, "FEEDS");
    }


    private class InterestsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_network_list, parent, false);
            }
            TextView user_name = (TextView) convertView.findViewById(R.id.user_name);
            TextView text = (TextView) convertView.findViewById(R.id.text);
            ImageView image = (ImageView) convertView.findViewById(R.id.map_snap);

            View article_layout = convertView.findViewById(R.id.article_layout);
            View video_layout = convertView.findViewById(R.id.video_layout);

            TextView like = (TextView) convertView.findViewById(R.id.like);
            TextView share = (TextView) convertView.findViewById(R.id.share);
            TextView comment = (TextView) convertView.findViewById(R.id.comment);
            TextView likes = (TextView) convertView.findViewById(R.id.likes);
            TextView time = (TextView) convertView.findViewById(R.id.time);

            // Populate data
            NetworkFeedItem item = mList.get(position);
            user_name.setText(item.name);
            if (item.is_map) {

            } else if (item.is_article) {
                article_layout.setVisibility(View.VISIBLE);
            } else if (item.is_video) {
                video_layout.setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }

    private void fillPlaceHolderData() {
        NetworkFeedItem item = new NetworkFeedItem();
        item.is_article = true;
        item.name = "Aniruddha Mazumdar";

        NetworkFeedItem item2 = new NetworkFeedItem();
        item.is_article = true;
        item.name = "RaviKiran S";

        NetworkFeedItem item3 = new NetworkFeedItem();
        item.is_article = true;
        item.name = "Kishore";

        mList.add(item);
        mList.add(item2);
        mList.add(item3);
    }
}
