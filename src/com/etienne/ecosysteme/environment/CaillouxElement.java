package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CaillouxElement extends Element {
    // Variant pour les cailloux
    public enum Variant {
        MOYEN, PETIT
    }

    // Map des textures
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(Variant.MOYEN, new Image(Objects.requireNonNull(CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_moyen.png"))));
        textureMap.put(Variant.PETIT, new Image(Objects.requireNonNull(CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_petit.jpg"))));
    }

    private final Variant variant;

    // Constructeur
    public CaillouxElement(Variant variant) {
        super(textureMap.get(variant));
        this.variant = variant;
    }

    // Getter
    public Variant getVariant() {
        return variant;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public boolean canPassObstacle() {
        return false;
    }

}
