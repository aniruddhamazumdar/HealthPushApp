package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.healthpush.healthpushapp.R;

/**
 * Created by ravikiran on 27/03/15.
 */
public class ChooseFragmentActivity extends ActionBarActivity {

    private static final int ARTICILE_FRAGMENT = 1;
    private static final String ARTICILE_FRAGMENT_CHOICE = "article_bundle";
    private Bundle args;
    private int choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reveal);

        if(savedInstanceState == null){
            args = getIntent().getExtras();
            choice = args.getInt(ARTICILE_FRAGMENT_CHOICE);
        }
        attachFragment(choice);
    }

    private void attachFragment(int choose){
        switch (choose){
            case ARTICILE_FRAGMENT:
                ArticleFragment.show(getSupportFragmentManager(), null, R.id.fragment_container);
                break;
        }
    }
}
