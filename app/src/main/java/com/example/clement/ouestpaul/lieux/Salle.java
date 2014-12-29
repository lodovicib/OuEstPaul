package com.example.clement.ouestpaul.lieux;

/**
 * Created by Adrien on 09/11/2014.
 */

/***********************************************************************************/

import com.example.clement.ouestpaul.location.Position;

/** Nom de la classe : Salle
 * Description : Gère les salles de l'université
 * Hérite de : Lieu
 * Variable(s) :
 * 		Batiment batiment : batiment ou se trouve la salle
 * Constructeur(s) :
 * 		Salle(int _id, String _nom, TypeLieu _type, CoordGPS _coord,
 * 			Batiment _batiment) : prend l'id et le nom d'une salle,
 * 				ainsi que son type, le lieu et le batiment où elle
 * 				se trouve en paramètre
 * Liste des méthodes :
 * 		String getBatiment()
 * 		String toString()
 /***********************************************************************************/
public class Salle extends Lieu {

    /********************VARIABLE(S)**************************************/
    private Batiment batiment;
/********************FIN VARIABLE(S)**********************************/

/********************CONSTRUCTEUR(S)**********************************/
    /****************
     * @param _id entree: int
     * @param _nom entree: String
     * @param _coord entree: CoordGPS
     * @param _batiment entree: Batiment
    /****************/
    public Salle(final int _id, final String _nom, final Position _coord,
                 final Batiment _batiment) {
        super(_id, _nom, TypeLieu.SALLE, _coord);
        batiment = _batiment;
    }
/********************FIN CONSTRUCTEUR(S)******************************/
    /****************
     * @return String
    /****************/
    public final String getBatiment() {
        return batiment.getNom();
    }
    /****************
     * @return String
    /****************/
    public final String toString() {
        return batiment.getNom() + " - " + getNom();
    }

    @Override
    public int compareTo(Lieu lieu) {
        return this.nom.compareTo(lieu.getNom());
    }
}
