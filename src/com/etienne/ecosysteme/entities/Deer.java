package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Deer extends Animaux {
    private static final Image DEER_IMAGE = new Image(Objects.requireNonNull(Deer.class.getResourceAsStream("/ressources/sprites/animals/deer.png")));

    public Deer(int row, int col) {
        super(row, col, 3, 1, 15);
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(DEER_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Cerfs qui ont peur de tout quand c'est trop proche, et qui essaye de rester en groupe
        Random random = new Random();

        // Récupérer tous les êtres vivants dans le rayon de vision
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // Filtrer pour ne garder que les menaces (pas de cerfs ni de lapins)
        List<EtreVivant> menaces = new ArrayList<>();
        for (EtreVivant vivant : vivantsProches) {
            if (!(vivant instanceof Deer || vivant instanceof Bunny)) {
                menaces.add(vivant);
            }
        }
        boolean menacePresente = !menaces.isEmpty();

        // Mouvement erratique si pas de menace, mais avec une proba failbe de bouger
        if (!menacePresente) {
            if (random.nextDouble() >= 0.02) { // 20 % de bouger
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            return;
        }

        // On calcule le meilleur déplacement en fct des menaces et du groupes
        int[] bestDirection = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int[] direction : EtreVivant.DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
                double score = calculerScoreDeplacement(newRow, newCol, menaces, vivantsProches);
                if (score > bestScore) {
                    bestScore = score;
                    bestDirection = direction;
                }
            }
        }

        if (bestDirection != null) {
            deplacerVers(row + bestDirection[0], col + bestDirection[1], mapVivants, grid);
        }
    }

    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> menaces, List<EtreVivant> vivantsProches) {
        double score = 0;

        // On fuit les menaces
        for (EtreVivant menace : menaces) {
            double distance = Math.pow(newRow - menace.getRow(), 2) + Math.pow(newCol - menace.getCol(), 2);
            score -= 40/(distance + 1); // Plus la menace est proche, plus c'est négatif ajuster les coefs
        }

        // Rester en groupe avec les autres
        for (EtreVivant vivant : vivantsProches) {
            if (vivant instanceof Deer) {
                double distance = Math.pow(newRow - vivant.getRow(), 2) + Math.pow(newCol - vivant.getCol(), 2);
                score += 1/(distance + 1); // Plus le cerf est proche, plus c'est positif
            }
        }

        return score;
    }
}
