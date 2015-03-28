package com.healthpush.healthpushapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.com.healthpush.healthpushapp.entity.FeedResponse.Feed;
import com.healthpush.healthpushapp.com.healthpush.healthpushapp.entity.FeedResponse;
import com.healthpush.healthpushapp.common.PractoGsonRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class ArticlesActivity extends ActionBarActivity {

    private ListView articles_list;
    private FeedsAdapter mAdapter;
    private LayoutInflater mInflater;
    private ArrayList<FeedResponse.Feed> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        articles_list = (ListView) findViewById(R.id.articles_list);
        mInflater = LayoutInflater.from(this);
    }

    private void initData() {

    }

    private void FetchFeeds() {
        PractoGsonRequest<FeedResponse> request = new PractoGsonRequest<FeedResponse>("", FeedResponse.class, "",
            new Response.Listener<FeedResponse>() {
                @Override
                public void onResponse(FeedResponse feed) {
                    mList = feed.feeds;
                    mAdapter = new FeedsAdapter(ArticlesActivity.this, 0);
                    articles_list.setAdapter(mAdapter);
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

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
            Feed feed = mList.get(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.feed_image);
            TextView title = (TextView) convertView.findViewById(R.id.feed_title);
            TextView text = (TextView) convertView.findViewById(R.id.feed_text);
            TextView views = (TextView) convertView.findViewById(R.id.feed_views);
            TextView likes = (TextView) convertView.findViewById(R.id.feed_likes);
            TextView shares = (TextView) convertView.findViewById(R.id.feed_shares);

//            Picasso.with(ArticlesActivity.this)
//                    .load("")
//                    .centerCrop()
//                    .fetch();
            title.setText(feed.title);
            text.setText(Html.fromHtml(feed.desc));

            views.setText("100 views");
            likes.setText("10 likes");
            shares.setText("9 shares");

            return convertView;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }
    }
}
