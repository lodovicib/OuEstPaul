package com.example.clement.ouestpaul.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.clement.ouestpaul.R;

/**
 * Created by Adrien on 23/01/2015.
 */
public class LinkFragment extends Fragment {

    private ImageView ups, neo, moodle, intranet;
    private static View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.fragment_link, container, false);
        } catch (InflateException e) {
            Log.e("MyApp", "Probleme " + e.toString());
        }
        ups = (ImageView) v.findViewById(R.id.imgUps);
        ups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link("http://www.univ-tlse3.fr/");
            }
        });
        neo = (ImageView) v.findViewById(R.id.neocampus);
        neo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link("http://www.irit.fr/neocampus/");
            }
        });
        moodle = (ImageView) v.findViewById(R.id.moodle);
        moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link("https://moodle.univ-tlse3.fr/login/index.php");
            }
        });
        intranet = (ImageView) v.findViewById(R.id.imgIntranet);
        intranet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link(" https://cas.ups-tlse.fr/cas/login");
            }
        });
        return v;
    }

    public void link(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
