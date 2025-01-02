package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SableType extends BaseType {
    // Liste des Variants
    public enum Variant {
        CENTRE, GAUCHE, DROITE, HAUT, BAS, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE,
        VERTICAL_HAUT, VERTICAL_BAS, VERTICAL_MILIEU, HORIZONTAL_GAUCHE, HORIZONTAL_DROITE,
        HORIZONTAL_MILIEU, UNIQUE
    }

    // Dico pour avoir toutes les textures, et les charger que une seule fois
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(Variant.CENTRE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_centre.png"))));
        textureMap.put(Variant.GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_gauche.png"))));
        textureMap.put(Variant.DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_droite.png"))));
        textureMap.put(Variant.HAUT, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_haut.png"))));
        textureMap.put(Variant.BAS, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_bas.png"))));
        textureMap.put(Variant.HAUT_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_haut_gauche.png"))));
        textureMap.put(Variant.HAUT_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_haut_droite.png"))));
        textureMap.put(Variant.BAS_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_bas_gauche.png"))));
        textureMap.put(Variant.BAS_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_bas_droite.png"))));
        textureMap.put(Variant.VERTICAL_HAUT, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_vertical_haut_unique.png"))));
        textureMap.put(Variant.VERTICAL_BAS, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_vertical_bas_unique.png"))));
        textureMap.put(Variant.VERTICAL_MILIEU, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_vertical_milieu_unique.png"))));
        textureMap.put(Variant.HORIZONTAL_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_horizontal_gauche_unique.png"))));
        textureMap.put(Variant.HORIZONTAL_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_horizontal_droite_unique.png"))));
        textureMap.put(Variant.HORIZONTAL_MILIEU, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_horizontal_milieu_unique.png"))));
        textureMap.put(Variant.UNIQUE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_centre_unique.png"))));
    }

    // Pour connaitre le variant de l'herbe
    private final Variant variant;

    // Constructeur
    public SableType(Variant variant) {
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
