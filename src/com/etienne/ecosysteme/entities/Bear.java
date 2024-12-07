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
        super(row, col, 30, 1, 5, Type.BEAR);
        this.centralRow = row;
        this.centralCol = col;
    }


    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Sinon, mouvement lent et circulaire
        mouvementLentCirculaire(mapVivants, grid, row, col);
    }

    // Mouvement lent et circulaire autour de la zone centrale
    private void mouvementLentCirculaire(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Générer un mouvement légèrement circulaire avec un peu d'aléatoire
        int verticalStep = random.nextBoolean() ? 1 : -1;
        int horizontalStep = random.nextBoolean() ? 1 : -1;

        // Favoriser les déplacements vers la zone centrale
        if (random.nextDouble() > 0.7) {
            verticalStep = Integer.signum(centralRow - getRow());
        }
        if (random.nextDouble() > 0.7) {
            horizontalStep = Integer.signum(centralCol - getCol());
        }

        // Essayer le déplacement
        if (!deplacerVers(row + verticalStep, col + horizontalStep, mapVivants, grid)) {
            // Si échoue, mouvement aléatoire
            int[] randomDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            if (deplacerVers(row + randomDirection[0], col + randomDirection[1], mapVivants, grid))
            {
                if (randomDirection != null) {
                    updateAnimation(parseDirection(randomDirection));
                }
            }

        }
    }

}
