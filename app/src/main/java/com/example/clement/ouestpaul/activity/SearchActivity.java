package com.example.clement.ouestpaul.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.clement.ouestpaul.JSONParser;
import com.example.clement.ouestpaul.search.ArrayResultsSearchAdaptater;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.interfaces.LieuAdapterListener;
import com.example.clement.ouestpaul.R;
import com.example.clement.ouestpaul.search.RechercheLieu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
//import com.google.gson.Gson;


import javax.xml.transform.Result;

public class SearchActivity extends Activity implements LieuAdapterListener {
 //   private LinkedList<Lieu> lieux;
//URL to get JSON Array
/* private static String url = "http://geojson.io/#id=gist:icocarto/e9251880dc7b7626258a&map=15/43.5603/1.4703";
    //JSON Node Names
    private static final String TAG_FEATURE = "features";
    private static final String TAG_ID = "ID";
    private static final String TAG_NOM = "Nom";
    private static final String TAG_LONG = "X";
    private static final String TAG_LAT = "Y";
*/
    private ListView listResultats;
    private EditText editRecherche;
   // Map<String, String> hashMapResultats;
   // List<Map<String, String>> myMapList = new ArrayList<Map<String, String>>();
    private RechercheLieu listeLieux;
    private String saisieRecherche;
    ArrayResultsSearchAdaptater adaptater;
    private final static int ID_FAVORIS_DIALOG = 0;
    Dialog box = null;
//    JSONArray user = null;

   public void addCharDansLeChampDeRecherche(char c) {
        editRecherche.setText(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

/*        InputStream source = retrieveStream(url);
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(source);
        SearchResponse response = gson.fromJson(reader, SearchResponse.class);
       // Toast.makeText(this, response.query, Toast.LENGTH_SHORT).show();
        List<Result> results = response.results;
        for (Result result : results) {
           // Toast.makeText(this, result.fromUser, Toast.LENGTH_SHORT).show();
        }

*/
        // Creating new JSON Parser
     /*   JSONParser jParser = new JSONParser();
        // Getting JSON from URL

        JSONObject json = jParser.getJSONFromUrl(url);

        try {
            // Getting JSON Array
            user = json.getJSONArray(TAG_FEATURE);
            JSONObject c = user.getJSONObject(0);
            // Storing  JSON item in a Variable
            String id = c.getString(TAG_ID);
            String name = c.getString(TAG_NOM);
            String longitude = c.getString(TAG_LONG);
            String lat = c.getString(TAG_LAT);
            Log.d("test", "Location reçue dans la boucle: je test json :"+id+" - "+name+" - "+longitude+" - "+lat);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        listeLieux = new RechercheLieu();
        editRecherche = (EditText) findViewById(R.id.searchView);
        //Typeface font = Typeface.createFromAsset(getAssets(),
          //      "Strato-linked.ttf");
        //TextView tv = (TextView) findViewById(R.id.searchView);
        //tv.setTypeface(font);

       /* ((ListView)findViewById(R.id.searchView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View v, int position, long id) {
                // Nous définissons notre intent en lui disant quelle classe il faut utiliser
                Intent detail_article= new Intent(getApplicationContext(),MapsActivity.class);
                // On lui transmet des paramètres, ici la position de l'entry du  feed que l'on voudra ouvrir
                // On peut passer tous les types primitifs (long, int , boolean)
                detail_article.putExtra("title", getTitle());

                // On démarre l'activity
                startActivity(detail_article);
                // On ferme l'activity en cours
                finish();
            }
        }); */

        editRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence s,
                                      final int start, final int before,
                                      final int count) {
                if (editRecherche.getText().toString().equals("")) {
                    adaptater.notifyDataSetChanged();
                } else {

                    saisieRecherche =
                            editRecherche.getText().toString();
                    lancerRecherche(saisieRecherche);
                    adaptater.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                adaptater.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(final CharSequence s,
                                          final int start, final int count,
                                          final int after) {

            }
        });
        this.listResultats = (ListView) findViewById(R.id.listView);
    }

    // fonction de recherche
    public final void lancerRecherche(final String nomRechercher) {
        ArrayList<Lieu> result = new ArrayList<Lieu>();
        //List<Map<String, String>> myMapList = new ArrayList<Map<String, String>>();
        RechercheLieu lieuxFiltre = listeLieux.find(nomRechercher);

        for (Lieu lieu : lieuxFiltre.getLieux()) {
            result.add(lieu);
            //Toast.makeText(this, lieu.isFavorite().toString(), Toast.LENGTH_LONG);
 /*           if(lieu.isFavorite()){
                mapResultat.setImg(R.drawable.ic_star_fav);
            }
            else
            {
                mapResultat.setImg(R.drawable.ic_star);
            }
*/
        }
        adaptater = new ArrayResultsSearchAdaptater(
                this.getBaseContext(), result,
                R.layout.affichage_item,
//                new String[] {"titre", "desc", "img" }, new int[] {
 //               R.id.titre, R.id.desc, R.id.img });
                new String[] {"titre", "desc" }, new int[] {
                R.id.titre, R.id.desc });
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
        Log.d("test", "Location reçue dans la boucle: je suis dans search "+item.getCoordonnee().toString());
        finish();
    }

    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                return null;
            }
            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();
        }
        catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }
        return null;
    }

}
