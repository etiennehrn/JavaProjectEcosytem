package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Wolf extends Animaux {

    // Constructeur
    public Wolf(int row, int col) {
        super(row, col, 4, 1, 12, Type.WOLF);
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Récupérer les lapins dans le rayon de vision
        List<EtreVivant> lapinsAProximite = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1)
                .stream()
                .filter(e -> e instanceof Bunny)
                .toList();

        if (lapinsAProximite.isEmpty()) {
            // Erratique
            Random random = new Random();
            if (random.nextDouble() >= 0.5) {
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);

            // Mise à jour de l'animation si le déplacement a eu lieu
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
        }
        // Déplacer le loup en fonction du score
        int[] direction = seDeplacerSelonScore(mapVivants, grid, (newRow, newCol) -> calculerScoreDeplacement(newRow, newCol, lapinsAProximite));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    // Calcule le score basé sur la proximité des lapins
    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> lapins) {
        double score = 0;
        for (EtreVivant lapin : lapins) {
            double distance = Math.pow(newRow - lapin.getRow(), 2) + Math.pow(newCol - lapin.getCol(), 2);
            score += 100 / (distance + 1); // Plus la distance est faible, plus le score est élevé
        }
        return score;
    }

    // Mange les lapins à proximités
    public void transformNearByBunny(MapVivant mapVivants) {
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol)) {
                EtreVivant target = mapVivants.getEtreVivant(newRow, newCol);
                if (target instanceof Bunny) {
                    // On mange le lapin
                    mapVivants.setEtreVivant(newRow, newCol, null);
                }
            }
        }
    }

    @Override
    public boolean isMenace(EtreVivant autre) {
        // Les loups sont des menaces pour les cerfs, les lapins et les ours.
        return (autre instanceof Bunny) || (autre instanceof Deer) || (autre instanceof Bear);
    }
}
