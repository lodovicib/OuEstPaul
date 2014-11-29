package com.example.clement.ouestpaul.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.clement.ouestpaul.JSONParser;
import com.example.clement.ouestpaul.search.ArrayResultsSearchAdaptater;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.interfaces.LieuAdapterListener;
import com.example.clement.ouestpaul.R;
import com.example.clement.ouestpaul.search.RechercheLieu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;


import javax.xml.transform.Result;

public class SearchActivity extends Activity implements LieuAdapterListener, SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {
    private boolean favorisopen = false;
    private SharedPreferences prefs;
    private ListView listResultats, listFavoris;
    private SearchView editRecherche;
    private RechercheLieu listeLieux;
    private ArrayResultsSearchAdaptater adaptater;
    private Map<String, ?> favoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listeLieux = new RechercheLieu();
        prefs = getPreferences(Context.MODE_PRIVATE);
        favoris = prefs.getAll();
        if (favoris.size() > 0) {
            for (String key : favoris.keySet()) {
                Lieu lieu = listeLieux.getLieuWithIdType(Integer.valueOf(key.toString()), (String) favoris.get(key));
                if (lieu != null)
                    lieu.setFavorite(true);
            }
        }
        editRecherche = (SearchView) findViewById(R.id.searchView);
        editRecherche.setOnQueryTextListener( this);
        editRecherche.setOnCloseListener(this);
        this.listResultats = (ListView) findViewById(R.id.listView);
        this.listFavoris = (ListView) findViewById(R.id.listFavoris);

    }


    public void onClickFavoris(Lieu item, View rowView) {
        SharedPreferences.Editor editeur = prefs.edit();
        Lieu lieu = listeLieux.getLieux().get(listeLieux.getLieux().indexOf(item));
        ImageView iconeView = (ImageView) rowView.findViewById(R.id.img);
        // Log.d("test", "Location reçue dans la boucle: je suis dans search "+lieu.getNom());
        if (lieu.isFavorite()) {
            lieu.setFavorite(false);
            iconeView.setImageResource(R.drawable.ic_star);
            editeur.remove(String.valueOf(lieu.getIdLieu()));
        }
        else {
            lieu.setFavorite(true);
            iconeView.setImageResource(R.drawable.ic_star_fav);
            editeur.putString(String.valueOf(lieu.getIdLieu()), lieu.getType().name());
        }
        editeur.commit();
        favoris = prefs.getAll();
    }

    public final void lancerRecherche(final String nomRechercher) {
        ArrayList<Lieu> result = new ArrayList<Lieu>();
        RechercheLieu lieuxFiltre = listeLieux.find(nomRechercher);

        for (Lieu lieu : lieuxFiltre.getLieux()) {
         result.add(lieu);
        }
        adaptater = new ArrayResultsSearchAdaptater(
                this.getBaseContext(), result,
                R.layout.affichage_item,
                new String[] {"titre", "desc", "img" }, new int[] {
                R.id.titre, R.id.desc, R.id.img });
        adaptater.addListener(this);
        listResultats.setAdapter(adaptater);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickNom(Lieu item, int position) {
        Intent mIntent = new Intent(SearchActivity.this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("result",item);
        mIntent.putExtras(bundle);
        setResult(1,mIntent);
        finish();
    }

    public void onBackPressed() {
        Intent mIntent = new Intent();
        setResult(2,mIntent);
        finish();
    }

    @Override
    public boolean onClose() {
        listResultats.setAdapter(adaptater);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        lancerRecherche(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if (!s.isEmpty()){
            lancerRecherche(s);
        } else {
            adaptater = new ArrayResultsSearchAdaptater(
                    this.getBaseContext(), new ArrayList<Lieu>(),
                    R.layout.affichage_item,
                    new String[] {"titre", "desc", "img" }, new int[] {
                    R.id.titre, R.id.desc, R.id.img });
            listResultats.setAdapter(adaptater);
        }
        return false;
    }

    public void onClick_buttonFavoris(View v) {
        if (favoris.size() > 0 && !favorisopen) {
            adaptater = new ArrayResultsSearchAdaptater(
                    this.getBaseContext(), listeLieux.getFavorites(),
                    R.layout.affichage_item,
                    new String[]{"titre", "desc", "img"}, new int[]{
                    R.id.titre, R.id.desc, R.id.img});
            adaptater.addListener(this);
            listFavoris.setVisibility(View.VISIBLE);
            listFavoris.setAdapter(adaptater);

            favorisopen = true;
        } else if (favorisopen) {
            listFavoris.setVisibility(View.INVISIBLE);
            favorisopen = false;
        } else
            Toast.makeText(SearchActivity.this, "Vous n'avez pas de recherches favorites",
                    Toast.LENGTH_SHORT).show();

    }
}
