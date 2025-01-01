package com.example.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe abstraite représentant un élément possible sur un type de base dans le jeu.
 * Un élément a une texture et peut être un obstacle ou permettre de passer à travers.
 */
public abstract class Element {
    /** Texture de l'élément */
    private final Image texture;

    /**
     * Constructeur de la classe Element.
     * Initialise l'élément avec une texture spécifique.
     *
     * @param texture L'image représentant la texture de cet élément.
     */
    public Element(Image texture) {
        this.texture = texture;
    }

    /**
     * Retourne la texture de l'élément.
     *
     * @return L'image représentant la texture de l'élément.
     */
    public Image getTexture() {
        return texture;
    }

    /**
     * Retourne un code unique pour cet élément.
     * Chaque sous-classe doit implémenter cette méthode pour fournir un code propre à son type.
     *
     * @return Le code unique de l'élément sous forme de chaîne de caractères.
     */
    public abstract String getCode();

    /**
     * Détermine si l'élément est un obstacle dans le jeu.
     * Chaque sous-classe doit implémenter cette méthode pour définir si l'élément est un obstacle.
     *
     * @return Retourne `true` si l'élément est un obstacle, sinon `false`.
     */
    public abstract boolean isObstacle();

    /**
     * Détermine si l'élément permet de passer un obstacle.
     * Chaque sous-classe doit implémenter cette méthode pour définir si l'élément permet de passer à travers.
     *
     * @return Retourne `true` si l'élément permet de passer, sinon `false`.
     */
    public abstract boolean canPassObstacle();
}
