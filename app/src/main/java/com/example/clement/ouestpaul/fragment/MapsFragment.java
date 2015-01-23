package com.example.clement.ouestpaul.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clement.ouestpaul.JSONParser;
import com.example.clement.ouestpaul.R;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.lieux.Service;
import com.example.clement.ouestpaul.location.MyLocationOverlay;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Adrien on 11/01/2015.
 */
public class MapsFragment extends Fragment {
    private static long MINDISTANCE = 10;
    private static long MINTIME = 10000;
    private static View v;
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
    private Bundle saved;

    /*  @Override
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

  */
    public MapsFragment() {
        super();
        // Just to be an empty Bundle. You can use this later with getArguments().set...
        setArguments(new Bundle());
    }

    /* Initialisation du boutton de lancement de l'itinéraire */
    public void init_button() {
        button_carte = (Button) v.findViewById(R.id.button_carte);
        button_carte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClick_button_Carte(v);
            }
        });
        button_carte.setVisibility(View.INVISIBLE);
    }

    /* Lance un thread qui envoie tous les ..... un json avec le déplacement de l'user */
    public void tracking() {
        final Handler mHandler = new Handler();
        final Runnable toRun = new Runnable() {
            @Override
            public void run() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONParser parser = new JSONParser();
                        try {
                            parser.makeRequest("http:/192.168.1.91:8080/apiDev/tracking/addTrack");
                        }
                        catch(Exception e) {
                            Log.e("Problem", e.getClass() + ": " + e.getMessage());
                        }
                    }
                });
                t.start();
                mHandler.postDelayed(this, 10000);
            }
        };
        mHandler.postDelayed(toRun, 10000);
    }

    public void init_location() {
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        best = locationManager.getBestProvider(critere, true);
        loc = new MyLocationOverlay(locationManager.getLastKnownLocation(best), this);
        if (best.equals("gps"))
            provider = LocationManager.GPS_PROVIDER;
        else
            provider = LocationManager.NETWORK_PROVIDER;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.activity_maps, container, false);
        } catch (InflateException e) {
    /* map is already there, just return view as it is */
        }
        Lieu resultLieu = (Lieu) getArguments().getSerializable("lieu");
        ArrayList<Service> resultServices = (ArrayList<Service>) getArguments().getSerializable("listService");
        saved = savedInstanceState;
        init_button();
         /* tracking(); */
        try {
            if (locationManager == null)
                init_location();
        } catch (Exception e) {
            Log.e("HEY", e.getClass() + " : " + e.getMessage());
        }
        setUpMapIfNeeded();
        /* Vérifie si on doit afficher un marker */
        if (resultLieu != null)
            affMarker(resultLieu);
        else if (resultServices != null)
            affMarkerServices(resultServices);
        return v;
    }

    public void affMarkerServices(ArrayList<Service> resultServices) {
        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        for (Service s : resultServices) {
            markerOptions.position(new LatLng(s.getCoordonnee().getX(), s.getCoordonnee().getY()));
            markerOptions.title(s.getNom());
            //markerOptions.snippet(resultLieu.getDesc());
            mMap.addMarker(markerOptions);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.563123, 1.466139), 15.0f));
    }

    /* Affiche un marker */
    public void affMarker(Lieu resultLieu) {
        mMap.clear();
        arrivee = new LatLng(resultLieu.getCoordonnee().getX(), resultLieu.getCoordonnee().getY());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(arrivee);
        markerOptions.title(resultLieu.toString());
        markerOptions.snippet(resultLieu.getDesc());
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arrivee, 15.0f));
        button_carte.setVisibility(View.VISIBLE);
        button_carte.setEnabled(true);
    }

    /* Vérification si la map est instancier et si on peut mettre à jour les données */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }
                @Override
                public View getInfoContents(Marker arg0) {
                    View v = getLayoutInflater(saved).inflate(R.layout.info_window_layout, null);
                    String title = arg0.getTitle();
                    String msg = arg0.getSnippet();
                    TextView textTitle = (TextView) v.findViewById(R.id.title);
                    TextView textMsg = (TextView) v.findViewById(R.id.msg);
                    textTitle.setText(title);
                    button_carte.setVisibility(View.VISIBLE);
                    button_carte.setEnabled(true);
                    arrivee = arg0.getPosition();
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
        }
    }

    /* MàJ de la map */
    private void setUpMap() {
        if (locationManager.isProviderEnabled(provider)) {
            loc = new MyLocationOverlay(locationManager.getLastKnownLocation(best), this);
            locationManager.requestLocationUpdates(provider, MINTIME, MINDISTANCE, loc.getLocationListener());
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 15.0f));
        } else {
            Toast.makeText(MapsFragment.this.getActivity(), "Veuillez activez votre GPS",
                    Toast.LENGTH_SHORT).show();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.563123, 1.466139), 15.0f));
        }
    }

    /* Si on click sur le boutton "Y aller" */
    public void onClick_button_Carte(View v) {
        float[] results = {0};
        if (locationManager.isProviderEnabled(provider)) {
            loc = new MyLocationOverlay(locationManager.getLastKnownLocation(best), this);
            locationManager.requestLocationUpdates(provider, MINTIME, MINDISTANCE, loc.getLocationListener());
            Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), arrivee.latitude, arrivee.longitude, results);
            if (results[0] < 1750) {
                String geoUriString = "http://maps.google.com/maps?" +
                        "saddr=" + loc.getLatitude()+ "," + loc.getLongitude()  +
                        "&daddr=" + arrivee.latitude + "," + arrivee.longitude;
                Intent mapCall = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUriString));
                startActivity(mapCall);
            } else
                alert("Oh non !", "Vous êtes trop loin de l'université. Veuillez vous rapprocher pour avoir un itinéraire");
        }  else
            alert("Oh non !", "Je n'arrive pas à récuperer votre position.");
    }

    /* Affiche une boite de dialogue */
    private void alert(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

}