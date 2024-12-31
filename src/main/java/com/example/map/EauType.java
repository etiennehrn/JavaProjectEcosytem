package com.example.map;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EauType extends BaseType {
    // Variant possible pour l'eau
    public enum VariantWater {
        CENTRE, GAUCHE, DROITE, HAUT, BAS, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE,
        VERTICAL_HAUT, VERTICAL_BAS, VERTICAL_MILIEU, HORIZONTAL_GAUCHE, HORIZONTAL_DROITE,
        HORIZONTAL_MILIEU, UNIQUE
    }

    // Code
    private String code;

    // Map des textures possibles
    private static final Map<VariantWater, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantWater.CENTRE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_centre.png"))));
        textureMap.put(VariantWater.GAUCHE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_gauche.png"))));
        textureMap.put(VariantWater.DROITE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_droite.png"))));
        textureMap.put(VariantWater.HAUT, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_haut.png"))));
        textureMap.put(VariantWater.BAS, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_bas.png"))));
        textureMap.put(VariantWater.HAUT_GAUCHE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_haut_gauche.png"))));
        textureMap.put(VariantWater.HAUT_DROITE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_haut_droite.png"))));
        textureMap.put(VariantWater.BAS_GAUCHE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_bas_gauche.png"))));
        textureMap.put(VariantWater.BAS_DROITE, new Image(Objects.requireNonNull(EauType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_bas_droite.png"))));
        textureMap.put(VariantWater.VERTICAL_HAUT, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_vertical_haut_unique.png"))));
        textureMap.put(VariantWater.VERTICAL_BAS, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_vertical_bas_unique.png"))));
        textureMap.put(VariantWater.VERTICAL_MILIEU, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_vertical_milieu_unique.png"))));
        textureMap.put(VariantWater.HORIZONTAL_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_horizontal_gauche_unique.png"))));
        textureMap.put(VariantWater.HORIZONTAL_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_horizontal_droite_unique.png"))));
        textureMap.put(VariantWater.HORIZONTAL_MILIEU, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_horizontal_milieu_unique.png"))));
        textureMap.put(VariantWater.UNIQUE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/eau/eau_unique.png"))));

    }

    // Type de variant
    private VariantWater variant;

    // Constructeur
    public EauType(VariantWater variant) {
        super(textureMap.get(variant));
        this.variant = variant;
        this.code = switch (variant) {
            // ICI LE CODE DES TYPES DE BASES
            case CENTRE -> "02" ;
            case GAUCHE -> "03" ;
            case DROITE -> "04" ;
            case HAUT -> "05" ;
            case BAS -> "06" ;
            case HAUT_GAUCHE -> "07" ;
            case HAUT_DROITE -> "08" ;
            case BAS_GAUCHE -> "09" ;
            case BAS_DROITE -> "10" ;
            case VERTICAL_HAUT -> "11" ;
            case VERTICAL_BAS -> "12" ;
            case VERTICAL_MILIEU -> "13" ;
            case HORIZONTAL_GAUCHE -> "14" ;
            case HORIZONTAL_DROITE -> "15" ;
            case HORIZONTAL_MILIEU -> "16" ;
            case UNIQUE -> "17" ;
            default -> throw new IllegalArgumentException("Type d'eau inconnu : " + variant);
        };
    }
    // Getter
    public VariantWater getVariant() {
        return variant;
    }
    public String getCode() { return code; }
    public static Map<VariantWater, Image> getTextureMap() {
        return textureMap;
    }


    @Override
    public boolean isObstacle() {
        return true;
    }
}
