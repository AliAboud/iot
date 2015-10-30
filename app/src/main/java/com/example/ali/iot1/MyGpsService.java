package com.example.ali.iot1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

/**
 * Created by ALI on 10/26/2015.
 */
public  class  MyGpsService  extends  Service  {

    String  GPS_FILTER  =  "cis493.action.GPS_LOCATION";
    Thread  triggerService;
    LocationManager lm;
    GPSListener   myLocationListener;
    boolean  isRunning  =  true;
    @Override
    public  IBinder  onBind(Intent  arg0)  {
        return  null;
    }

    @Override
    public  void   onCreate()   {
        super.onCreate();
    }

    @Override
    public  void   onStart(Intent  intent,  int  startId)  {
        super.onStart(intent,  startId);
        Log.e("<MyGpsService-onStart>", "I  am   alive-GPS!");

        //   we  place   the  slow  work  of  the   service  in  its  own   thread   so  the
        //   response   we  send  our   caller   who  run   a  "startService(...)"  method
        //   gets  a  quick   OK   from   us.
        triggerService  =  new  Thread(new  Runnable()  {
            public  void  run()  {
                try  {
                    Looper.prepare();
                    //  try  to  get  your  GPS  location  using  the  LOCATION.SERVIVE  provider
                    lm  =  (LocationManager)  getSystemService(Context.LOCATION_SERVICE);
                    //  This  listener  will  catch  and  disseminate  location  updates
                    myLocationListener  =  new  GPSListener(getBaseContext());
                    long  minTime  =  1000;
                    float  minDistance  =  1;
                    lm.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            minTime,
                            minDistance,
                            myLocationListener);
                    Looper.loop();

                }  catch  (Exception  e)  {
                    Log.e("MYGPS",  e.getMessage()  );
                }
            }//  run
        });
        triggerService.start();
    }//  onStart

}
