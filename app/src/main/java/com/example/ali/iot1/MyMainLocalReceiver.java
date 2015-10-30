package com.example.ali.iot1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

/**
 * Created by ALI on 10/26/2015.
 */
public class MyMainLocalReceiver extends BroadcastReceiver {


    private GoogleMap mMap;

    private Marker mMyLocationMarker = null;


    public MyMainLocalReceiver(GoogleMap map) {
        super();
        mMap = map;

    }

    @Override
    public void onReceive(Context context, Intent callerIntent) {
        double latitude = callerIntent.getDoubleExtra("latitude", -1);
        double longitude = callerIntent.getDoubleExtra("longitude", -1);
        String msg = "  lat:  " + Double.toString(latitude) + "   "
                + "  lon:   " + Double.toString(longitude);
        Log.d("MyMainLocalReceiver", "My Receiver : " + msg);
        Geocoder gc = new Geocoder(context, Locale.US);

        this.updateMapLocation(latitude, longitude);
    }


    private void updateMapLocation(double latitude, double longitude) {
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(latitude,
                        longitude));


        mMap.moveCamera(center);

        if (mMyLocationMarker == null) {
            mMyLocationMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My location"));
        } else {
            mMyLocationMarker.setPosition(new LatLng(latitude, longitude));
        }

    }

}
