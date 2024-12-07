package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Pig extends Animaux {
    private static final Image PIG_IMAGE = new Image(Objects.requireNonNull(Pig.class.getResourceAsStream("/ressources/sprites/animals/boar.png")));

    public Pig(int row, int col) {
        super(row, col, 10, 1, 5, Type.PIG);
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(PIG_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // pas de déplacement pour l'instant
    }
}
