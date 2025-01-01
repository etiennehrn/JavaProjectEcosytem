package com.example.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant un type de sable dans la map.
 * Chaque type de sable peut avoir plusieurs variantes qui diffèrent par leur apparence.
 */
public class HerbeType extends BaseType {
    /**
     * Enum représentant les variantes possibles pour le type d'herbe.
     */
    public enum VariantHerbe {
        CLAIR, DALLE
    }

    /** Code correspondant à la variante */
    private String code;

    /** Map contenant les textures associées à chaque variante de sable */
    private static final Map<VariantHerbe, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantHerbe.CLAIR, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_clair.png"))));
        textureMap.put(VariantHerbe.DALLE, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_dalle.png"))));
    }

    /** Type de variant */
    private VariantHerbe variant;

    /**
     * Constructeur de la classe HerbeType.
     * Initialise le type d'herbe en fonction de la variante spécifiée.
     * Charge la texture correspondante et définit un code unique pour chaque variante.
     *
     * @param variant La variante d'herbe à associer à cet objet.
     */
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

    /**
     * Retourne la variante d'herbe associée à cet objet.
     *
     * @return La variante d'herbe.
     */
    public VariantHerbe getVariant() {
        return variant;
    }

    /**
     * Retourne le code associé à cette variante d'herbe.
     *
     * @return Le code sous forme de chaîne de caractères.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourne la map contenant toutes les textures des variantes d'herbe.
     *
     * @return La map associant chaque variante à une texture.
     */
    public static Map<VariantHerbe, Image> getTextureMap() {
        return textureMap;
    }

    /**
     * Vérifie si cet objet est un obstacle.
     *
     * @return Retourne toujours `false`, car l'herbe n'est pas un obstacle.
     */
    @Override
    public boolean isObstacle() {
        return false;
    }

}
