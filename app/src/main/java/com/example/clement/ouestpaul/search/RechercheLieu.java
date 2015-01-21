package com.example.clement.ouestpaul.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.clement.ouestpaul.JSONParser;
import com.example.clement.ouestpaul.lieux.Batiment;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.location.Position;
import com.example.clement.ouestpaul.lieux.Salle;
import com.example.clement.ouestpaul.lieux.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.example.clement.ouestpaul.search.RechercheLieu.TypeRecherche.*;

/**
 * Created by Adrien on 09/11/2014.
 */
public class RechercheLieu {
    private static final String TAG_FEATURE = "features";
    private static final String TAG_PROPERTIES = "properties";
    private static final String TAG_ID = "ID";
    private static final String TAG_NOM = "Nom";
    private static final String TAG_LONG = "X";
    private static final String TAG_LAT = "Y";
    private static final String TAG_ACTIVITE = "Activité";
    private static final String TAG_ETABLI = "Etablissements";
    JSONArray user = null;
    private static String url = "https://gist.githubusercontent.com/anonymous/c0611f9df6eec247ee45/raw/1ac8f80694c078395b9aa404e482bfc734e4b9d3/map.geojson";

    private Context lecontext;



    public static enum TypeRecherche {
        /** Type batiment */
        ALL,
        /** Type arret (bus ou métro) */
        NOTHING,
        /** Type service (infirmerie, parking cafeteria, etc...) */
        ETABLIONLY,
        /** Type salle (de cours, TP, amphi, etc...) */
        ACTIVITEONLY
    }

    public RechercheLieu() {
        lieux = new LinkedList<Lieu>();
        JSONParser jParser = new JSONParser();
        try {
            jParser.makeRequest("http://10.0.2.2/apiDev/tracking/addTrack");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                LinkedList<Lieu> lieuxThead = new LinkedList<Lieu>();
                JSONParser jParser = new JSONParser();
                // Getting JSON from URL
                JSONObject json = jParser.getJSONFromUrl(url);
                try {
                    // Getting JSON Array
           user = json.getJSONArray(TAG_FEATURE);
                    int i;

                   for (i = 0; i < user.length() ; i++) {
                      // Log.d("test", "Location reçue dans la boucle: je test json :");
                       JSONObject array = user.getJSONObject(i).getJSONObject(TAG_PROPERTIES);
                       // Storing  JSON item in a Variable
                       String id = array.getString(TAG_ID);
                       String name = array.getString(TAG_NOM);
                       String longitude = array.getString(TAG_LONG);
                       String lat = array.getString(TAG_LAT);
                       String activite = array.getString(TAG_ACTIVITE);
                       String etabli = array.getString(TAG_ETABLI);
                       lieuxThead.push(
                               new Batiment(Integer.valueOf(id), name, new Position(Double.valueOf(lat), Double.valueOf(longitude)), activite, etabli));
                       //  Log.d("test", "Location reçue dans la boucle: je test json :" + id + " - " + name + " - " + longitude + " - " + lat);

                   }
                    setGetLieux(lieuxThead);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            }
        });

t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Batiment b1 = new Batiment(1, "U1", new Position(43.560304, 1.470264));
        Batiment b2 = new Batiment(2, "U2", new Position(43.561322, 1.470526));
        Batiment b8 = new Batiment(8, "3TP2", new Position(43.560965, 1.467803));
        Batiment b9 = new Batiment(9, "U4", new Position(43.562703, 1.469192));
        Batiment b10 = new Batiment(10, "3A", new Position(43.560172, 1.469249));
        Batiment b11 = new Batiment(11, "U3", new Position(43.561961, 1.469963));
        Batiment b12 = new Batiment(12, "1A", new Position(43.56224, 1.467181));
        Batiment b13 = new Batiment(13, "2A", new Position(43.563835, 1.46655));
        Batiment b14 = new Batiment(14, "4A", new Position(43.558419, 1.470171));
        Batiment b15 = new Batiment(15, "1R3", new Position(43.561682, 1.465903));

