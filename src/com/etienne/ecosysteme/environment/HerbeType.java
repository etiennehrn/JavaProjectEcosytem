package com.etienne.ecosysteme.environment;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe représentant le type de terrain "Herbe" dans l'écosystème.
 * Chaque instance de HerbeType est associée à un variant spécifique,
 * correspondant à une texture particulière.
 */
public class HerbeType extends BaseType {

    /**
     * Enumération des différents variants de l'herbe.
     * Chaque variant correspond à une texture distincte (par exemple, clair, dalle, foncé).
     */
    public enum Variant {
        CLAIR, DALLE, FONCE
    }

    // Carte statique associant chaque variant à sa texture (chargée une seule fois)
    private static final Map<Variant, Image> textureMap = new HashMap<>();

    // Chargement des textures statiques
    static {
        textureMap.put(Variant.CLAIR, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_clair.png"))));
        textureMap.put(Variant.DALLE, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_dalle.png"))));
        textureMap.put(Variant.FONCE, new Image(Objects.requireNonNull(HerbeType.class.getResourceAsStream("/ressources/textures/baseType/herbe/herbe_fonce.png"))));
    }

    // Variant spécifique pour chaque instance de HerbeType
    private final Variant variant;

    /**
     * Constructeur de la classe HerbeType.
     * Associe un variant à une texture spécifique.
     *
     * @param variant Le variant spécifique de l'herbe (par exemple, CLAIR, DALLE, FONCE).
     */
    public HerbeType(Variant variant) {
        super(textureMap.get(variant)); // Appel au constructeur de BaseType avec la texture correspondante
        this.variant = variant;
    }

    /**
     * Retourne le variant de cette instance de HerbeType.
     *
     * @return Le variant associé (CLAIR, DALLE, FONCE).
     */
    public Variant getVariant() {
        return variant;
    }

    /**
     * Indique si ce type de terrain constitue un obstacle.
     * Pour l'herbe, retourne toujours {@code false}.
     *
     * @return {@code false}, car l'herbe n'est pas un obstacle.
     */
    @Override
    public boolean isObstacle() {
        return false;
    }
}
