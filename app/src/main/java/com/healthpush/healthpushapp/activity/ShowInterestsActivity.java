package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.healthpush.healthpushapp.R;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class ShowInterestsActivity extends ActionBarActivity {

    private Bundle mArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_interests);

        mArgs = getIntent().getExtras();

        String[] selectedInterests = mArgs.getStringArray("SELECTED_INTERESTS");

        initControls();

        findViewById(R.id.tv_placeholder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInterestDetails();
            }
        });
    }

    private void initControls() {

    }

    private void initData() {

    }

    private void startInterestDetails() {
        // Start interest details activity here.
    }
}
