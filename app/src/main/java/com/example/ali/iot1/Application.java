package com.example.ali.iot1;


import android.app.Activity;
import android.provider.Settings;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by ALI on 11/1/2015.
 */
public class Application extends android.app.Application{
    private String android_id ;

    @Override
    public void onCreate() {
        super.onCreate();
        android_id=Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Connection.createConnection(android_id, getBaseContext(), false);

        Connection.getInstance().connect();
    }
}
