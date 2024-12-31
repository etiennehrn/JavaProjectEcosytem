package com.example.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
// Type herbe
public class HerbeType extends BaseType {
    // Liste des Variants
    public enum VariantHerbe {
        CLAIR, DALLE
    }

    // Code
    private String code;

    // Dico pour avoir toutes les textures, et les charger que une seule fois
    private static final Map<VariantHerbe, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantHerbe.CLAIR, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_clair.png"))));
        textureMap.put(VariantHerbe.DALLE, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_dalle.png"))));
    }

    // Pour connaitre le variant de l'herbe
    private VariantHerbe variant;

    // Constructeur
    public HerbeType(VariantHerbe variant) {
        super(textureMap.get(variant));
        this.variant = variant;
        this.code = switch (variant) {
            // ICI LE CODE DES TYPES DE BASES
            case CLAIR -> "00" ;
            case DALLE -> "01" ;
            default -> throw new IllegalArgumentException("Type d'herbe inconnu : " + variant);
        };
    }

    // Getter
    public VariantHerbe getVariant() {
        return variant;
    }
    public String getCode() { return code; }
    public static Map<VariantHerbe, Image> getTextureMap() {
        return textureMap;
    }


    @Override
    public boolean isObstacle() {
        return false;
    }
}
