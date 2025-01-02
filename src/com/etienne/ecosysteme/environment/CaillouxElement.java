package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * La classe `CaillouxElement` représente un élément de type cailloux qui peut être présent sur une case.
 * Cet élément peut avoir différentes variantes, comme un caillou de taille moyenne ou petit.
 */

public class CaillouxElement extends Element {
    /** Variant pour les cailloux */
    public enum VariantCailloux {
        MOYEN, PETIT
    }

    /** Code pour le variant */
    public String code;

    /** Map des textures */
    private static final Map<VariantCailloux, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantCailloux.MOYEN, new Image(Objects.requireNonNull(CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_moyen.png"))));
        textureMap.put(VariantCailloux.PETIT, new Image(Objects.requireNonNull(CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_petit.png"))));
    }

    /** Variante spécifique de cailloux (par exemple, moyen ou petit) */
    private final VariantCailloux variant;

    /**
     * Constructeur pour créer un nouvel élément de type cailloux avec une variante spécifique.
     *
     * @param variant La variante de cailloux (par exemple, moyen ou petit).
     */
    public CaillouxElement(VariantCailloux variant) {
        super(textureMap.get(variant));
        this.variant = variant;
        this.code = switch (variant) {
            case MOYEN -> "F";
            case PETIT -> "G";
        };
    }

    // Getters
    /**
     * Récupère la variante de cailloux.
     *
     * @return La variante de cailloux.
     */
    public VariantCailloux getVariant() {
        return variant;
    }

    /**
     * Récupère le code de l'élément de type cailloux.
     * Ce code est utilisé pour identifier l'élément dans une case.
     *
     * @return Le code de l'élément.
     */
    public String getCode() {
        return code;
    }

    /**
     * Récupère la map des textures des variantes de cailloux.
     *
     * @return La map des textures.
     */
    public static Map<CaillouxElement.VariantCailloux, Image> getTextureMap() {
        return textureMap;
    }

    //Méthode pour savoir si il s'agit d'un obstacle
    /**
     * Indique si l'élément de type cailloux est un obstacle.
     * Les cailloux sont des obstacles sur le terrain.
     *
     * @return `true` si l'élément est un obstacle.
     */
    @Override
    public boolean isObstacle() {
        return true;
    }

    /**
     * Indique si l'obstacle (cailloux) peut être franchi.
     * Les cailloux ne permettent pas de passer à travers.
     *
     * @return `false` car un cailloux ne peut pas être franchi.
     */
    @Override
    public boolean canPassObstacle() {
        return false;
    }

}