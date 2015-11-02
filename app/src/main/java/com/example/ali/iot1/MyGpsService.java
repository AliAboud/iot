package com.example.ali.iot1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

/**
 * Created by ALI on 10/26/2015.
 */
public class MyGpsService extends Service {

    String GPS_FILTER = "cis493.action.GPS_LOCATION";
    Thread triggerService;
    LocationManager lm;
    GPSListener myLocationListener;
    boolean isRunning = true;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("<MyGpsService-onStart>", "I  am   alive-GPS!");

        //   we  place   the  slow  work  of  the   service  in  its  own   thread   so  the
        //   response   we  send  our   caller   who  run   a  "startService(...)"  method
        //   gets  a  quick   OK   from   us.
        triggerService = new Thread(new Runnable() {
            public void run() {
                getGPSFix_Version1(); // coarse: network based
                getGPSFix_Version2(); // fine: gps-chip based
            }// run
        });
        triggerService.start();
    }//  onStart

    public void getGPSFix_Version1() {
// Get a location as soon as possible
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
// work with best available provider
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
// capture location data sent by current provider
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
// assemble data bundle to be broadcasted
            Intent intentFilteredResponse = new Intent(GPS_FILTER);
            intentFilteredResponse.putExtra("latitude", latitude);

            intentFilteredResponse.putExtra("longitude", longitude);
            intentFilteredResponse.putExtra("provider", provider);
            Log.e(">>GPS_Service<<", provider + " =>Lat:" + latitude
                    + " lon:" + longitude);
// send the location data out
            sendBroadcast(intentFilteredResponse);
        }
    }


    public void getGPSFix_Version2() {
        try {
// using: GPS_PROVIDER
// more accuracy but needs to see the sky for satellite fixing
            Looper.prepare();
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
// This listener will catch and disseminate location updates
            myLocationListener = new GPSListener(getBaseContext());
// define update frequency for GPS readings
            long minTime = 10*1000; // best time: 5*60*1000 (5min)
            float minDistance = 5; // 5 meters
// request GPS updates
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    myLocationListener);
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
