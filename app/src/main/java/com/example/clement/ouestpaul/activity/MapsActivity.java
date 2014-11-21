package com.example.clement.ouestpaul.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.location.MyLocationOverlay;
import com.example.clement.ouestpaul.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private boolean menuopen = false;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private MyLocationOverlay loc;
    private Location curLocation;
    private Button button_carte, button_pieton, button_velo, button_voiture;
    LatLng arrivee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        button_carte = (Button) findViewById(R.id.button_carte);
        button_carte.setVisibility(View.INVISIBLE);
        button_pieton = (Button) findViewById(R.id.button_pieton);
        button_velo = (Button) findViewById(R.id.button_velo);
        button_voiture = (Button) findViewById(R.id.button_voiture);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            loc = new MyLocationOverlay(locationManager.getLastKnownLocation("gps"), this);
        } catch (Exception e) {
           // Log.d("test", "Location reçue dans la boucle:marche pas 1 : "+e);
        }
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, loc.getLocationListener());
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, loc.getLocationListener());
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(loc.getLocationListener());
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {

            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
              //  Log.d("test", "Location reçue dans la boucle map pas null : ");
                setUpMap();
            }
        } else if (curLocation != null && loc != null) {
           // Log.d("test", "Location reçue dans la boucle avant : "+curLocation.getLatitude());

            if (!loc.sameLocation(curLocation)) {
                loc.getLocationListener().onLocationChanged(curLocation);
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0F,loc.getLocationListener());
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && loc.enableMyLocation()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, loc.getLocationListener());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, loc.getLocationListener());
     if (curLocation == null)
                curLocation = new Location(LocationManager.GPS_PROVIDER);
           // Log.d("test", "Location reçue dans la boucle:marche pas 1 : ");
            curLocation.setLatitude(loc.getLatitude());
            curLocation.setLongitude(loc.getLongitude());
            mMap.setMyLocationEnabled(true);

            //mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title("Vous êtes ici"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 15.0f));
        } else {
            Toast.makeText(MapsActivity.this, "Veuillez activez votre GPS",
                    Toast.LENGTH_SHORT).show();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.563123, 1.466139), 15.0f));
        }
    }

    public void onClick_button_Recherche(View v) {
        Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onClick_button_SousCarte(View v) {
        String geoUriString = "http://maps.google.com/maps?" +
                "saddr=" + loc.getLatitude()+ "," + loc.getLongitude()  +
                "&daddr=" + arrivee.latitude + "," + arrivee.longitude;
        switch (v.getId()) {
            case R.id.button_pieton:
                geoUriString += "&directionsmode=walking";
                break;
            case R.id.button_velo:
                geoUriString += "&directionsmode=bicycling";
                break;
           /* case R.id.button_voiture:
                geoUriString += "&directionsmode=driving";
                break;*/
        }
        Intent mapCall = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUriString));
        startActivity(mapCall);
    }

    public void onClick_button_Carte(View v) {
        float[] results = {0};
        Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), arrivee.latitude, arrivee.longitude, results);
        if (results[0] < 1750) {
            //Log.d("test", "Location reçue dans la boucle:marche pas 1 : "+results[0]);
            if (!menuopen) {
                button_pieton.setVisibility(View.VISIBLE);
                button_pieton.setEnabled(true);
                button_velo.setVisibility(View.VISIBLE);
                button_velo.setEnabled(true);
                button_voiture.setVisibility(View.VISIBLE);
                button_voiture.setEnabled(true);
                menuopen = true;
            } else {
                button_pieton.setVisibility(View.INVISIBLE);
                button_pieton.setEnabled(false);
                button_velo.setVisibility(View.INVISIBLE);
                button_velo.setEnabled(false);
                button_voiture.setVisibility(View.INVISIBLE);
                button_voiture.setEnabled(false);
                menuopen = false;
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oh non !");
            builder.setMessage("Vous êtes trop loin de l'université. Veuillez vous rapprocher pour avoir un itinéraire");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Add your code for the button here.
                }
            });
// Set the Icon for the Dialog
            //alertDialog.setIcon(R.drawable.icon);
            builder.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
                Lieu mPerson = (Lieu)data.getSerializableExtra("result");
            mMap.clear();
            arrivee = new LatLng(mPerson.getCoordonnee().getX(), mPerson.getCoordonnee().getY());
            mMap.addMarker(new MarkerOptions().position(arrivee).title(mPerson.toString()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrivee, 15.0f));
            button_carte.setVisibility(View.VISIBLE);
            button_carte.setEnabled(true);
        }
    }
}
