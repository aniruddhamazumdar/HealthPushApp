package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.healthpush.healthpushapp.R;

/**
 * Created by ravikiran on 27/03/15.
 */
public class RevealCategory extends ActionBarActivity {

    private RevealFragment mRevealFragment;
    private ArticleFragment mArticleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reveal);

        if (savedInstanceState == null){
            mArticleFragment = ArticleFragment.show(getSupportFragmentManager(),null,R.id.article_container);

        }

    }


}
