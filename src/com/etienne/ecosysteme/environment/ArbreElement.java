package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant un élément de type "Arbre" dans l'écosystème.
 * Les arbres peuvent avoir différents variants, chacun avec une texture spécifique.
 * Certains variants peuvent constituer des obstacles, tandis que d'autres permettent de passer.
 */
public class ArbreElement extends Element {

    /**
     * Enumération des différents variants d'arbre.
     * Chaque variant représente un type d'arbre avec une texture unique.
     */
    public enum Variant {
        CLAIR, MORT, BUISSON, CHAMPIGNON, NENUPHAR, DENSE_CLAIR
    }

    // Carte statique associant chaque variant d'arbre à sa texture
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures statiques au démarrage
    static {
        try {
            textureMap.put(Variant.CLAIR, new Image(Objects.requireNonNull(
                    ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_clair.png"),
                    "Texture manquante : CLAIR")));
            textureMap.put(Variant.MORT, new Image(Objects.requireNonNull(
                    ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_mort.png"),
                    "Texture manquante : MORT")));
            textureMap.put(Variant.BUISSON, new Image(Objects.requireNonNull(
                    ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_buisson.png"),
                    "Texture manquante : BUISSON")));
            textureMap.put(Variant.CHAMPIGNON, new Image(Objects.requireNonNull(
                    ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/champignon.png"),
                    "Texture manquante : CHAMPIGNON")));
            textureMap.put(Variant.NENUPHAR, new Image(Objects.requireNonNull(
                    ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/nenuphar.png"),
                    "Texture manquante : NENUPHAR")));
            textureMap.put(Variant.DENSE_CLAIR, new Image(Objects.requireNonNull(
                    ArbreElement.class.getResourceAsStream("/ressources/textures/element/arbre/arbre_dense_clair.png"),
                    "Texture manquante : DENSE_CLAIR")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Erreur lors du chargement des textures d'ArbreElement", e);
        }
    }

    // Variant spécifique pour chaque instance d'ArbreElement
    private final Variant variant;

    /**
     * Constructeur de la classe ArbreElement.
     * Associe un variant à une texture spécifique.
     *
     * @param variant Le variant spécifique de l'arbre (par exemple, CLAIR, MORT, BUISSON).
     */
    public ArbreElement(Variant variant) {
        super(Objects.requireNonNull(textureMap.get(variant),
                "Texture introuvable pour le variant: " + variant)); // Appel au constructeur de la classe mère Element
        this.variant = variant;
    }

    /**
     * Retourne le variant de cette instance d'ArbreElement.
     *
     * @return Le variant associé (CLAIR, MORT, BUISSON, etc.).
     */
    public Variant getVariant() {
        return variant;
    }

    /**
     * Détermine si cet élément est un obstacle.
     * Certains variants (par exemple, CLAIR, MORT) sont considérés comme des obstacles.
     *
     * @return {@code true} si l'élément est un obstacle, {@code false} sinon.
     */
    @Override
    public boolean isObstacle() {
        return getVariant() == Variant.MORT || getVariant() == Variant.CLAIR;
    }

    /**
     * Détermine si cet élément permet de passer un obstacle.
     * Par exemple, le variant NENUPHAR permet de contourner un obstacle.
     *
     * @return {@code true} si l'élément permet de passer un obstacle, {@code false} sinon.
     */
    @Override
    public boolean canPassObstacle() {
        return getVariant() == Variant.NENUPHAR;
    }
}
