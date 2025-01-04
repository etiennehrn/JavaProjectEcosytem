package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant un élément de type "Cailloux" dans l'écosystème.
 * Les cailloux peuvent avoir différents variants (moyen, petit, pont),
 * chacun associé à une texture spécifique.
 */
public class CaillouxElement extends Element {

    /**
     * Enumération des différents variants de cailloux.
     * Chaque variant correspond à une texture distincte.
     */
    public enum Variant {
        MOYEN, PETIT, PONT
    }

    // Carte statique associant chaque variant de cailloux à sa texture
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures statiques au démarrage
    static {
        textureMap.put(Variant.MOYEN, new Image(Objects.requireNonNull(
                CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_moyen.png"))));
        textureMap.put(Variant.PETIT, new Image(Objects.requireNonNull(
                CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_petit.png"))));
        textureMap.put(Variant.PONT, new Image(Objects.requireNonNull(
                CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_pont.png"))));
    }

    // Variant spécifique pour chaque instance de cailloux
    private final Variant variant;

    /**
     * Constructeur de la classe CaillouxElement.
     * Associe un variant à une texture spécifique.
     *
     * @param variant Le variant spécifique du cailloux (par exemple, MOYEN, PETIT, PONT).
     */
    public CaillouxElement(Variant variant) {
        super(textureMap.get(variant)); // Appel au constructeur de la classe mère Element
        this.variant = variant;
    }

    /**
     * Retourne le variant de cette instance de CaillouxElement.
     *
     * @return Le variant associé (MOYEN, PETIT, PONT).
     */
    public Variant getVariant() {
        return variant;
    }

    /**
     * Détermine si cet élément est un obstacle.
     * Par défaut, tous les cailloux sont considérés comme des obstacles.
     *
     * @return {@code true}, car les cailloux sont des obstacles.
     */
    @Override
    public boolean isObstacle() {
        return true;
    }

    /**
     * Détermine si cet élément permet de contourner un obstacle.
     * Par défaut, les cailloux ne permettent pas de contourner un obstacle.
     *
     * @return {@code false}, car les cailloux ne permettent pas le passage.
     */
    @Override
    public boolean canPassObstacle() {
        return false;
    }
}
