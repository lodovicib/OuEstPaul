package com.example.clement.ouestpaul.lieux;

import com.example.clement.ouestpaul.location.Position;

/**
 * Created by Adrien on 09/11/2014.
 */
public class Batiment extends Lieu {
    /****************
     * @param _id entree: int
     * @param _nom entree: String
     * @param _coord entree: CoordGPS
    /****************/

    public Batiment(
            final int _id, final String _nom,
            final Position _coord) {
        super(_id, _nom, Lieu.TypeLieu.BATIMENT, _coord);
    }

    public Batiment(
            final int _id, final String _nom,
            final Position _coord, final String _activite, final String _etablissement) {
        super(_id, _nom, Lieu.TypeLieu.BATIMENT, _coord, _activite, _etablissement);
    }

    /**********************************************************/
    /** Nom de la méthode : toString
     *  Description : Convertie la classe en String
     *  @return String
    /**********************************************************/
    public final String toString() {
        return getNom();
    }

    @Override
    public int compareTo(Lieu o) {
        return this.nom.compareTo(o.getNom());
    }
}
