package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.healthpush.healthpushapp.R;

/**
 * Created by ravikiran on 28/03/15.
 */
public class ShareActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mEditText;
    private Button mSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initControls();
    }

    private void initControls(){

        mEditText = (EditText) findViewById(R.id.share_edittxt);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        mSubmitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:




                break;
        }
    }
}
