package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant le type de terrain "Sable" dans l'écosystème.
 * Chaque instance de SableType est associée à un variant spécifique,
 * correspondant à une texture particulière.
 */
public class SableType extends BaseType {

    /**
     * Enumération des différents variants de sable.
     * Chaque variant correspond à une texture distincte représentant une disposition du sable (centre, bords, coins, etc.).
     */
    public enum Variant {
        CENTRE, GAUCHE, DROITE, HAUT, BAS,
        HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE,
        VERTICAL_HAUT, VERTICAL_BAS, VERTICAL_MILIEU,
        HORIZONTAL_GAUCHE, HORIZONTAL_DROITE, HORIZONTAL_MILIEU, UNIQUE
    }

    // Dictionnaire statique contenant les textures associées à chaque variant
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures au démarrage
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

    // Variant spécifique de cette instance de sable
    private final Variant variant;

    /**
     * Constructeur de la classe SableType.
     * Associe un variant à une texture spécifique dans la carte des textures.
     *
     * @param variant Le variant spécifique de sable.
     */
    public SableType(Variant variant) {
        super(textureMap.get(variant)); // Appel au constructeur de BaseType avec la texture correspondante
        this.variant = variant;
    }

    /**
     * Retourne le variant de cette instance de sable.
     *
     * @return Le variant associé (par exemple, CENTRE, GAUCHE, UNIQUE, etc.).
     */
    public Variant getVariant() {
        return variant;
    }

    /**
     * Indique si ce type de terrain constitue un obstacle.
     * Pour le sable, retourne toujours {@code false}.
     *
     * @return {@code false}, car le sable n'est pas un obstacle.
     */
    @Override
    public boolean isObstacle() {
        return false;
    }
}
