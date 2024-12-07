package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.function.BiFunction;

public class Bunny extends Animaux {

    public Bunny(int row, int col) {
        super(row, col, 1, 1, 5, Type.BUNNY); // Vitesse 2 pour les lapins, ils sont rapide les fous
    }


    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Lapins qui font les tapettes en gros (sauf pour les zombies et les autres lapins), et ils sont très rapide pour fuir
        Random random = new Random();

        // Récupérer tous les êtres vivants dans le rayon de vision
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // On filtre pour ne garder que les menaces
        List<EtreVivant> menaces = vivantsProches.stream().filter(vivant -> vivant.isMenace(this)).toList();
        // Mouvement erratique si pas de menace, très faible proba de bouger
        if (menaces.isEmpty()) {
            if (random.nextDouble() >= 0.1) {
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // On calcule le déplacement en fonction des menaces
        int[] direction = seDeplacerSelonScore(mapVivants, grid, calculerScoreLapin(menaces));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    // On définit une biFunction pour calculer le score pour les déplacements, c'est ici qu'on pourra mettre des comportements spécifiques.
    private BiFunction<Integer, Integer, Double> calculerScoreLapin(List<EtreVivant> menaces) {
        return (newRow, newCol) -> {
            double score = 0;

            // On maximise la distance par rapport aux menaces
            for (EtreVivant menace : menaces) {
                double distance = Math.pow(newCol - menace.getCol(), 2) + Math.pow(newRow-menace.getRow(), 2);
                score += distance;
            }
            return score;
        };
    }

}
