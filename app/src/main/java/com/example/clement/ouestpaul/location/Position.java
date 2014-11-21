package com.example.clement.ouestpaul.location;

/**
 * Created by Adrien on 09/11/2014.
 */
/***********************************************************************************/

import java.io.Serializable;

/** Nom de la classe : CoordGPS
 * Description : Cordonnées GPS pour les Lieu
 * Variable(s) :
 * 		double x: Position X (latitude)
 * 		double y: Position Y (longitude)
 * Constructeur(s) :
 * 		CoordGPS(double _x, double _y) : Creer une coordonnée grace a la latitude et longitude
 * Liste des méthodes :
 * 		double getDistance(CoordGPS _c)
 * 		double getX()
 * 		double getY()
 * 		void setX(double _x)
 * 		void setY(double _x)
 /***********************************************************************************/
public class Position implements Serializable {

    protected static final long serialVersionUID = -7060210544600464481L;
    /********************CONSTRUCTEUR(S)**********************************/
    public Position(double _x, double _y) {
        x = _x; y = _y;
    }
    /********************FIN CONSTRUCTEUR(S)**********************************/

    public double getDistance(Position _c) {
        return Math.sqrt((_c.getX()-x)*(_c.getX()-x) + (_c.getY()-y)*(_c.getY()-y));
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void setX(double _x) {
        x = _x;
    }
    public void setY(double _y) {
        y = _y;
    }
    public String toString()
    {
        return x + " " + y;
    }

    /********************VARIABLE(S)**************************************/
    private double x, y;
    /********************FIN VARIABLE(S)**************************************/
}
