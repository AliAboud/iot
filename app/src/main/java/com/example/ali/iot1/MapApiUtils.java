package com.example.ali.iot1;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by ALI on 11/2/2015.
 */
public class MapApiUtils {
    public static Address getStreet(Context context, double latitude, double longitude) {

        Geocoder geoCoder = new Geocoder(context);
        List<Address> matches = null;
        Address bestMatch = null;
        try {
            matches = geoCoder.getFromLocation(latitude, longitude, 1);
            bestMatch = (matches.isEmpty() ? null : matches.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bestMatch;
    }

}
