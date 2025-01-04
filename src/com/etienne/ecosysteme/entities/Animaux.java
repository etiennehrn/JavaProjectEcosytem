package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe abstraite représentant un animal dans l'écosystème.
 * Elle hérite de la classe EtreVivant et fournit des fonctionnalités spécifiques aux animaux.
 */
public abstract class Animaux extends EtreVivant {

    // Enumération des différents types d'animaux
    public enum Type {
        DEER, BEAR, PIG, FOX, WOLF, BUNNY
    }

    // Type spécifique de l'animal (par exemple, cerf, ours, etc.)
    private final Type type;

    /**
     * Constructeur de la classe Animaux.
     *
     * @param row          Ligne initiale de l'animal sur la carte.
     * @param col          Colonne initiale de l'animal sur la carte.
     * @param vitesse      Vitesse de déplacement de l'animal.
     * @param nourriture   Quantité de nourriture de l'animal.
     * @param visionRange  Portée de vision de l'animal.
     * @param type         Type de l'animal (défini par l'enum Type).
     */
    public Animaux(int row, int col, int vitesse, int nourriture, int visionRange, Type type) {
        super(row, col, vitesse, nourriture, visionRange);
        this.type = type;
    }

    /**
     * Méthode abstraite pour générer un déplacement spécifique à l'animal.
     * Chaque sous-classe doit implémenter cette méthode en fonction de son comportement.
     *
     * @param mapVivant   Carte des entités vivantes.
     * @param grid        Carte environnementale.
     * @param row         Ligne actuelle de l'animal.
     * @param col         Colonne actuelle de l'animal.
     */
    public abstract void gen_deplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col);

    /**
     * Retourne le sprite actuel de l'animal sous forme d'une ImageView.
     * L'image est redimensionnée en fonction de la taille des cases.
     *
     * @param tileSize Taille d'un carré élémentaire sur la carte.
     * @return ImageView représentant l'animal.
     */
    @Override
    public ImageView getSprite(int tileSize) {
        Image sprite = getCurrentSprite();
        ImageView imageView = new ImageView(sprite);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    /**
     * Obtient le sprite actuel de l'animal basé sur sa direction et son animation.
     *
     * @return L'image actuelle du sprite.
     */
    private Image getCurrentSprite() {
        Image[] directionSprites = SpriteManager.getInstance().getSprites(typeToSring(getType()), getLastDirection());
        return directionSprites[getAnimationFrame()];
    }

    /**
     * Retourne le type de l'animal.
     *
     * @return Type de l'animal (par exemple, DEER, BEAR, etc.).
     */
    public Type getType() {
        return type;
    }

    /**
     * Convertit le type de l'animal en une chaîne de caractères.
     *
     * @param type Type de l'animal.
     * @return Nom du type sous forme de chaîne (par exemple, "deer" pour DEER).
     */
    private String typeToSring(Animaux.Type type) {
        return switch (type) {
            case DEER -> "deer";
            case BEAR -> "bear";
            case PIG -> "pig";
            case FOX -> "fox";
            case WOLF -> "wolf";
            case BUNNY -> "bunny";
            default -> "";
        };
    }

    /**
     * Retourne le facteur de vitesse de l'animal pendant la nuit.
     * Par défaut, les animaux sont plus lents la nuit.
     *
     * @return Facteur de vitesse pendant la nuit.
     */
    @Override
    public double getFacteurVitesseNuit() {
        return 3;
    }

    /**
     * Retourne le facteur de vitesse de l'animal pendant la journée.
     * Par défaut, la vitesse est normale le jour.
     *
     * @return Facteur de vitesse pendant le jour.
     */
    @Override
    public double getFacteurVitesseJour() {
        return 1;
    }
}
