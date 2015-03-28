package com.healthpush.healthpushapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.healthpush.healthpushapp.R;

/**
 * Created by ravikiran on 28/03/15.
 */
public class CheckInActivity extends ActionBarActivity implements View.OnClickListener {

    private View mShareContainer;
    private View mRecordContainer;
    private Button mProfileBtn;
    private Button mInterest;
    private Button mNetworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        initControls();

    }

    private void initControls(){


        mShareContainer = findViewById(R.id.share_container);
        mShareContainer.setOnClickListener(this);
        mRecordContainer = findViewById(R.id.record_container);
        mRecordContainer.setOnClickListener(this);

        mProfileBtn = (Button) findViewById(R.id.profile_btn);
        mProfileBtn.setOnClickListener(this);
        mInterest = (Button) findViewById(R.id.interest_btn);
        mInterest.setOnClickListener(this);
        mNetworks = (Button) findViewById(R.id.network_btn);
        mNetworks.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_container:
                break;

            case R.id.share_container:
                Intent shareIntent = new Intent(this,ChooseCategoryActivity.class);
                startActivity(shareIntent);

                break;

            case R.id.interest_btn:
                Intent interestIntent = new Intent(this,RevealCategory.class);
                startActivity(interestIntent);
                finish();
                break;

            case R.id.profile_btn:

                Intent profileIntent = new Intent(this,UserProfileActivity.class);
                startActivity(profileIntent);
                finish();

                break;

            case R.id.network_btn:

                Intent networkIntent = new Intent(this,NetworkActivity.class);
                startActivity(networkIntent);
                finish();

                break;
        }
    }
}
