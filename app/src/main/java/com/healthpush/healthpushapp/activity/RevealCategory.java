package com.healthpush.healthpushapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.Utils;
import com.healthpush.healthpushapp.model.FeedDesc;
import com.healthpush.healthpushapp.request.PractoGsonRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ravikiran on 27/03/15.
 */
public class RevealCategory extends ActionBarActivity {

    private RevealFragment mRevealFragment;
    private LayoutInflater mLayoutInflater;
    private View articleView;
    private LinearLayout mArticleContainer;
    private LinearLayout mVidoesContainer;
    private Bundle args;
    private String mCategory;
    private String[] mSelectedInterests;
    private SharedPreferences mPrefs;
    private View mProgressBarContainer;
    private TextView mArticleHeader;
    private TextView mVideoHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        args = getIntent().getExtras();

        mSelectedInterests = Utils.getUserInterests(mPrefs);

       // mCategory = args.getString("category");

        mLayoutInflater = LayoutInflater.from(this);


        initControls();
        setActionBarSpinner();

    }

    private void setActionBarSpinner(){
        /** Create an array adapter to populate dropdownlist */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, mSelectedInterests);

        /** Enabling dropdown list navigation for the action bar */
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Toast.makeText(getBaseContext(), "You selected : " + mSelectedInterests[itemPosition]  , Toast.LENGTH_SHORT).show();
                loadMap(mSelectedInterests[itemPosition].trim());
                setArticles(mSelectedInterests[itemPosition].trim());
                return false;
            }
        };

        /** Setting dropdown items and item navigation listener for the actionbar */
        getSupportActionBar().setListNavigationCallbacks(adapter, navigationListener);
    }

    private void initControls(){
        mArticleContainer = (LinearLayout) findViewById(R.id.articles_container);
        mVidoesContainer = (LinearLayout) findViewById(R.id.videos_container);
        mProgressBarContainer = findViewById(R.id.progressContainer);
        mArticleHeader = (TextView)findViewById(R.id.article_header);
        mVideoHeader = (TextView)findViewById(R.id.video_header);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setArticles(final String category){
        //getArticles();
        String url = "https://6caa58a5.ngrok.com/api/feed?";
        final ArrayMap<String, String> param = new ArrayMap<String, String>();
        param.put("type", "articles");
        param.put("category", category);

        PractoGsonRequest<FeedDesc> request = new PractoGsonRequest<FeedDesc>(Request.Method.GET,
                url,
                FeedDesc.class,
                null,
                param,
                new Response.Listener<FeedDesc>() {
                    @Override
                    public void onResponse(FeedDesc fitVideos) {

                        int count = 0;
                        for (FeedDesc.Feeds feeds : fitVideos.feed){
                            if (count < 3){
                                View articleView = mLayoutInflater.inflate(R.layout.item_article, mArticleContainer, false);
                                ImageView articleImage =  (ImageView) articleView.findViewById(R.id.article_image);

                                Picasso.with(RevealCategory.this).load(feeds.image).placeholder(getResources().getDrawable(R.drawable.common_signin_btn_icon_normal_dark)).error(getResources().getDrawable(R.drawable.common_signin_btn_icon_normal_dark)).into(articleImage);
                                TextView articleTxt = (TextView) articleView.findViewById(R.id.article_txt);

                                articleTxt.setText(feeds.title);

                                mArticleContainer.addView(articleView);
                                count = mArticleContainer.getChildCount();
                            } else {
                                View articleView = mLayoutInflater.inflate(R.layout.item_digit_article, mArticleContainer, false);
                                TextView articleTxt = (TextView) articleView.findViewById(R.id.count_txt);

                                articleTxt.setText(""+(fitVideos.feed.size() - 3)+"+"+"\n"+"Articles");
                                //   Picasso.with(mContext).load(data.url).into(articleImage);

                                mArticleContainer.addView(articleView);

                                return;
                            }

                        }

                        setVideos(category);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        setVideos(category);
                        mArticleContainer.setVisibility(View.GONE);
                        mArticleHeader.setVisibility(View.GONE);
                    }
                });

        HealthPushApplication.getInstance().addToRequestQueue(request, "Article");


    }

    private void setVideos(String category){
        //getArticles();

        String url = "https://6caa58a5.ngrok.com/api/feed?";
        final ArrayMap<String, String> param = new ArrayMap<String, String>();
        param.put("type", "videos");
        param.put("category",category);

        PractoGsonRequest<FeedDesc> request = new PractoGsonRequest<FeedDesc>(Request.Method.GET,
                url,
                FeedDesc.class,
                null,
                param,
                new Response.Listener<FeedDesc>() {
                    @Override
                    public void onResponse(FeedDesc fitVideos) {

                        int count = 0;
                        for (FeedDesc.Feeds feeds : fitVideos.feed){
                            if (count < 3){
                                View videoView = mLayoutInflater.inflate(R.layout.item_video, mVidoesContainer, false);
                                ImageView videoImage =  (ImageView) videoView.findViewById(R.id.video_image);

                                Picasso.with(RevealCategory.this).load(feeds.image).placeholder(getResources().getDrawable(R.drawable.common_signin_btn_icon_normal_dark)).error(getResources().getDrawable(R.drawable.common_signin_btn_icon_normal_dark)).into(videoImage);

                                mVidoesContainer.addView(videoView);
                                count = mVidoesContainer.getChildCount();
                            } else {
                                View videoView = mLayoutInflater.inflate(R.layout.item_digit_article, mVidoesContainer, false);
                                TextView videoTxt = (TextView) videoView.findViewById(R.id.count_txt);

                                videoTxt.setText(""+(fitVideos.feed.size() - 3)+"+"+"\n"+"Videos");
                                //   Picasso.with(mContext).load(data.url).into(articleImage);

                                mVidoesContainer.addView(videoView);

                                return;
                            }

                        }
                        setCLickListeners();
                        mProgressBarContainer.setVisibility(View.GONE);

                    }
                },

                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mVidoesContainer.setVisibility(View.GONE);
                mVideoHeader.setVisibility(View.GONE);
                mProgressBarContainer.setVisibility(View.GONE);
            }
        });

        HealthPushApplication.getInstance().addToRequestQueue(request, "TAG");
    }

    private void loadMap(String category){
        Bundle bundle = new Bundle();
        bundle.putString("category",category);
        ClinicMapFragment.show(getSupportFragmentManager(), bundle, R.id.map_container);
    }

    private void setCLickListeners(){
        if (mArticleContainer.getChildCount() > 0){
            for(int i = 0 ; i < mArticleContainer.getChildCount(); i++){
                ((View)mArticleContainer.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RevealCategory.this,"test",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


    }


}
