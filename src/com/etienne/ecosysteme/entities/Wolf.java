package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import com.etienne.ecosysteme.environment.MapVivant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Random;

public class Wolf extends Animaux {
    private static final Image WOLF_IMAGE = new Image(Objects.requireNonNull(Wolf.class.getResourceAsStream("/ressources/sprites/animals/wolf.png")));
    // Sprites pour chaque direction (3 images par direction)
    private static final Image[] WOLF_SPRITES_UP = new Image[3];
    private static final Image[] WOLF_SPRITES_DOWN = new Image[3];
    private static final Image[] WOLF_SPRITES_LEFT = new Image[3];
    private static final Image[] WOLF_SPRITES_RIGHT = new Image[3];

    // Pour animation
    private int animationFrame = 0;
    private String lastDirection = "down";

    // Chargement des sprites
    static {
        try {
            for (int i = 0; i < 3; i++) {
                WOLF_SPRITES_UP[i] = new Image(Objects.requireNonNull(Wolf.class.getResourceAsStream("/ressources/sprites/animals/wolf/wolf_up_" + i + ".png")));
                WOLF_SPRITES_DOWN[i] = new Image(Objects.requireNonNull(Wolf.class.getResourceAsStream("/ressources/sprites/animals/wolf/wolf_down_" + i + ".png")));
                WOLF_SPRITES_LEFT[i] = new Image(Objects.requireNonNull(Wolf.class.getResourceAsStream("/ressources/sprites/animals/wolf/wolf_left_" + i + ".png")));
                WOLF_SPRITES_RIGHT[i] = new Image(Objects.requireNonNull(Wolf.class.getResourceAsStream("/ressources/sprites/animals/wolf/wolf_right_" + i + ".png")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement des sprites du loup", e);
        }
    }

    // Constructeur
    public Wolf(int row, int col) {
        super(row, col, 2, 1, 5);
    }

    // Récupérer le sprite actuel
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
        return switch (lastDirection) {
            case "up" -> WOLF_SPRITES_UP[animationFrame];
            case "down" -> WOLF_SPRITES_DOWN[animationFrame];
            case "left" -> WOLF_SPRITES_LEFT[animationFrame];
            case "right" -> WOLF_SPRITES_RIGHT[animationFrame];
            default -> WOLF_SPRITES_DOWN[0];
        };
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Pour l'instant que erratique
        Random random = new Random();
        if (random.nextDouble() >= 0.3) {
            return; // Pas de déplacement
        }
        int[] direction = mouvementErratique(mapVivants, grid, row, col);

        // Mise à jour de l'animation si le déplacement a eu lieu
        if (direction != null) {
            lastDirection = getDirectionName(direction);
            animationFrame = (animationFrame + 1) % 3;
        }
    }
}
