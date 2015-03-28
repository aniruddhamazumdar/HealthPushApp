package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.model.Article;

import java.util.ArrayList;

/**
 * Created by ravikiran on 27/03/15.
 */
public class RevealCategory extends ActionBarActivity {

    private RevealFragment mRevealFragment;
    private ArticleFragment mArticleFragment;
    private ArrayList<Article.ArticleData> lists;
    private LayoutInflater mLayoutInflater;
    private View articleView;
    private LinearLayout mArticleContainer;
    private LinearLayout mVidoesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        mLayoutInflater = LayoutInflater.from(this);
        initControls();
        loadMap();
        setArticles();
        setVideos();
    }

    private void initControls(){
        mArticleContainer = (LinearLayout) findViewById(R.id.articles_container);
        mVidoesContainer = (LinearLayout) findViewById(R.id.videos_container);

    }

    private void setArticles(){
        //getArticles();
        lists = new ArrayList<Article.ArticleData>();
        Article.ArticleData articleData = new Article.ArticleData();
        articleData.name = "kira";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi1";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi2";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi3";
        int count = 0;
        for (Article.ArticleData data : lists){
            if (count < 3){
                View articleView = mLayoutInflater.inflate(R.layout.item_article, mArticleContainer, false);
                ImageView articleImage =  (ImageView) articleView.findViewById(R.id.article_image);
                TextView articleTxt = (TextView) articleView.findViewById(R.id.article_txt);

                articleTxt.setText(data.name);
                //   Picasso.with(mContext).load(data.url).into(articleImage);

                mArticleContainer.addView(articleView);
                count = mArticleContainer.getChildCount();
            } else {
                View articleView = mLayoutInflater.inflate(R.layout.item_digit_article, mArticleContainer, false);
                TextView articleTxt = (TextView) articleView.findViewById(R.id.article_txt);

                articleTxt.setText(""+(lists.size() - 3)+"\n"+"Articles");
                //   Picasso.with(mContext).load(data.url).into(articleImage);

                mArticleContainer.addView(articleView);

                return;
            }

        }

    }

    private void setVideos(){
        //getArticles();
        lists = new ArrayList<Article.ArticleData>();
        Article.ArticleData articleData = new Article.ArticleData();
        articleData.name = "kira";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi1";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi2";
        lists.add(articleData);
        articleData = new Article.ArticleData();
        articleData.name = "ravi3";
        int count = 0;
        for (Article.ArticleData data : lists){
            if (count < 3){
                View articleView = mLayoutInflater.inflate(R.layout.item_article, mVidoesContainer, false);
                ImageView articleImage =  (ImageView) articleView.findViewById(R.id.article_image);
                TextView articleTxt = (TextView) articleView.findViewById(R.id.article_txt);

                articleTxt.setText(data.name);
                //   Picasso.with(mContext).load(data.url).into(articleImage);

                mVidoesContainer.addView(articleView);
                count = mVidoesContainer.getChildCount();
            } else {
                View articleView = mLayoutInflater.inflate(R.layout.item_digit_article, mArticleContainer, false);
                TextView articleTxt = (TextView) articleView.findViewById(R.id.article_txt);

                articleTxt.setText(""+(lists.size() - 3)+"\n"+"Articles");
                //   Picasso.with(mContext).load(data.url).into(articleImage);

                mVidoesContainer.addView(articleView);

                return;
            }

        }

    }

    private void loadMap(){
        Bundle bundle = new Bundle();
        bundle.putString("category","swimming");
        ClinicMapFragment.show(getSupportFragmentManager(),bundle,R.id.map_container);
    }


}
