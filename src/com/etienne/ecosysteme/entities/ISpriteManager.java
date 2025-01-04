package com.etienne.ecosysteme.entities;

import javafx.scene.image.Image;

public interface ISpriteManager {
    /**
     * Charge tous les sprites nécessaires pour le jeu.
     */
    void loadAllSprites();

    /**
     * Récupère-les sprites pour un type et une direction donnée.
     *
     * @param type      Le type d'entité (par exemple, "human", "zombie").
     * @param direction La direction de l'entité.
     * @return Un tableau d'images représentant les sprites pour cette direction.
     */
    Image[] getSprites(String type, Animaux.Direction direction);

}
