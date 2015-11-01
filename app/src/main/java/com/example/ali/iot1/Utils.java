package com.example.ali.iot1;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by ALI on 10/31/2015.
 */
public class Utils {
    public static String getDeviceId(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Toast.makeText(context, deviceId, Toast.LENGTH_SHORT).show();
        return deviceId;

}
}
