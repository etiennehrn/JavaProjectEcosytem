package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

/**
 * Classe abstraite représentant un élément présent sur une case de la carte.
 * Un élément est associé à une texture et possède des propriétés spécifiques,
 * comme la possibilité d'être un obstacle ou de permettre de contourner un obstacle.
 */
public abstract class Element {

    // Texture graphique représentant l'élément
    private final Image texture;

    /**
     * Constructeur de la classe Element.
     * Associe une texture à l'élément.
     *
     * @param texture Texture graphique de l'élément.
     */
    public Element(Image texture) {
        this.texture = texture;
    }

    /**
     * Retourne la texture graphique de l'élément.
     *
     * @return Image représentant la texture de l'élément.
     */
    public Image getTexture() {
        return texture;
    }

    /**
     * Détermine si l'élément constitue un obstacle.
     * Cette méthode doit être implémentée par les sous-classes.
     *
     * @return {@code true} si l'élément est un obstacle, {@code false} sinon.
     */
    public abstract boolean isObstacle();

    /**
     * Détermine si l'élément permet de passer un obstacle.
     * Cette méthode doit être implémentée par les sous-classes.
     *
     * @return {@code true} si l'élément permet de contourner un obstacle, {@code false} sinon.
     */
    public abstract boolean canPassObstacle();
}
