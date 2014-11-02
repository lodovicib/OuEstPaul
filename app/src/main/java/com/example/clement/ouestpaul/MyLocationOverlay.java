package com.example.clement.ouestpaul;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.MapView;

/**
 * Created by Adrien on 02/11/2014.
 */
public class MyLocationOverlay {

    LocationListener loc;
Location location;
    public static final String TAG = "YOUR-TAG-NAME";
/*    public MyLocationOverlay(Context context, MapView carte) {

    }*/

    public MyLocationOverlay(Location _location) {
        location = _location;
        loc = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "Location reçue : lat=" + location.getLatitude() + "/long=" + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                StringBuffer buf = new StringBuffer("Status modifié : dispositif="+s+"/status=");
                switch (i) {
                    case LocationProvider.AVAILABLE:
                        buf.append("En service");
                        Integer t = (Integer)bundle.get("satellites");
                        if (t != null)
                            buf.append("satelittes="+t);
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        buf.append("Hors service");
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        buf.append("Tenporairement indisponible");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d(TAG, "Dispositif activé : "+s);
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d(TAG, "Dispositif désactivé : "+s);
            }
        };
    }

public double getLatitude() {
   return location.getLatitude();
}

    public double getLongitude() {
        return location.getLongitude();
    }
    public boolean enableMyLocation() {
        return true;
    }
}
