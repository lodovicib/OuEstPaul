package com.example.clement.ouestpaul.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.clement.ouestpaul.R;

/**
 * Created by clement on 19/01/2015.
 */
public class ParamFragment extends Fragment {

    private SharedPreferences dataParam;
    private SharedPreferences.Editor editor;
    private Switch track;
    private static View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.fragment_param, container, false);
        } catch (InflateException e) {
            Log.e("MyApp", "Probleme "+e.toString());
        }
        dataParam = this.getActivity().getSharedPreferences("parametres", Context.MODE_PRIVATE);
        editor = dataParam.edit();
        track = (Switch) v.findViewById(R.id.switch1);
        track.setChecked(dataParam.getBoolean("autorizeTracking", true));
        track.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
              if(isChecked)
                  editor.putBoolean("autorizeTracking", true);
              else
                  editor.putBoolean("autorizeTracking", false);
                editor.apply();
            }
          });
        return v;
    }

}
