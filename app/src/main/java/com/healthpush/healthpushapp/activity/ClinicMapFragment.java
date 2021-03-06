package com.healthpush.healthpushapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.GooglePlacesReadTask;
import com.healthpush.healthpushapp.common.Utils;
import com.healthpush.healthpushapp.model.CategorySliced;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.dworks.libs.actionbarplus.ActionBarFragment;

/**
 * Created by aniruddhamazumdar on 21/03/15.
 */
public class ClinicMapFragment extends ActionBarFragment implements OnMapReadyCallback {


    private View mRootView;
    private SupportMapFragment mMapFragment;
    private WeakReference mWeakRefRunnable;
    private Handler mHandler;
    private GoogleMap mGoogleMap;
    private String mGeoLatitude;
    private String mGeoLongitude;
    private String category;
    private static final String GOOGLE_API_KEY = "AIzaSyAKxRhxDxgaujTUy2k2taB-uzBUs54uWVc";
    private double latitude;
    private double longitude;
    private int PROXIMITY_RADIUS = 5000;
    private CategorySliced.Location Mappy;

    public static void show(FragmentManager fragManager, Bundle args, int containerId) {
        FragmentTransaction ft = fragManager.beginTransaction();
        ClinicMapFragment fragment = new ClinicMapFragment();
        fragment.setArguments(args);
        ft.replace(containerId, fragment, "map");
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mappy = (CategorySliced.Location)getArguments().getSerializable("location");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUpMapIfNeeded();
        return mRootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }



    private void setUpMapIfNeeded() {
        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        mMapFragment = (SupportMapFragment) mFragmentManager.findFragmentById(R.id.map);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mFragmentManager.beginTransaction().add(R.id.map, mMapFragment).commitAllowingStateLoss();
        }
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            return;
        }
        setUpMap(googleMap);
    }

    private void setUpMap(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;

        for (CategorySliced.Loc articleFeed : Mappy.feed){
            LatLng latLng = new LatLng(Double.parseDouble(articleFeed.location.lat),Double.parseDouble(articleFeed.location.lng));
            drawMarker(latLng);
        }


    }

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        mGoogleMap.addMarker(markerOptions);
    }






}
