package com.healthpush.healthpushapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.com.healthpush.healthpushapp.entity.FeedResponse;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.common.PractoGsonRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class ArticlesActivity extends ActionBarActivity {

    private ListView articles_list;
    private FeedsAdapter mAdapter;
    private LayoutInflater mInflater;
    private ArrayList<FeedResponse.Feed> mList;

    private String mCategory;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        articles_list = (ListView) findViewById(R.id.articles_list);
        mInflater = LayoutInflater.from(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP |
                ActionBar.DISPLAY_SHOW_TITLE);

        mCategory = getIntent().getExtras().getString("CATEGORY");

        mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("loading articles...");
        mDialog.setCancelable(false);

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

    private void initData() {
        FetchFeeds();
    }

    private void FetchFeeds() {
        mDialog.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", "articles");
        params.put("category", mCategory.toLowerCase());

        PractoGsonRequest<FeedResponse> request = new PractoGsonRequest<FeedResponse>(Request.Method.GET,
                "https://6caa58a5.ngrok.com/api/feed", FeedResponse.class, "", params,
            new Response.Listener<FeedResponse>() {
                @Override
                public void onResponse(FeedResponse feed) {
                    mDialog.dismiss();

                    mList = feed.feed;
                    mAdapter = new FeedsAdapter(ArticlesActivity.this, 0);
                    articles_list.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDialog.dismiss();
                Toast.makeText(ArticlesActivity.this, "Oops! Something's not right here, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        HealthPushApplication.getInstance().addToRequestQueue(request, "FEEDS");
    }

    private class FeedsAdapter extends ArrayAdapter<FeedResponse.Feed> {

        public FeedsAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public FeedResponse.Feed getItem(int position) {
            return mList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_feed_list, parent, false);
            }
            FeedResponse.Feed feed = mList.get(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.feed_image);
            TextView title = (TextView) convertView.findViewById(R.id.feed_title);
            TextView text = (TextView) convertView.findViewById(R.id.feed_text);
            TextView views = (TextView) convertView.findViewById(R.id.feed_views);
            TextView likes = (TextView) convertView.findViewById(R.id.feed_likes);
            TextView shares = (TextView) convertView.findViewById(R.id.feed_shares);

            Picasso.with(ArticlesActivity.this)
                    .load(feed.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_progress_image)
                    .error(R.drawable.ic_progress_image)
                    .into(image);

            title.setText(feed.title);
            text.setText(Html.fromHtml(feed.desc));

            views.setText("100 views");
            likes.setText("10 likes");
            shares.setText("9 shares");

            return convertView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
