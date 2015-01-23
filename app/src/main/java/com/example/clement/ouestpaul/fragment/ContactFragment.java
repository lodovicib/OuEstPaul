package com.example.clement.ouestpaul.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clement.ouestpaul.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clement on 23/01/2015.
 */
public class ContactFragment extends Fragment {

    private static View v;
    private Spinner objet;
    private Button sendMail;
    private TextView message;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }
        try {
            v = inflater.inflate(R.layout.fragment_contact, container, false);
        } catch (InflateException e) {
            Log.e("MyApp", "Probleme " + e.toString());
        }
        setHasOptionsMenu(true);
        ArrayList<String> listObjet = new ArrayList<String>();
        listObjet.add("Suggestion");
        listObjet.add("Bug");
        listObjet.add("Ajouter/modifier/supprimer une donnée");
        listObjet.add("Autre");

        objet = (Spinner) v.findViewById(R.id.spinnerObjet);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this.getActivity(), android.R.layout.simple_spinner_item, listObjet);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        objet.setAdapter(dataAdapter);

        message = (TextView) v.findViewById(R.id.messageContact);

        sendMail = (Button) v.findViewById(R.id.buttonContact);
        sendMail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (message.getText().length() == 0)
                    Toast.makeText(ContactFragment.this.getView().getContext(), "Veuillez entrer un message",
                            Toast.LENGTH_SHORT).show();
               else
                    sendEmail();
            }
        });

        return v;
    }

    public void sendEmail() {
        String to = "ouestpaul.application@gmail.com";
        String subject = objet.getSelectedItem().toString();
        String msg = message.getText().toString();
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, msg);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choisissez un client de messagerie:"));

        message.setText("");
        objet.setSelection(0);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
}
