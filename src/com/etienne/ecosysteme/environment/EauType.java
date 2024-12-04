package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EauType extends BaseType {
    // Variant possible pour l'eau
    public enum Variant {
        CENTRE, GAUCHE, DROIT, HAUT, BAS, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE
    }

    // Map des textures possibles
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(Variant.CENTRE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_centre.png"))));
        textureMap.put(Variant.GAUCHE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_gauche.png"))));
        textureMap.put(Variant.DROIT, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_droite.png"))));
        textureMap.put(Variant.HAUT, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_haut.png"))));
        textureMap.put(Variant.BAS, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_bas.png"))));
        textureMap.put(Variant.HAUT_GAUCHE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_haut_gauche.png"))));
        textureMap.put(Variant.HAUT_DROITE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_haut_droite.png"))));
        textureMap.put(Variant.BAS_GAUCHE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_bas_gauche.png"))));
        textureMap.put(Variant.BAS_DROITE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_bas_droite.png"))));
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
