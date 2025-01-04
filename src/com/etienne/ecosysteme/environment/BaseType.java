package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

/**
 * Classe abstraite représentant un type de base pour une case.
 * Les types de base incluent des terrains comme herbe, eau, sable, etc.
 * Chaque type de base est associé à une texture et possède des propriétés spécifiques.
 */
public abstract class BaseType {

    // Texture graphique représentant le type de base
    private final Image texture;

    /**
     * Constructeur de la classe BaseType.
     * Initialise un type de base avec une texture.
     *
     * @param texture Texture graphique associée au type de base.
     */
    public BaseType(Image texture) {
        this.texture = texture;
    }

    /**
     * Retourne la texture graphique de ce type de base.
     *
     * @return Image représentant la texture du type de base.
     */
    public Image getTexture() {
        return texture;
    }

    /**
     * Détermine si ce type de base constitue un obstacle.
     * Les sous-classes doivent implémenter cette méthode pour spécifier le comportement.
     *
     * @return {@code true} si le type de base est un obstacle, {@code false} sinon.
     */
    public abstract boolean isObstacle();
}
