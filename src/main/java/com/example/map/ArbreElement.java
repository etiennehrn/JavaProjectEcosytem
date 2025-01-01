package com.example.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Représente un élément de type arbre dans le terrain, avec plusieurs variantes telles que
 * un arbre clair, un arbre mort, un buisson, un champignon, un nénuphar, ou un arbre dense clair.
 * Chaque variante a une texture associée.
 */
public class ArbreElement extends Element {
    /**
     * Enumération représentant les différentes variantes d'arbres.
     */
    public enum VariantArbre {
        CLAIR, MORT, BUISSON, CHAMPIGNON, NENUPHAR, DENSE_CLAIR
    }

    /** Code pour la variante d'arbre */
    public String code;

    /** Map des textures pour chaque variante d'arbre */
    private static final Map<VariantArbre, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        try {
            textureMap.put(VariantArbre.CLAIR, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_clair.png"), "Texture manquante : CLAIR")));
            textureMap.put(VariantArbre.MORT, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_mort.png"), "Texture manquante : MORT")));
            textureMap.put(VariantArbre.BUISSON, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_buisson.png"), "Texture manquante : BUISSON")));
            textureMap.put(VariantArbre.CHAMPIGNON, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/champignon.png"), "Texture manquante : CHAMPIGNON")));
            textureMap.put(VariantArbre.NENUPHAR, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/nenuphar.png"), "Texture manquante : NENUPHAR")));
            textureMap.put(VariantArbre.DENSE_CLAIR, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_dense_clair.png"), "Texture manquante : DENSE_CLAIR")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Erreur lors du chargement des textures d'ArbreElement", e);
        }
    }

    /** Variante de l'arbre */
    private VariantArbre variant;

    /**
     * Constructeur pour créer un élément d'arbre avec une variante spécifique.
     * Ce constructeur initialise la texture et le code de l'élément en fonction de la variante.
     *
     * @param variant La variante d'arbre à utiliser pour cet élément (par exemple, CLAIR, MORT, etc.).
     */
    public ArbreElement(VariantArbre variant) {
        super(Objects.requireNonNull(textureMap.get(variant), "Texture introuvable pour le variant: " + variant));
        this.variant = variant;
        this.code = switch (variant) {
            case CLAIR -> "A";
            case MORT -> "B";
            case BUISSON -> "C";
            case CHAMPIGNON -> "D";
            case NENUPHAR -> "E";
            case DENSE_CLAIR -> "H";
        };
    }


    // Getter

    /**
     * Récupère la variante d'arbre associée à cet élément.
     *
     * @return La variante d'arbre.
     */
    public VariantArbre getVariant() {
        return variant;
    }

    /**
     * Récupère le code associé à cette variante d'arbre.
     *
     * @return Le code (par exemple, "A" pour CLAIR, "B" pour MORT, etc.).
     */
    public String getCode() {
        return code;
    }

    /**
     * Récupère la map des textures associées à chaque variante d'arbre.
     *
     * @return La map des textures.
     */
    public static Map<ArbreElement.VariantArbre, Image> getTextureMap() {
        return textureMap;
    }

    /**
     * Détermine si cet élément d'arbre est un obstacle.
     *
     * @return true si l'arbre est un obstacle (par exemple, pour les variantes CLAIR et MORT), false sinon.
     */
    @Override
    public boolean isObstacle() {
        return getVariant() == VariantArbre.MORT || getVariant() == VariantArbre.CLAIR;
    }

    /**
     * Détermine si cet élément d'arbre permet de passer à travers.
     *
     * @return true si l'élément permet de passer à travers (par exemple, pour la variante NENUPHAR),
     *         false sinon.
     */
    @Override
    public boolean canPassObstacle() {
        return getVariant() == VariantArbre.NENUPHAR;
    }
}