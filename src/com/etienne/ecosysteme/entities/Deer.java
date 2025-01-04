package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import java.util.*;

/**
 * Classe représentant un cerf (Deer) dans l'écosystème.
 * Les cerfs fuient les menaces proches tout en essayant de se regrouper avec d'autres cerfs.
 */
public class Deer extends Animaux {

    /**
     * Constructeur de la classe Deer.
     * Initialise un cerf avec sa position, sa vitesse et sa portée de vision.
     *
     * @param row Ligne initiale du cerf.
     * @param col Colonne initiale du cerf.
     */
    public Deer(int row, int col) {
        super(row, col, 3, 1, 15, Type.DEER); // Vitesse modérée, portée de vision étendue
    }

    /**
     * Génère le déplacement du cerf.
     * Si aucune menace n'est détectée, le cerf bouge rarement et de manière erratique.
     * En présence de menaces, il cherche à maximiser l'éloignement des menaces tout en se regroupant avec d'autres cerfs.
     *
     * @param mapVivants Carte des entités vivantes.
     * @param grid Carte environnementale (MapEnvironnement).
     * @param row Ligne actuelle du cerf.
     * @param col Colonne actuelle du cerf.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        Random random = new Random();

        // Récupère les êtres vivants dans le rayon de vision
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // Filtre pour ne garder que les menaces
        List<EtreVivant> menaces = vivantsProches.stream()
                .filter(vivant -> vivant.isMenace(this))
                .toList();

        // Si aucune menace n'est détectée
        if (menaces.isEmpty()) {
            // Faible probabilité de bouger (1%)
            if (random.nextDouble() >= 0.01) {
                return; // Pas de déplacement
            }

            // Déplacement erratique (aléatoire)
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // Si des menaces sont détectées, calcule un déplacement optimal
        int[] direction = seDeplacerSelonScore(mapVivants, grid, (newRow, newCol) ->
                calculerScoreDeplacement(newRow, newCol, menaces, vivantsProches));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    /**
     * Calcule le score total pour le déplacement d'un cerf.
     * Combine l'éloignement des menaces et le regroupement avec d'autres cerfs.
     *
     * @param newRow Nouvelle ligne potentielle.
     * @param newCol Nouvelle colonne potentielle.
     * @param menaces Liste des menaces détectées.
     * @param vivantsProches Liste des êtres vivants proches.
     * @return Score total pour le déplacement.
     */
    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> menaces, List<EtreVivant> vivantsProches) {
        double score = 0;
        score += scorePourFuirMenaces(newRow, newCol, menaces);
        score += scorePourSeRegrouper(newRow, newCol, vivantsProches);
        return score;
    }

    /**
     * Calcule le score basé sur l'éloignement des menaces.
     * Le score est négatif pour favoriser l'éloignement.
     *
     * @param newRow Nouvelle ligne potentielle.
     * @param newCol Nouvelle colonne potentielle.
     * @param menaces Liste des menaces détectées.
     * @return Score pour l'éloignement des menaces.
     */
    private double scorePourFuirMenaces(int newRow, int newCol, List<EtreVivant> menaces) {
        double score = 0;
        for (EtreVivant menace : menaces) {
            double distance = Math.pow(newRow - menace.getRow(), 2) + Math.pow(newCol - menace.getCol(), 2);
            score -= 70 / (distance + 1); // Coefficient ajustable pour l'impact des menaces
        }
        return score;
    }

    /**
     * Calcule le score pour se regrouper avec d'autres cerfs.
     * Favorise les positions proches d'autres cerfs.
     *
     * @param newRow Nouvelle ligne potentielle.
     * @param newCol Nouvelle colonne potentielle.
     * @param vivantsProches Liste des êtres vivants proches.
     * @return Score pour le regroupement.
     */
    private double scorePourSeRegrouper(int newRow, int newCol, List<EtreVivant> vivantsProches) {
        double score = 0;
        for (EtreVivant vivant : vivantsProches) {
            if (vivant instanceof Deer) { // Vérifie si l'entité est un cerf
                double distance = Math.pow(newRow - vivant.getRow(), 2) + Math.pow(newCol - vivant.getCol(), 2);
                score += 1 / (distance + 1); // Coefficient ajustable pour le regroupement
            }
        }
        return score;
    }

    /**
     * Les cerfs ne considèrent aucune entité comme une menace.
     *
     * @param autre Autre être vivant.
     * @return false Toujours faux.
     */
    @Override
    public boolean isMenace(EtreVivant autre) {
        return false;
    }
}
