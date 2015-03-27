package com.healthpush.healthpushapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthpush.healthpushapp.R;

import dev.dworks.libs.actionbarplus.ActionBarFragment;

/**
 * Created by ravikiran on 27/03/15.
 */
public class RevealFragment extends ActionBarFragment {

    public static RevealFragment show(FragmentManager fm, Bundle args,int container) {
        final FragmentTransaction ft = fm.beginTransaction();

        RevealFragment fragment = new RevealFragment();
        fragment.setArguments(args);
        ft.replace(container, fragment);
        ft.commitAllowingStateLoss();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
