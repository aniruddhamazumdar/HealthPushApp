package com.healthpush.healthpushapp.activity;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.healthpush.healthpushapp.HealthPushApplication;
import com.healthpush.healthpushapp.R;
import com.healthpush.healthpushapp.common.PractoFabricGsonRequest;
import com.healthpush.healthpushapp.model.CategorySliced;

import java.nio.DoubleBuffer;

/**
 * Created by ravikiran on 28/03/15.
 */
public class MapActivity extends ActionBarActivity implements LocationListener {

    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mCategory = getIntent().getExtras().getString("category");

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        String url = "https://6caa58a5.ngrok.com/api/feed";
        final ArrayMap<String, String> param = new ArrayMap<String, String>();
        param.put("type", "location");
        param.put("latlong",""+latitude+","+longitude);
        param.put("category",mCategory);

        PractoFabricGsonRequest<CategorySliced> request = new PractoFabricGsonRequest<CategorySliced>(Request.Method.GET,
                url,
                CategorySliced.class,
                null,
                param,
                new Response.Listener<CategorySliced>() {
                    @Override
                    public void onResponse(CategorySliced categorySlice) {

                        if (categorySlice != null){

                            CategorySliced.Location locations = categorySlice.locations;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("location",locations);

                            ClinicMapFragment.show(getSupportFragmentManager(), bundle, R.id.fragment_container);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        HealthPushApplication.getInstance().addToRequestQueue(request, "TAG");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
