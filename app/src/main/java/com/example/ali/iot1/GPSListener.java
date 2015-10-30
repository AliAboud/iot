package com.example.ali.iot1;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ALI on 10/26/2015.
 */
public class GPSListener implements LocationListener {
    String  GPS_FILTER  =  "cis493.action.GPS_LOCATION";
    Context mContext;
    public GPSListener(Context context){
        mContext= context;
    }
    @Override
    public void onLocationChanged(Location location) {
        double  latitude   =  location.getLatitude();
        double  longitude   =  location.getLongitude();
        Intent  myFilteredResponse  =  new Intent(GPS_FILTER);
        myFilteredResponse.putExtra("latitude",   latitude);
        myFilteredResponse.putExtra("longitude",   longitude);
        Log.e(">>GPS_Service<<", "Lat:" + latitude + "   lon:" + longitude);
        mContext.sendBroadcast(myFilteredResponse);
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
