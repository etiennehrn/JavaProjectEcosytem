package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Bear extends Animaux {
    // Les ours ont un comportement solitaire, ils font peur aux autres et garde leur terrain

    // Position centrale Ours
    private final int centralRow;
    private final int centralCol;

    private final Random random = new Random();

    public Bear(int row, int col) {
        super(row, col, 1, 1, 5, Type.BEAR);
        this.centralRow = row;
        this.centralCol = col;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        int[] directionCercle = mouvementCirculaire(mapVivants, grid, this.centralRow, this.centralCol);

        if (directionCercle != null) {
            updateAnimation(parseDirection(directionCercle));
        }
        else {
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
        }
    }
}