        Salle s1 = new Salle(1, "Mathis", new Position(43.560304, 1.470264), b1);
        Salle s2 = new Salle(2, "Borel", new Position(43.561322, 1.470526), b2);
        Salle s3 = new Salle(3, "Frenet", new Position(43.561322, 1.470526), b2);
        Salle s4 = new Salle(4, "Vandel", new Position(43.561322, 1.470526), b2);
        Salle s5 = new Salle(5, "De Broglie", new Position(43.561322, 1.470526), b2);
        Salle s6 = new Salle(6, "Denjoy", new Position(43.560304, 1.470264), b1);
        Salle s7 = new Salle(7, "Einstein", new Position(43.560965, 1.467803), b8);
        Salle s9 = new Salle(9, "Baillaud", new Position(43.562703, 1.469192), b9);
        Salle s10 = new Salle(10, "Concorde", new Position(43.562703, 1.469192), b9);
        Salle s15 = new Salle(15, "Shannon", new Position(43.562703, 1.469192), b9);
        Salle s23 = new Salle(23, "Turing", new Position(43.562703, 1.469192), b9);
        Salle s24 = new Salle(24, "Langevin", new Position(43.560172, 1.469249), b10);
        Salle s8 = new Salle(8, "Ampère", new Position(43.560172, 1.469249), b10);
        Salle s11 = new Salle(11, "Cotton", new Position(43.560172, 1.469249), b10);
        Salle s12 = new Salle(12, "Curie", new Position(43.560172, 1.469249), b10);
        Salle s13 = new Salle(13, "Daurat", new Position(43.561961, 1.469963), b11);
        Salle s14 = new Salle(14, "Fermat", new Position(43.56224, 1.467181), b12);
        Salle s25 = new Salle(25, "Stieltjes", new Position(43.56224, 1.467181), b12);
        Salle s20 = new Salle(20, "Maxwell", new Position(43.560965, 1.467803), b8);
        Salle s16 = new Salle(16, "Grignard", new Position(43.563835, 1.46655), b13);
        Salle s18 = new Salle(18, "Le Chatelier", new Position(43.563835, 1.46655), b13);
        Salle s19 = new Salle(19, "Leclerc du Sablon", new Position(43.558419, 1.470171), b14);
        Salle s21 = new Salle(21, "Molliard", new Position(43.558419, 1.470171), b14);
        Salle s22 = new Salle(22, "Schwartz", new Position(43.561682, 1.465903), b15);

        lieux.push(s23);
        lieux.push(s24);
        lieux.push(s25);
        lieux.push(s1);
        lieux.push(s2);
        lieux.push(s3);
        lieux.push(s4);
        lieux.push(s5);
        lieux.push(s6);
        lieux.push(s7);
        lieux.push(s8);
        lieux.push(s9);
        lieux.push(s10);
        lieux.push(s11);
        lieux.push(s12);
        lieux.push(s13);
        lieux.push(s14);
        lieux.push(s15);
        lieux.push(s16);
        lieux.push(s22);
        lieux.push(s18);
        lieux.push(s19);
        lieux.push(s20);
        lieux.push(s21);
    }

    /**********************************************************/
    /** Constructeur par copie
     *
     *  Description : Constructeur par copie
     *  @param _lieux : Lieux
     *  		Lieux à copier
    /**********************************************************/
    public RechercheLieu(final RechercheLieu _lieux) {
        lieux = (LinkedList<Lieu>) _lieux.getLieux();
    }

    /**********************************************************/
    /** Constructeur
     *
     *  Description : Créé un Lieux grâce à une liste de Lieu
     *  @param _lieux : LinkedList<Lieu>
     *  		liste de Lieu à mettre dans le Lieux
    /**********************************************************/
    public RechercheLieu(final LinkedList<Lieu> _lieux) {
        lieux = _lieux;
    }

