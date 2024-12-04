package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;

public abstract class Animaux extends EtreVivant {
    // Enumérateur pour avoir les types d'animaux différents
    public enum Type {
        DEER, BEAR, BOAR, FOX, WOLF, BUNNY
    }

    // Constructeur
    public Animaux(int row, int col, int vitesse, int nourriture, int visionRange) {
        super(row, col, vitesse, nourriture, visionRange);
    }

    // Méthode abstraite pour générer le déplacement
    public abstract void gen_deplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col);

    // Méthode pour obtenir le/les sprites en fonction de la taille d'un carré élémentaire
    @Override
    public abstract ImageView getSprite(int tileSize);

}
