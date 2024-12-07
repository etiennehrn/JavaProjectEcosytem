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


    // Directions possibles
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    
    // Pour l'animation
    private int animationFrame = 0;
    private Direction lastDirection = Direction.DOWN;

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
        Image[] directionSprites = AnimalSpriteManager.getInstance().getSprites(type, lastDirection);
        return directionSprites[animationFrame];
    }

    // Mise à jour de l'animation et de la direction
    protected void updateAnimation(Direction direction) {
        this.lastDirection = direction;
        this.animationFrame = (animationFrame + 1) % 3; // Boucle sur 3 images
    }

    // Méthode pour convertir une direction en chaîne
    protected static Direction parseDirection(int[] movement) {
        if (movement[0] == -1) return Direction.UP;
        if (movement[0] == 1) return Direction.DOWN;
        if (movement[1] == -1) return Direction.LEFT;
        if (movement[1] == 1) return Direction.RIGHT;
        return Direction.DOWN; // Par défaut
    }

    // Getter
    public Type getType() {
        return type;
    }

}
