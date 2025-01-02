package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Animaux extends EtreVivant {
    // Enumérateur pour avoir les types d'animaux différents
    public enum Type {
        DEER, BEAR, PIG, FOX, WOLF, BUNNY
    }

    private final Type type;

    // Constructeur
    public Animaux(int row, int col, int vitesse, int nourriture, int visionRange, Type type) {
        super(row, col, vitesse, nourriture, visionRange);
        this.type = type;
    }

    // Méthode abstraite pour générer le déplacement
    public abstract void gen_deplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col);

    // Récupérer le sprite actuel, dépends de taille d'un carré élémentaire
    @Override
    public ImageView getSprite(int tileSize) {
        Image sprite = getCurrentSprite();
        ImageView imageView = new ImageView(sprite);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    // Obtenir le sprite actuel basé sur la direction et l'animation
    private Image getCurrentSprite() {
        Image[] directionSprites = SpriteManager.getInstance().getSprites(typeToSring(getType()), getLastDirection());
        return directionSprites[getAnimationFrame()];
    }

    // Getter
    public Type getType() {
        return type;
    }

    // Convertit le type en chaine de caractère
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

    // Lent la nuit
    @Override
    public double getFacteurVitesseNuit() {
        return 3;
    }

    // Normal le jour
    @Override
    public double getFacteurVitesseJour() {
        return 1;
    }

}
