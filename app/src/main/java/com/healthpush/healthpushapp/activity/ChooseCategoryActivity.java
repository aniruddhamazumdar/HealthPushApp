package com.healthpush.healthpushapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.Utils;

/**
 * Created by ravikiran on 28/03/15.
 */
public class ChooseCategoryActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView mCategoryListView;
    private SharedPreferences mPrefs;
    private String[] mCategories;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_category);

        mCategoryListView = (ListView) findViewById(R.id.category_listview);
        mCategoryListView.setOnItemClickListener(this);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCategories = Utils.getUserInterests(mPrefs);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mCategories);

        mCategoryListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        String category = (String) adapter.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putString("category_value",category);

        Intent intent = new Intent(this,ShareActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
