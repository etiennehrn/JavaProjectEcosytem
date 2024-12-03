package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
// Type herbe
public class HerbeType extends BaseType {
    // Liste des Variants
    public enum Variant {
        CLAIR, DALLE
    }

    // Dico pour avoir toutes les textures, et les charger que une seule fois
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(Variant.CLAIR, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_clair.png"))));
        textureMap.put(Variant.DALLE, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_dalle.png"))));

    }

    // Pour connaitre le variant de l'herbe
    private final Variant variant;

    // Constructeur
    public HerbeType(Variant variant) {
        super(textureMap.get(variant));
        this.variant = variant;
    }

    // Getter
    public Variant getVariant() {
        return variant;
    }

    @Override
    public boolean isObstacle() {
        return false;
    }
}
