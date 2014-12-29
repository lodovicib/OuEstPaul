package com.example.clement.ouestpaul.lieux;

/**
 * Created by Adrien on 09/11/2014.
 */
/***********************************************************************************/

import com.example.clement.ouestpaul.location.Position;

/** Nom de la classe : Service
 * Description : Gère les salles de l'université
 * Hérite de : Lieu
 * Variable(s) :
 * 		Batiment batiment : batiment ou se trouve le service
 * Constructeur(s) :
 * 		Service(int _id, String _nom, CoordGPS _coord) : prend l'id et le nom du
 * 				service ainsi que les coordonnées où elle se trouve
 * 		Service(int _id, String _nom, CoordGPS _coord, Batiment _batiment) : prend
 * 				l'id et le nom du service ainsi que les coordonnées et le
 * 					batiment où elle se trouve
 * Liste des méthodes :
 * 		String getBatiment()
 * 		String toString()
 /***********************************************************************************/
public class Service extends Lieu {

    /********************VARIABLE(S)**************************************/
    private Batiment batiment;
/********************FIN VARIABLE(S)**********************************/
/********************CONSTRUCTEUR(S)**********************************/
    /****************
     * @param _id entree: int
     * @param _nom entree: String
     * @param _coord entree: CoordGPS
    /****************/
    public Service(final int _id, final String _nom, final Position _coord) {
        super(_id, _nom, TypeLieu.SERVICE, _coord);
        batiment = null;
    }

    /****************
     * @param _id entree: int
     * @param _nom entree: String
     * @param _coord entree: CoordGPS
     * @param _batiment entree: Batiment
    /****************/
    public Service(final int _id, final String _nom,
                   final Position _coord, final Batiment _batiment) {
        super(_id, _nom, TypeLieu.SERVICE, _coord);
        batiment = _batiment;
    }
/********************FIN CONSTRUCTEUR(S)******************************/

    /****************
     * @return String
    /****************/
    public final String getBatiment() {
        if (batiment != null) {
            return batiment.getNom();
        }
        return "";
    }

    /****************
     * @return String
    /****************/
    public final String toString() {
        if (batiment != null) {
            return batiment.getNom() + " - " + getNom();
        }
        return getNom();
    }

    @Override
    public int compareTo(Lieu lieu) {
        return this.nom.compareTo(lieu.getNom());
    }
}
