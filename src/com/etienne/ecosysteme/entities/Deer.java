package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Deer extends Animaux {

    public Deer(int row, int col) {
        super(row, col, 3, 1, 15, Type.DEER);
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Cerfs qui ont peur de tout quand c'est trop proche, et qui essaye de rester en groupe
        Random random = new Random();

        // Récupérer tous les êtres vivants dans le rayon de vision
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // Filtrer pour ne garder que les menaces
        List<EtreVivant> menaces = vivantsProches.stream().filter(vivant -> vivant.isMenace(this)).toList();


        // Mouvement erratique si pas de menace, mais avec une probabilité faible de bouger
        if (menaces.isEmpty()) {
            if (random.nextDouble() >= 0.01) { // 20 % de bouger
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        int[] direction = seDeplacerSelonScore(mapVivants, grid, vivantsProches, (newRow, newCol) -> calculerScoreDeplacement(newRow, newCol, menaces, vivantsProches));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    // Calcule le score total pour le déplacement
    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> menaces, List<EtreVivant> vivantsProches) {
        double score = 0;
        score += scorePourFuirMenaces(newRow, newCol, menaces);
        score += scorePourSeRegrouper(newRow, newCol, vivantsProches);

        return score;
    }

    // Calcule le score basé sur l'éloignement des menaces
    private double scorePourFuirMenaces(int newRow, int newCol, List<EtreVivant> menaces) {
        double score = 0;
        for (EtreVivant menace : menaces) {
            double distance = Math.pow(newRow - menace.getRow(), 2) + Math.pow(newCol - menace.getCol(), 2);
            score -= 70 / (distance + 1); // Ajuster le coefficient selon le comportement souhaité
        }
        return score;
    }

    // Ici pour se regrouper
    private double scorePourSeRegrouper(int newRow, int newCol, List<EtreVivant> vivantsProches) {
        double score = 0;
        for (EtreVivant vivant : vivantsProches) {
            if (vivant instanceof Deer) {
                double distance = Math.pow(newRow - vivant.getRow(), 2) + Math.pow(newCol - vivant.getCol(), 2);
                score += 1 / (distance + 1); // Ajuster le coefficient selon le comportement souhaité
            }
        }
        return score;
    }

    @Override
    public boolean isMenace(EtreVivant autre) {
        return false;
    }
}
