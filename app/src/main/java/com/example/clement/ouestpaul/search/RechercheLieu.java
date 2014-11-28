package com.example.clement.ouestpaul.search;

import com.example.clement.ouestpaul.lieux.Batiment;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.location.Position;
import com.example.clement.ouestpaul.lieux.Salle;
import com.example.clement.ouestpaul.lieux.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Adrien on 09/11/2014.
 */
public class RechercheLieu {

    public RechercheLieu() {
        lieux = new LinkedList<Lieu>();

        Batiment b1 = new Batiment(1, "U1", new Position(43.560304, 1.470264));
      //  b1.setFavorite(true);
        Batiment b2 = new Batiment(2, "U2", new Position(43.561322, 1.470526));
        Batiment b3 = new Batiment(3, "U3", new Position(43.561961, 1.469963));
        Batiment b4 = new Batiment(4, "A2", new Position(43.562313, 1.458113));
        Batiment b5 = new Batiment(5, "A1", new Position(43.562785, 1.458816));
        Batiment b6 = new Batiment(6, "3TP1", new Position(43.559872, 1.468856));
        Batiment b7 = new Batiment(7, "1R1", new Position(43.561283, 1.466285));
        Batiment b8 = new Batiment(8, "3TP2", new Position(43.560965, 1.467803));

        Salle s1 = new Salle(1, "Mathis", new Position(43.560304, 1.470264), b1);
        Salle s2 = new Salle(2, "Borel", new Position(43.561322, 1.470526), b2);
        Salle s3 = new Salle(3, "Frenet", new Position(43.561322, 1.470526), b2);
        Salle s4 = new Salle(4, "Vandel", new Position(43.561322, 1.470526), b2);
        Salle s5 = new Salle(5, "De Broglie", new Position(43.561322, 1.470526), b2);
        Salle s6 = new Salle(6, "Denjoy", new Position(43.560304, 1.470264), b1);
        Salle s7 = new Salle(7, "Einstein", new Position(43.560965, 1.467803), b8);

        lieux.push(b1);
		lieux.getLast().addPorte(new Position(43.560474, 1.470285));
        lieux.push(b2);
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
        lieux.push(b3);
		lieux.getLast().addPorte(new Position(43.561502, 1.470264));
        lieux.push(b4);
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
        lieux.push(b5);
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
        lieux.push(b6);
        lieux.getLast().addPorte(new Position(43.561030, 1.470414));
        lieux.push(b7);
        lieux.getLast().addPorte(new Position(43.561030, 1.470414));
        lieux.push(b8);
        lieux.getLast().addPorte(new Position(43.561030, 1.470414));

        lieux.push(s1);
        lieux.push(s2);
        lieux.push(s3);
        lieux.push(s4);
        lieux.push(s5);
        lieux.push(s6);
        lieux.push(s7);

/*		addSalle(5, "210", new Position(4.561734, 10.470425), new Batiment(2,"U2", new Position(43.561734, 1.470425)));
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
		addSalle(6, "211", new Position(4.561734, 10.470425), new Batiment(2,"U2", new Position(43.561734, 1.470425)));
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
		addSalle(7, "010", new Position(4.561734, 10.470425), new Batiment(3,"U3", new Position(43.561734, 1.470425)));
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));

/*		addService(8, "infirmerie", new Position(4.561734, 10.470425), new Batiment(12,"Administration", new Position(43.561734, 1.470425)));
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
		addService(9, "Cafétéria", new Position(4.561734, 10.470425), new Batiment(13,"RU1", new Position(43.561734, 1.470425)));
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
		addService(10, "Parking 1", new Position(4.561734, 10.470425), null);
		lieux.getLast().addPorte(new Position(43.561030, 1.470414));
		*/
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

    /**********************************************************/
    /** Getter : getFavorites
     *
     *  Description : Retourne tous les Lieu favoris
     *  @return List<Lieu>
     *  		liste des Lieu favoris
    /**********************************************************/
    public final List<Lieu> getFavorites() {
        List<Lieu> ls = new LinkedList<Lieu>();

        for (Lieu l : lieux) {
            if (l.isFavorite()) {
                ls.add(l);
            }
        }

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
            if (l.getNom().toLowerCase().startsWith(_nom.toLowerCase())) {
                ls.push(l);
            }
        }
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
