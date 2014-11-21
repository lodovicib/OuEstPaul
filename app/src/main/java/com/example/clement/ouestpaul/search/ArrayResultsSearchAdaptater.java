package com.example.clement.ouestpaul.search;

/**
 * Created by Adrien on 09/11/2014.
 */

/***********************************************************************************/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.R;
import com.example.clement.ouestpaul.interfaces.LieuAdapterListener;

import java.util.ArrayList;
import java.util.List;

/** Nom de la classe : ArrayLigneAdapter
 * Hérite de SimpleAdapter
 * Description : Adaptateur personnalisé  pour gérer une liste de lignes
 * Variable(s) :
 * 		mLignes : Liste de Lignes
 * 		mInflater : objet permettant la création d’un objet View
 * Constructeur(s) :
 * 		ArrayLigneAdapterontext context, ArrayList<Ligne> data,
 int resource, String[] from, int[] to) :
 Redéfinit le constructeur parent pour une liste de Ligne
 * Liste des méthodes :
 * 		View getView(int position, View convertView, ViewGroup parent)
 * 		int getCount()
 /***********************************************************************************/

public class ArrayResultsSearchAdaptater extends BaseAdapter {

    /********************VARIABLE(S)**************************************/
    /**************
     * Liste de Lignes
     /*************/
    private List<Lieu> mLignes;
    /**************
     * Objet permettant la création d’un objet View
     /*************/
    private LayoutInflater mInflater;

    private Context context;
    /********************FIN VARIABLE(S)**************************************/

    /********************CONSTRUCTEUR(S)**********************************/
    /****************/
    @SuppressWarnings("unchecked")
    public ArrayResultsSearchAdaptater(final Context _context, final ArrayList<Lieu> data,
                             final int resource, final String[] from, final int[] to) {
       // super(context, data, resource, from, to);
        //Log.d("test", "Location reçue dans la boucle: je suis dans lancerrecherche hash "+ data.toArray());
       // mLignes = new ArrayList<ResultsSearch>();
       /* Iterator<? extends Map<String, ?>> it = data.iterator() ;
        while (it.hasNext()) {
            HashMap<String, String> h = (HashMap<String, String>) it.next();
              for(String key: h.keySet()) {
                  mLignes.add(new ResultsSearch(key, h.get(key)));
             }

        }*/
        context = _context;
        mLignes = data;
        mInflater = LayoutInflater.from(context);
    }
    /********************FIN CONSTRUCTEUR(S)******************************/

    /**********************************************************/
    /** Nom de la méthode : getView
     *  Description : Personnalise la vue associée à un item
     *  @param position (position de l'item)
     *  @param convertView (vue associée)
     *  @param parent (vue parent)
     *  @return rowView
    /**********************************************************/
    public final View getView(final int position,
                              final View convertView, final ViewGroup parent) {

        View rowView = mInflater.inflate(R.layout.affichage_item, null);
        Lieu ligne = mLignes.get(position);
        TextView nameView = (TextView) rowView.findViewById(R.id.titre);
        nameView.setText(ligne.toString());
       // TextView sizeView = (TextView) rowView.findViewById(R.id.desc);
       // sizeView.setText(String.valueOf(ligne.getDesc()));
  //      ImageView iconeView = (ImageView) rowView.findViewById(R.id.img);
  //     iconeView.setImageResource(ligne.getImg());

        //------------ Début de l'ajout -------
//On mémorise la position de la "Personne" dans le composant textview
        nameView.setTag(position);
//On ajoute un listener
        nameView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                Integer position = (Integer) v.getTag();

                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                sendListener(mLignes.get(position), position);

            }
        });

            return rowView;
    }
    /**********************************************************/
    /** Nom de la méthode : getCount
     *  Description : Renvoie la taille de la liste
     *  @return int
    /**********************************************************/
    public final int getCount() {
        return mLignes.size();
    }

    @Override
    public Object getItem(int i) {
        return mLignes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**********************************************************/
    /** Nom de la méthode : getItemAtPosition
     *  Description : Renvoie la ligne demandée
     *  @param position (numéro de l'élément de la liste)
     *  @return Ligne
    /**********************************************************/
    public final Lieu getItemAtPosition(final int position) {
        return mLignes.get(position);
    }

    //Contient la liste des listeners
    private ArrayList<LieuAdapterListener> mListListener = new ArrayList<LieuAdapterListener>();
    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(LieuAdapterListener aListener) {
        mListListener.add(aListener);
    }

    private void sendListener(Lieu item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickNom(item, position);
        }
    }
}