/********************FIN CONSTRUCTEUR(S)******************************/

    /**********************************************************/
    /** Getter : addLieu
     *
     *  Description : Ajoute un Lieu à la liste des Lieu
     *  @param _l : Lieu
     ***********************************************************/
    public final void addLieu(final Lieu _l) {
        lieux.add(_l);
    }

    public synchronized void setGetLieux(LinkedList<Lieu> l) {
       lieux = l;
    }
    /**********************************************************/
    /** Getter : getLieux
     *
     *  Description : Retourne la liste des Lieu
     *  @return List<Lieu>
     *  		liste des Lieu dans un Lieux
    /**********************************************************/
    public final List<Lieu> getLieux() {
        return lieux;
    }

    public Lieu getLieuWithIdType(int id, String type) {
        for (Lieu l : lieux) {
            if (l.getIdLieu() == id && l.getType().toString().equals(type)) {
                return l;
            }
        }
        return null;
    }

    public final ArrayList<String> getEtablissements() {
        ArrayList<String> ls = new ArrayList<String>();
ls.add("Tous");
        for (Lieu l : lieux) {
            if (!ls.contains(l.getEtablissement()) && l.getEtablissement() != null)
                ls.add(l.getEtablissement());
        }
        Collections.sort(ls);
        return ls;
    }

    public final ArrayList<String> getActivites() {
        ArrayList<String> ls = new ArrayList<String>();
        ls.add("Tous");
        for (Lieu l : lieux) {
            if (!ls.contains(l.getActivite()) && l.getActivite() != null && !l.getActivite().equals("null"))
                ls.add(l.getActivite());
        }
        Collections.sort(ls);
        return ls;
    }
    /**********************************************************/
    /** Getter : getFavorites
     *
     *  Description : Retourne tous les Lieu favoris
     *  @return List<Lieu>
     *  		liste des Lieu favoris
    /**********************************************************/
    public final ArrayList<Lieu> getFavorites() {
        ArrayList<Lieu> ls = new ArrayList<Lieu>();

        for (Lieu l : lieux) {
            if (l.isFavorite()) {
                ls.add(l);
            }
        }
        Collections.sort(ls);
        return ls;
    }

    private TypeRecherche typeOfRechercheA(String _etabli, String _activite) {
        if (_etabli.equals("Tous") && _activite.equals("Tous")) // 1 1
            return NOTHING;
        else if (!_etabli.equals("Tous") && !_activite.equals("Tous")) // 0 0
            return ALL;
        else if (_etabli.equals("Tous") && !_activite.equals("Tous")) // 1 0
            return ACTIVITEONLY;
        return ETABLIONLY; // 0 1
    }

    public ArrayList<Lieu> findByNameRechercheA(String _nom, String _etabli, String _activite) {

        ArrayList<Lieu> ls = new ArrayList<Lieu>();
if (_nom == null)
    _nom = "";
        switch (typeOfRechercheA(_etabli, _activite)) {
            case ALL :
                for (Lieu l : lieux) {
                    if (l.getNom().toLowerCase().contains(_nom.toLowerCase())
                            && l.getEtablissement() != null && l.getActivite() != null
                            && l.getActivite().equals(_activite) && l.getEtablissement().equals(_etabli)) {
                        ls.add(l);
                    }
                }
                break;
            case NOTHING :
                ls.addAll(findByNameBegin(_nom).getLieux());
                break;
            case ETABLIONLY :
                for (Lieu l : lieux) {
                    if (l.getNom().toLowerCase().contains(_nom.toLowerCase())
                            && l.getEtablissement() != null && l.getEtablissement().equals(_etabli)) {
                        ls.add(l);
                    }
                }
                break;
            case ACTIVITEONLY :
                for (Lieu l : lieux) {
                    if (l.getNom().toLowerCase().contains(_nom.toLowerCase())
                           && l.getActivite() != null && l.getActivite().equals(_activite)) {
                        ls.add(l);
                    }
                }
                break;
            default:
                break;
        }
        Collections.sort(ls);
        return ls;
    }
    /**********************************************************/
    /** Getter : getWithoutFavorites
     *
     *  Description : Retourne tous les Lieu non favoris
     *  @return List<Lieu>
     *  		liste des Lieu non favoris
    /**********************************************************/
    public final List<Lieu> getWithoutFavorites() {
        List<Lieu> ls = new LinkedList<Lieu>();

        for (Lieu l : lieux) {
            if (!l.isFavorite()) {
                ls.add(l);
            }
        }
        Collections.sort(ls);
        return ls;
    }

