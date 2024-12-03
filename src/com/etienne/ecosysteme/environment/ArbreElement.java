package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Element arbre
public class ArbreElement extends Element {
    // Variant pour l'arbre
    public enum Variant {
        CLAIR, MORT, BUISSON, CHAMPIGNON, NENUPHAR, DENSE_CLAIR
    }

    // Map des textures
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        try {
            textureMap.put(Variant.CLAIR, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_clair.png"), "Texture manquante : CLAIR")));
            textureMap.put(Variant.MORT, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_mort.png"), "Texture manquante : MORT")));
            textureMap.put(Variant.BUISSON, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_buisson.png"), "Texture manquante : BUISSON")));
            textureMap.put(Variant.CHAMPIGNON, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/champignon.png"), "Texture manquante : CHAMPIGNON")));
            textureMap.put(Variant.NENUPHAR, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/nenuphar.png"), "Texture manquante : NENUPHAR")));
            textureMap.put(Variant.DENSE_CLAIR, new Image(Objects.requireNonNull(ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_dense_clair.png"), "Texture manquante : DENSE_CLAIR")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Erreur lors du chargement des textures d'ArbreElement", e);
        }
    }

    private final Variant variant;

    // Constructeur
    public ArbreElement(Variant variant) {
        super(Objects.requireNonNull(textureMap.get(variant), "Texture introuvable pour le variant: " + variant));
        this.variant = variant;
    }

    // Getter
    public Variant getVariant() {
        return variant;
    }

    @Override
    public boolean isObstacle() {
        return getVariant() == Variant.MORT || getVariant() == Variant.CLAIR;
    }

    @Override
    public boolean canPassObstacle() {
        return getVariant() == Variant.NENUPHAR;
    }
}
