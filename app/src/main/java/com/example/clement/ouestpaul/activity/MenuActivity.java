package com.example.clement.ouestpaul.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clement.ouestpaul.ExpandableAdapter;
import com.example.clement.ouestpaul.GestionServices;
import com.example.clement.ouestpaul.R;
import com.example.clement.ouestpaul.fragment.ContactFragment;
import com.example.clement.ouestpaul.fragment.LinkFragment;
import com.example.clement.ouestpaul.fragment.MapsFragment;
import com.example.clement.ouestpaul.fragment.ParamFragment;
import com.example.clement.ouestpaul.interfaces.LieuAdapterListener;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.search.ArrayResultsSearchAdaptater;
import com.example.clement.ouestpaul.search.RechercheLieu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Adrien on 11/01/2015.
 */
public class MenuActivity extends ActionBarActivity  implements LieuAdapterListener, SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    private Toolbar toolbar;
    private boolean favorisopen = false;
    private boolean tracking;
    private SharedPreferences prefs, parametres;
    private ListView listResultats, listFavoris, listResultRechercheA;
    private SearchView editRecherche;
    private RechercheLieu listeLieux;
    private ArrayResultsSearchAdaptater adaptater;
    private Map<String, ?> favoris;
    private Spinner rowEtablissement, rowActivite;
    private LinearLayout blocRechercheA;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    private GestionServices listServices;

    private android.support.v4.app.FragmentManager fragmentManager;
    private int pos;
    private ArrayList<Object> childItem = new ArrayList<Object>();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void setChildGroupData() {
        ArrayList<String> child = new ArrayList<String>();
        childItem.add(child);
        childItem.add(listServices.getTypesServices());
        child = new ArrayList<String>();
        childItem.add(child);
        child = new ArrayList<String>();
        childItem.add(child);
        child = new ArrayList<String>();
        childItem.add(child);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listServices = new GestionServices();
        setChildGroupData();

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.expend);
       // mDrawerList.setSelector( R.drawable.select_menu);
        List<String> asd = Arrays.asList(mPlanetTitles);
        ArrayList<String> array = new ArrayList<String>();
        for(String s : asd){
            array.add(s);

    }
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        final ExpandableAdapter mNewAdapter = new ExpandableAdapter(this, array, childItem);
        mNewAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                        this);

        mDrawerList.setAdapter(mNewAdapter);  //, R.layout.drawer_list_item ));

        mDrawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            public void onGroupExpand(int groupPosition) {
                int len = mNewAdapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        mDrawerList.collapseGroup(i);
                    }
                }
            }
        });
       mDrawerList.setOnGroupClickListener(this);
        mDrawerList.setOnChildClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // enable ActionBar app icon to behave as action to toggle nav drawer
        if (toolbar != null)
            setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setIcon();
    toolbar.setLogo(R.drawable.ic_launcher);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar, /*R.drawable.ic_drawer,  nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        listeLieux = new RechercheLieu();
        parametres = getSharedPreferences("parametres", Context.MODE_PRIVATE);
        if (!parametres.contains("autorizeTracking")){
            SharedPreferences.Editor editor = parametres.edit();
            editor.putBoolean("autorizeTracking", true);
            editor.apply();
            tracking = true;
        } else
            tracking = parametres.getBoolean("autorizeTracking", true);
        prefs = getPreferences(Context.MODE_PRIVATE);
        favoris = prefs.getAll();
        if (favoris.size() > 0) {
            for (String key : favoris.keySet()) {
                    Lieu lieu = listeLieux.getLieuWithIdType(Integer.valueOf(key.toString()), (String) favoris.get(key));
                    if (lieu != null)
                        lieu.setFavorite(true);
                }
        }
       // editRecherche = (SearchView) findViewById(R.id.searchView);
        if (savedInstanceState == null) {
            selectItem(0);
        }
        this.listResultats = (ListView) findViewById(R.id.listView);
        this.listFavoris = (ListView) findViewById(R.id.listFavoris);
        listFavoris.setVisibility(View.INVISIBLE);
        listResultats.setVisibility(View.INVISIBLE);
        this.listResultRechercheA = (ListView) findViewById(R.id.listRechercheA);
        blocRechercheA = (LinearLayout) findViewById(R.id.blockRechercheA);
       /* rowEtablissement = (Spinner) findViewById(R.id.spinnerEtablissement);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, listeLieux.getEtablissements());
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        rowEtablissement.setAdapter(dataAdapter);
        // rowEtablissement.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        rowActivite = (Spinner) findViewById(R.id.spinnerActivite);
        dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, listeLieux.getActivites());
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        rowActivite.setAdapter(dataAdapter);*/

    }

     Fragment fragment = new MapsFragment();

    public void onBackPressed() {
        listFavoris.setVisibility(View.INVISIBLE);
        favorisopen = false;
        listResultats.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClickNom(Lieu item, int position) {
        onBackPressed();

        fragmentMap = new MapsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("lieu", item);
        fragmentMap.setArguments(bundle);
        //fragmentManager = getSupportFragmentManager();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, fragmentMap);
        transaction.addToBackStack(null);
        transaction.commit();
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
    public boolean onQueryTextSubmit(String s) {
       // blocRechercheA.setVisibility(View.INVISIBLE);
        lancerRecherche(s);
        return false;
    }

    public boolean onQueryTextChange(String s) {

        if (!s.isEmpty()){
            listResultats.setVisibility(View.VISIBLE);
            listFavoris.setVisibility(View.INVISIBLE);
            //blocRechercheA.setVisibility(View.INVISIBLE);
            lancerRecherche(s);
        } else {
            //blocRechercheA.setVisibility(View.VISIBLE);
            listResultats.setVisibility(View.INVISIBLE);
            adaptater = new ArrayResultsSearchAdaptater(
                    this.getBaseContext(), new ArrayList<Lieu>(),
                    R.layout.affichage_item,
                    new String[] {"titre", "desc", "img" }, new int[] {
                    R.id.titre, R.id.desc, R.id.img });
            listResultats.setAdapter(adaptater);
        }
        return false;
    }

    public void onClick_buttonFavoris() {
        if (favoris.size() > 0 && !favorisopen) {
           // blocRechercheA.setVisibility(View.INVISIBLE);
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
          //  blocRechercheA.setVisibility(View.VISIBLE);
            listFavoris.setVisibility(View.INVISIBLE);
            favorisopen = false;
        } else
            Toast.makeText(this, "Vous n'avez pas de recherches favorites",
                    Toast.LENGTH_SHORT).show();

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
       // menu.findItem(R.id.action_menu).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(R.id.recherche == item.getItemId()) {
            onBackPressed();
        }
            if(R.id.favoris == item.getItemId()) {
                onClick_buttonFavoris();
                //Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
                //startActivity(intent);
            }
                return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onClose() {
        listResultats.setAdapter(adaptater);
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
   //     Toast.makeText(this, "Clicked On Child" + view.getTag(),
     //           Toast.LENGTH_SHORT).show();
        setTitle(mPlanetTitles[i] +" : "+view.getTag().toString());
        view.setSelected(true);
        mDrawerList.setItemChecked(i, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        fragmentMap = new MapsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listService", listServices.getListServiceByName(view.getTag().toString()));

        fragmentMap.setArguments(bundle);
        //fragmentManager = getSupportFragmentManager();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, fragmentMap);
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        if (i != 1)
            selectItem(i);
        view.setSelected(true);
       //mDrawerList.setItemChecked(i, true);
       //mDrawerList.setSelectedGroup(i);
        return false;
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ExpandableListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);

        }
    }

    protected boolean isAlwaysExpanded() {
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        if (pos > 1 || mDrawerLayout.isDrawerOpen(mDrawerList))
            inflater.inflate(R.menu.search, menu);
        else {
            inflater.inflate(R.menu.menu, menu);
            MenuItem searchItem = menu.findItem(R.id.recherche);
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            //ComponentName cn = new ComponentName(this, MenuActivity.class);

           // editRecherche = (SearchView) searchItem.getActionView();
            editRecherche = (SearchView) menu.findItem(R.id.recherche).getActionView();
//            editRecherche.setSearchableInfo(manager.getSearchableInfo(cn));
//            editRecherche.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
           // SearchableInfo searchableInfo = manager.getSearchableInfo(getComponentName());
                editRecherche.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            editRecherche.setOnQueryTextListener(this);
            editRecherche.setOnCloseListener(this);
            if (isAlwaysExpanded()) {
                editRecherche.setIconifiedByDefault(false);
            } else {
                searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                        | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    FragmentTransaction transaction;
Fragment fragmentMap = new MapsFragment();
    private void selectItem(int position) {
        pos = position;
        if (position == 0) {
            tracking = parametres.getBoolean("autorizeTracking", true);
            //fragment = new MapsFragment();
        }
        else if (position == 2)
            fragment = new LinkFragment();
        else if (position == 3)
            fragment = new ParamFragment();
        else if (position == 4)
            fragment = new ContactFragment();
        else
            fragment = new MapsFragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
if (position == 0)
    transaction.replace(R.id.content_frame, fragmentMap);
    else
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
                transaction.commit();
        mDrawerList.setItemChecked(position, true);

        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}