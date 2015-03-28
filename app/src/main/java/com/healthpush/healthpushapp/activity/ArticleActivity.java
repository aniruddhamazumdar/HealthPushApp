package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.model.CategorySliced;
import com.healthpush.healthpushapp.model.FeedDesc;
import com.squareup.picasso.Picasso;

/**
 * Created by ravikiran on 28/03/15.
 */
public class ArticleActivity extends ActionBarActivity {

    private Bundle args;
    private ImageView mDescImage;
    private TextView mDescTitle;
    private TextView mDescTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_desc);

        args = getIntent().getExtras();
        initControl();
        if (args != null){

            CategorySliced.ArticleFeed feedDesc = (CategorySliced.ArticleFeed) args.getSerializable("FEED_DESC");
            if (feedDesc != null){
                setDescription(feedDesc);
            }
        }

    }

    private void initControl(){
        mDescImage = (ImageView) findViewById(R.id.desc_image);
        mDescTitle = (TextView) findViewById(R.id.desc_title);
        mDescTxt = (TextView) findViewById(R.id.desc_txt);
    }


    private void setDescription(CategorySliced.ArticleFeed desc){

        Picasso.with(this).load(desc.image).placeholder(getResources().getDrawable(R.drawable.common_signin_btn_icon_normal_dark)).error(getResources().getDrawable(R.drawable.common_signin_btn_icon_normal_dark)).into(mDescImage);
        mDescTitle.setText(desc.title);
        mDescTxt.setText(desc.desc);

    }
}
