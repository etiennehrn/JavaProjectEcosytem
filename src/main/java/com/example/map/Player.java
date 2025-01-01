package com.example.map;

/**
 * La classe <code>Player</code> permet de générer un joueur avec des caractéristiques spécifiques (sa vitesse, son champ
 * de vision).
 */

public class Player {
    /** Valeur de la colonne du joueur */
    public int x;

    /** Valeur de la ligne du joueur */
    public int y;

    /** Champ de vision du joueur */
    public int visionRange;

    /** Vitesse du joueur */
    public int vitesse;

    /**
     * Constructeur par défaut pour initialiser un joueur avec des valeurs par défaut.
     * La position du joueur est fixée à (50, 50), la plage de vision à 20, et la vitesse à 3.
     */
    public Player() {
        this.x = 50; //position Initiale en x
        this.y = 50;
        this.visionRange = 20;
        this.vitesse = 3;
    }

    /**
     * Retourne la plage de vision du joueur.
     *
     * @return La plage de vision du joueur.
     */
    public int getVisionRange() {
        return visionRange;
    }

    /**
     * Retourne la position en Y du joueur.
     *
     * @return La position en Y du joueur.
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne la position en X du joueur.
     *
     * @return La position en X du joueur.
     */
    public int getX() {
        return x;
    }

    /**
     * Définit la position en X du joueur.
     *
     * @param x La nouvelle position en X du joueur.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Définit la position en Y du joueur.
     *
     * @param y La nouvelle position en Y du joueur.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retourne la vitesse du joueur.
     *
     * @return La vitesse de déplacement du joueur.
     */
    public int getVitesse() {
        return vitesse;
    }
}
