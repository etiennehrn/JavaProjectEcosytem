package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import com.etienne.ecosysteme.environment.MapVivant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Boar extends Animaux {
    private static final Image BOAR_IMAGE = new Image(Objects.requireNonNull(Boar.class.getResourceAsStream("/ressources/sprites/animals/boar.png")));

    public Boar(int row, int col) {
        super(row, col, 10, 1, 5);
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(BOAR_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // pas de déplacement pour l'instant
    }
}
