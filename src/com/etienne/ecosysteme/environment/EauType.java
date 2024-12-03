package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EauType extends BaseType {
    // Variant possible pour l'eau
    public enum Variant {
        CENTRE
    }

    // Map des textures possibles
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(Variant.CENTRE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_centre.png"))));
    }

    // Type de variant
    private final Variant variant;

    // Constructeur
    public EauType(Variant variant) {
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
}
