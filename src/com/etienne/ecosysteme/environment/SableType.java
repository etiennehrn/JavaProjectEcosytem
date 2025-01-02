package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant un type de sable dans la map.
 * Chaque type de sable peut avoir plusieurs variantes qui diffèrent par leur apparence.
 */
public class SableType extends BaseType {
    /**
     * Enum représentant les variantes possibles pour le sable.
     */
    public enum VariantSable {
        CENTRE, GAUCHE, DROITE, HAUT, BAS, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE,
        VERTICAL_HAUT, VERTICAL_BAS, VERTICAL_MILIEU, HORIZONTAL_GAUCHE, HORIZONTAL_DROITE,
        HORIZONTAL_MILIEU, UNIQUE
    }

    /** Code correspondant à la variante */
    private String code;

    /** Map contenant les textures associées à chaque variante de sable */
    private static final Map<VariantSable, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantSable.CENTRE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_centre.png"))));
        textureMap.put(VariantSable.GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_gauche.png"))));
        textureMap.put(VariantSable.DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_droite.png"))));
        textureMap.put(VariantSable.HAUT, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_haut.png"))));
        textureMap.put(VariantSable.BAS, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_bas.png"))));
        textureMap.put(VariantSable.HAUT_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_haut_gauche.png"))));
        textureMap.put(VariantSable.HAUT_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_haut_droite.png"))));
        textureMap.put(VariantSable.BAS_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_bas_gauche.png"))));
        textureMap.put(VariantSable.BAS_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_bas_droite.png"))));
        textureMap.put(VariantSable.VERTICAL_HAUT, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_vertical_haut_unique.png"))));
        textureMap.put(VariantSable.VERTICAL_BAS, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_vertical_bas_unique.png"))));
        textureMap.put(VariantSable.VERTICAL_MILIEU, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_vertical_milieu_unique.png"))));
        textureMap.put(VariantSable.HORIZONTAL_GAUCHE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_horizontal_gauche_unique.png"))));
        textureMap.put(VariantSable.HORIZONTAL_DROITE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_horizontal_droite_unique.png"))));
        textureMap.put(VariantSable.HORIZONTAL_MILIEU, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_horizontal_milieu_unique.png"))));
        textureMap.put(VariantSable.UNIQUE, new Image(Objects.requireNonNull(SableType.class.getResourceAsStream("/ressources/textures/baseType/sable/sable_centre_unique.png"))));
    }

    /** Type de variant */
    private VariantSable variant;

    /**
     * Constructeur de la classe SableType.
     * Initialise le type de sable en fonction de la variante spécifiée.
     * Charge la texture et définit un code unique pour chaque variante de sable.
     *
     * @param variant La variante de sable à associer à cet objet.
     */
    public SableType(VariantSable variant) {
        super(textureMap.get(variant));
        this.variant = variant;
        this.code = switch (variant) {
            // ICI LE CODE DES TYPES DE BASES
            case CENTRE -> "18" ;
            case GAUCHE -> "19" ;
            case DROITE -> "20" ;
            case HAUT -> "21" ;
            case BAS -> "22" ;
            case HAUT_GAUCHE -> "23" ;
            case HAUT_DROITE -> "24" ;
            case BAS_GAUCHE -> "25" ;
            case BAS_DROITE -> "26" ;
            case VERTICAL_HAUT -> "27" ;
            case VERTICAL_BAS -> "28" ;
            case VERTICAL_MILIEU -> "29" ;
            case HORIZONTAL_GAUCHE -> "30" ;
            case HORIZONTAL_DROITE -> "31" ;
            case HORIZONTAL_MILIEU -> "32" ;
            case UNIQUE -> "33" ;
            default -> throw new IllegalArgumentException("Type d'eau inconnu : " + variant);

        };
    }
    /**
     * Retourne la variante de sable associée à cet objet.
     *
     * @return La variante de sable.
     */
    public VariantSable getVariant() {
        return variant;
    }

    /**
     * Retourne le code associé à cette variante de sable.
     *
     * @return Le code sous forme de chaîne de caractères.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourne la map contenant toutes les textures des variantes de sable.
     *
     * @return La map associant chaque variante à une texture.
     */
    public static Map<VariantSable, Image> getTextureMap() {
        return textureMap;
    }

    /**
     * Vérifie si cet objet est un obstacle.
     *
     * @return Retourne toujours `true`, car le sable est un obstacle.
     */
    @Override
    public boolean isObstacle() {
        return false;
    }
}