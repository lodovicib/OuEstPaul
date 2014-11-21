package com.example.clement.ouestpaul.lieux;

/**
 * Created by Adrien on 09/11/2014.
 */

/***********************************************************************************/

import com.example.clement.ouestpaul.location.Position;

import java.io.Serializable;
import java.util.LinkedList;

/** Nom de la classe : Lieu
 * Description : Un Lieu dans la bdd
 * Variable(s) :
 * 		int idLieu: Identifiant du Lieu dans la bdd
 *		String nom: Nom du Lieu
 *		Boolean favorite : Choix favori de l'utilisateur ?
 *		TypeLieu type: Type du Lieu suivant l'enum suivante :
 *			BATIMENT, ARRET, SERVICE, SALLE
 *		CoordGPS coordonnee: Contient les coordonnees du centre du batiment
 *		LinkedList<CoordGPS> portes: Liste les coordonnées des portes du Lieu
 * Constructeur(s) :
 * 		Lieu (int _id, String _nom, TypeLieu _type, CoordGPS _coord) :
 * 			Crée un Lieu.
 * Liste des méthodes :
 * 		void addPorte(CoordGPS _c)
 * 		Boolean isFavorite()
 * 		CoordGPS getCoordonnee()
 * 		int getIdLieu()
 * 		String getNom()
 * 		TypeLieu getType()
 * 		LinkedList<CoordGPS> getPortes()
 * 		CoordGPS getPremierePorte(CoordGPS _c)
 *
 /***********************************************************************************/


public abstract class Lieu implements Serializable  {

    protected static final long serialVersionUID = -7060210544600464481L;

    /********************VARIABLE(S)**************************************/
    /**************
     * Identifiant du Lieu dans la bdd
     /*************/
    protected int idLieu;
    /**************
     * Nom du Lieu
     /*************/
    protected String nom;
    /**************
     * Est-ce un favori ?
     /*************/
    protected Boolean favorite;
    /**************
     * Type du Lieu suivant l'enum suivante : BATIMENT, ARRET, SERVICE, SALLE
     /*************/
    protected TypeLieu type;
    /**************
     * Contient les coordonnees du centre du batiment
     /*************/
    protected Position coordonnee;
    /**************
     * Liste les coordonnées des portes du Lieu
     /*************/
    protected LinkedList<Position> portes;
    /**************
     * Liste des types de lieux
     /*************/
    public static enum TypeLieu {
        /** Type batiment */
        BATIMENT,
        /** Type arret (bus ou métro) */
        ARRET,
        /** Type service (infirmerie, parking cafeteria, etc...) */
        SERVICE,
        /** Type salle (de cours, TP, amphi, etc...) */
        SALLE
    }
    /********************FIN VARIABLE(S)**************************************/

    Lieu() {}

    /********************CONSTRUCTEUR(S)**********************************/
    /****************
     * @param _id entree: int
     * @param _nom entree: String
     * @param _type entree: TypeLieu
     * @param _coord entree: CoordGPS
    /****************/
    Lieu(final int _id, final String _nom, final TypeLieu _type,
         final Position _coord) {
        idLieu = _id;
        nom = _nom;
        type = _type;
        coordonnee = _coord;
        portes = new LinkedList<Position>();
        favorite = false;
    }
    /********************FIN CONSTRUCTEUR(S)******************************/

    /**********************************************************/
    /** Nom de la méthode : addPorte
     *  Description : Ajoute une porte au Lieu
     *  @param _c : CoordGPS - Coordonée de la porte
    /**********************************************************/
    public final void addPorte(final Position _c) {
        portes.add(_c);
    }

    /**********************************************************/
    /** Nom de la méthode : setFavorite
     *  Description : Indique qu'un Lieu devient favori ou non
     *  @param _b : Boolean - favori ou non ?
    /**********************************************************/
    public final void setFavorite(final Boolean _b) {
        favorite = _b;
    }

    /**********************************************************/
    /** Nom de la méthode : isFavorite
     *  Description : Est-ce un lieu favori ?
     *  @return Boolean
    /**********************************************************/
    public final Boolean isFavorite() {
        return favorite;
    }

    /**********************************************************/
    /** Nom de la méthode : getCoordonnee
     *  Description : Renvoie les coordonnées du Lieu
     *  @return sortie: CoordGPS (Coordonée du Lieu)
    /**********************************************************/
    public final Position getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(Position _coordonnee) {
        coordonnee = _coordonnee;
    }
    /**********************************************************/
    /** Nom de la méthode : getIdLieu
     *  Description : Renvoie l'id du Lieu
     *  @return sortie: int (Coordonée du Lieu)
    /**********************************************************/
    public final int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int _id) {
        idLieu = _id;
    }
    /**********************************************************/
    /** Nom de la méthode : getNom
     *  Description : Renvoie le nom du Lieu
     *  @return sortie: String (Nom du Lieu)
    /**********************************************************/
    public final String getNom() {
        return nom;
    }

    public void setNom(String _nom) {
        nom = _nom;
    }
    /**********************************************************/
    /** Nom de la méthode : getType
     *  Description : Renvoie le type du Lieu
     *  @return sortie: TypeLieu (Type du Lieu)
    /**********************************************************/
    public final TypeLieu getType() {
        return type;
    }

    public void setType(TypeLieu _type) {
       type = _type;    }
    /**********************************************************/
    /** Nom de la méthode : getPortes
     *  Description : Renvoie les coordonnées des portes du Lieu
     *  @return sortie: LinkedList<CoordGPS> (Liste des portes du Lieu)
    /**********************************************************/
    public final LinkedList<Position> getPortes() {
        return portes;
    }

    public void setPortes(LinkedList<Position> _portes) {
        portes = _portes;
    }
    /**********************************************************/
    /** Nom de la méthode : getPremierePorte
     *  Description : Renvoie les coordonnées de la porte la plus proche
     *  @param _c entree: CoordGPS (Coordonée)
     *  @return sortie: CoordGPS (Coordonée de la porte la plusproche)
    /**********************************************************/
    public final Position getPremierePorte(final Position _c) {
        Position porte = portes.getFirst();
        double delta = _c.getDistance(porte);

        // TODO FIX: Regarde 2 fois le premier !
        for (Position p : portes) {
            if (delta > _c.getDistance(p)) {
                porte = p;
            }
        }
        return porte;
    }

    /**********************************************************/
    /** Nom de la méthode : toString
     *  Description : Converti la classe en String
     *  @return String
    /**********************************************************/
    public abstract String toString();





}

