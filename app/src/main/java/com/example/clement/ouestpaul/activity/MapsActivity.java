package com.example.clement.ouestpaul.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.location.MyLocationOverlay;
import com.example.clement.ouestpaul.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private boolean menuopen = false;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private Criteria critere;
    private MyLocationOverlay loc;
    private Location curLocation;
    private Button button_carte, button_pieton, button_velo, button_voiture;
   private LatLng arrivee;
    private String best;
private String provider;
    private static long MINDISTANCE = 10;
    private static long MINTIME = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initButton();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        best = locationManager.getBestProvider(critere, true);
        loc = new MyLocationOverlay(locationManager.getLastKnownLocation(best), this);
        if (best.equals("gps"))
            provider = LocationManager.GPS_PROVIDER;
        else
            provider = LocationManager.NETWORK_PROVIDER;
        setUpMapIfNeeded();
    }

    private void initButton() {
        button_carte = (Button) findViewById(R.id.button_carte);
        button_carte.setVisibility(View.INVISIBLE);
        button_pieton = (Button) findViewById(R.id.button_pieton);
        button_velo = (Button) findViewById(R.id.button_velo);
        button_voiture = (Button) findViewById(R.id.button_voiture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        loc = new MyLocationOverlay(locationManager.getLastKnownLocation(best), this);
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(loc.getLocationListener());
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }
                @Override
                public View getInfoContents(Marker arg0) {
                    View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                    String title = arg0.getTitle();
                    String msg = arg0.getSnippet();
                    TextView textTitle = (TextView) v.findViewById(R.id.title);
                    TextView textMsg = (TextView) v.findViewById(R.id.msg);
                    textTitle.setText(title);
                    if (msg == null)
                        textMsg.setVisibility(View.INVISIBLE);
                    else
                        textMsg.setText(Html.fromHtml(msg));
                    return v;
                }
            });
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }// else
           // setUpMap();
            // else if (curLocation != null && loc != null) {
           // Log.d("test", "Location reçue dans la boucle avant : "+curLocation.getLatitude());

           /* if (!loc.sameLocation(curLocation)) {
                loc.getLocationListener().onLocationChanged(curLocation);
                setUpMap();
            }*/
       // }
    }

    private void setUpMap() {
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider, MINTIME, MINDISTANCE, loc.getLocationListener());
            mMap.setMyLocationEnabled(true);
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
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider, MINTIME, MINDISTANCE, loc.getLocationListener());
            Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), arrivee.latitude, arrivee.longitude, results);
            if (results[0] < 1750) {
                if (!menuopen)
                    changedButton(true, View.VISIBLE);
                   else
                    changedButton(false, View.INVISIBLE);
            } else
                alert("Oh non !", "Vous êtes trop loin de l'université. Veuillez vous rapprocher pour avoir un itinéraire");
        }  else
            alert("Oh non !", "Je n'arrive pas à récuperer votre position.");
    }

    private void changedButton(boolean enable, int visibility) {
        button_pieton.setVisibility(visibility);
        button_pieton.setEnabled(enable);
        button_velo.setVisibility(visibility);
        button_velo.setEnabled(enable);
        button_voiture.setVisibility(visibility);
        button_voiture.setEnabled(enable);
        menuopen = enable;
    }

    private void alert(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Add your code for the button here.
            }
        });
// Set the Icon for the Dialog
        //alertDialog.setIcon(R.drawable.icon);
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Lieu mPerson = (Lieu)data.getSerializableExtra("result");
            mMap.clear();
            arrivee = new LatLng(mPerson.getCoordonnee().getX(), mPerson.getCoordonnee().getY());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(arrivee);
            markerOptions.title(mPerson.toString());
            markerOptions.snippet(mPerson.getDesc());
            mMap.addMarker(markerOptions);
           // marker.showInfoWindow();

           // mMap.addMarker(new MarkerOptions().position(arrivee).title(mPerson.toString()).snippet(mPerson.getDesc()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrivee, 15.0f));
            button_carte.setVisibility(View.VISIBLE);
            button_carte.setEnabled(true);
        }
    }
}
