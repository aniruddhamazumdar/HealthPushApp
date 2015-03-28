package com.healthpush.healthpushapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP |
            ActionBar.DISPLAY_SHOW_TITLE);

        initControls();

        findViewById(R.id.tv_placeholder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInterestDetails();
            }
        });
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

    }

    private void initData() {

    }

    private void startInterestDetails() {
        Intent intent = new Intent(this,RevealCategory.class);
        startActivity(intent);
    }
}
