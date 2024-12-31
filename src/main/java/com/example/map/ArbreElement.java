package com.example.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Element arbre
public class ArbreElement extends Element {
    // Variant pour l'arbre
    public enum VariantArbre {
        CLAIR, MORT, BUISSON, CHAMPIGNON, NENUPHAR, DENSE_CLAIR
    }

    // Code
    public String code;

    // Map des textures
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

    private VariantArbre variant;

    // Constructeur
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
    public VariantArbre getVariant() {
        return variant;
    }
    public String getCode(){ return code; }
    public static Map<ArbreElement.VariantArbre, Image> getTextureMap() {
        return textureMap;
    }


    @Override
    public boolean isObstacle() {
        return getVariant() == VariantArbre.MORT || getVariant() == VariantArbre.CLAIR;
    }

    @Override
    public boolean canPassObstacle() {
        return getVariant() == VariantArbre.NENUPHAR;
    }
}
