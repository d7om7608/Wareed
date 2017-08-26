package com.wadimakkah.d7om7.wareed;

/**
 * Created by Fares on 05/08/17.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationClass {

    protected double lon;
    protected double lat;
    protected double alt;

    private Context mContext;



    public LocationClass(Activity activity){
        mContext = activity;
        LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},5);


        }else{
            Criteria criteria = new Criteria();
            String bestProvider = String.valueOf(manager.getBestProvider(criteria, true)).toString();
            android.location.Location location = manager.getLastKnownLocation(bestProvider);
            if(location != null){
                lon = location.getLongitude();
                lat = location.getLatitude();
                alt = location.getAltitude();
            }else{
                Toast.makeText(mContext,"Failed to get Location",Toast.LENGTH_LONG);
            }
        }



    }

    protected String getCurrectCity(){
        return this.getCity(this.lon,this.lat);
    }
    protected String getCity(double lon,double lat) {
        Geocoder gcd = new Geocoder(mContext, Locale.ENGLISH);
        try {
            List<Address> addresses = gcd.getFromLocation(lat, lon, 1);
            if (addresses.size() > 0)
            {
                return addresses.get(0).getLocality();
            }
            else
            {
                return "";
            }
        }catch (IOException ex){
            return "";
        }

    }

    public double getLongitude(){
        return lon;
    }

    public double getLatitude(){
        return lat;
    }

    public double getAltitude(){
        return alt;
    }
}