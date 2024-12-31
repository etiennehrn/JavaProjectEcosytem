package com.example.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CaillouxElement extends Element {
    // Variant pour les cailloux
    public enum VariantCailloux {
        MOYEN, PETIT
    }

    // Code
    public String code;

    // Map des textures
    private static final Map<VariantCailloux, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantCailloux.MOYEN, new Image(Objects.requireNonNull(CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_moyen.png"))));
        textureMap.put(VariantCailloux.PETIT, new Image(Objects.requireNonNull(CaillouxElement.class.getResourceAsStream("/ressources/textures/element/cailloux/cailloux_petit.png"))));
    }

    private final VariantCailloux variant;

    // Constructeur
    public CaillouxElement(VariantCailloux variant) {
        super(textureMap.get(variant));
        this.variant = variant;
        this.code = switch (variant) {
            case MOYEN -> "F";
            case PETIT -> "G";
        };
    }

    // Getter
    public VariantCailloux getVariant() {
        return variant;
    }
    public String getCode() {
        return code;
    }
    public static Map<CaillouxElement.VariantCailloux, Image> getTextureMap() {
        return textureMap;
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
