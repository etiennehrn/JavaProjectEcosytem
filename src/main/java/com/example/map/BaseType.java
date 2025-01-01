package com.example.map;

import javafx.scene.image.Image;

/**
 * Classe abstraite représentant un type de base d'une case du terrain.
 * Cela peut inclure des types tels que l'herbe, l'eau, le sol, etc.
 * Chaque type de base a une texture associée.
 */
public abstract class BaseType {
    /** Texture du type */
    private final Image texture;


    /**
     * Constructeur pour initialiser un type de base avec une texture.
     *
     * @param texture La texture associée au type de base.
     */
    public BaseType(Image texture) {
        this.texture = texture;
    }


    // Getter

    /**
     * Récupère la texture associée à ce type de base.
     *
     * @return L'image représentant la texture du type de base.
     */
    public Image getTexture() {
        return texture;
    }

    /**
     * Récupère le code du type de base.
     * Ce code sert à identifier le type de base (par exemple, pour l'herbe ou l'eau).
     *
     * @return Le code du type de base.
     */
    public abstract String getCode();

    /**
     * Méthode abstraite qui détermine si ce type de base est un obstacle.
     * Les classes dérivées (comme HerbeType, EauType, etc.) définiront cette méthode
     * pour indiquer si le type de base empêche le passage.
     *
     * @return true si ce type de base est un obstacle, false sinon.
     */
    public abstract boolean isObstacle();
}