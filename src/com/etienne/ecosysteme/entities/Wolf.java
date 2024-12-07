package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Random;

public class Wolf extends Animaux {

    // Constructeur
    public Wolf(int row, int col) {
        super(row, col, 2, 1, 5, Type.WOLF);
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Pour l'instant qu'erratique
        Random random = new Random();
        if (random.nextDouble() >= 0.9) {
            return; // Pas de déplacement
        }
        int[] direction = mouvementErratique(mapVivants, grid, row, col);

        // Mise à jour de l'animation si le déplacement a eu lieu
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    @Override
    public boolean isMenace(EtreVivant autre) {
        // Les loups sont des menaces pour les cerfs, les lapins et les ours.
        return (autre instanceof Bunny) || (autre instanceof Deer) || (autre instanceof Bear);
    }
}
