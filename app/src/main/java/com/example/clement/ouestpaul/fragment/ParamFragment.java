package com.example.clement.ouestpaul.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clement.ouestpaul.R;

/**
 * Created by clement on 19/01/2015.
 */
public class ParamFragment extends Fragment {

    private static View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.fragment_param, null);
        } catch (InflateException e) {
        }

        return v;
    }

    public void onClick_tracking(View v) {

    }

}