/**********************************************************/
    /** Nom de la méthode : find
     *  Description : Recherche de façon intelligente un lieu
     *  @param 	_nom : String - nom du lieu à trouver
     *  @return Lieux liste des lieux correspondant à la recherche
    /**********************************************************/
    public final RechercheLieu find(final String _nom) {
        return (new RechercheLieu(lieux)).findByNameBegin(_nom);
    }

/**********************************************************/
    /** Nom de la méthode : findByNameBegin
     *  Description : fait une liste de recherche qui
     *  			correspond au mot recherché
     *  @param	_nom : String - nom du lieu à trouver
     *  @return Lieux - liste des lieux correspondant à la recherche
    /**********************************************************/
    public final RechercheLieu findByNameBegin(final String _nom) {
        LinkedList<Lieu> ls = new LinkedList<Lieu>();
        for (Lieu l : lieux) {
            if (l.getNom().toLowerCase().contains(_nom.toLowerCase())) {
                ls.push(l);
            }
        }
        Collections.sort(ls);
        return new RechercheLieu(ls);
    }

    /**********************************************************/
    /** Nom de la méthode : findBatimentById
     *  Description : Recherche un batiment grace a son id
     *  @param 	_id : int - id du batiment à trouver
     *  @return Batiment liste des lieux correspondant à la recherche
    /**********************************************************/
    public final Batiment findBatimentById(final int _id) {
        for (Lieu l : lieux) {
            if (l.getType() == Lieu.TypeLieu.BATIMENT
                    && l.getIdLieu() == _id) {
                return (Batiment) l;
            }
        }
        return null;
    }

    /**********************************************************/
    /** Nom de la méthode : addBatiment
     *  Description : Ajoute un batiment à la liste de lieux
     *  @param 	_id : int - l'id du batiment
     *  @param 	_nom : String - nom de la salle
     *  @param 	_coord : CoordGPS - les coordonnées de la salle
    /**********************************************************/
    protected final void addBatiment(
            final int _id, final String _nom, final Position _coord) {
        lieux.push(new Batiment(_id, _nom, _coord));
    }

    /**********************************************************/
    /** Nom de la méthode : addSalle
     *  Description : Ajoute une salle à la liste de lieux
     *  @param 	_id : int - l'id de la salle
     *  @param 	_nom : String - nom de la salle
     *  @param 	_coord : CoordGPS - les coordonnées de la salle
     *  @param 	_batiment : Batiment - le batiment où se trouve la salle
    /**********************************************************/
    protected final void addSalle(
            final int _id, final String _nom,
            final Position _coord, final Batiment _batiment) {
        lieux.push(new Salle(_id, _nom, _coord, _batiment));
    }

    /**********************************************************/
    /** Nom de la méthode : addService
     *  Description : Ajoute un service à la liste de lieux
     *  @param	_id : int - l'id du service
     *  @param	_nom : String - nom du service
     *  @param	_coord : CoordGPS - les coordonnées du service
     *  @param	_batiment : Batiment - le batiment où se trouve la salle
    /**********************************************************/
    protected final void addService(
            final int _id, final String _nom,
            final Position _coord, final Batiment _batiment) {
        if (_batiment != null) {
            lieux.push(new Service(_id, _nom, _coord, _batiment));
        } else {
            lieux.push(new Service(_id, _nom, _coord));
        }
    }

    /********************VARIABLE(S)**************************************/
    private LinkedList<Lieu> lieux;
/********************FIN VARIABLE(S)**********************************/

}
