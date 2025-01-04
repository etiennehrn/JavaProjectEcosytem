package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Classe représentant un lapin (Bunny) dans l'écosystème.
 * Les lapins sont rapides et fuient les menaces potentielles (par exemple, loups ou ours).
 */
public class Bunny extends Animaux {

    /**
     * Constructeur de la classe Bunny.
     * Initialise un lapin avec sa position, sa vitesse et sa portée de vision.
     *
     * @param row Ligne initiale du lapin.
     * @param col Colonne initiale du lapin.
     */
    public Bunny(int row, int col) {
        super(row, col, 10, 1, 5, Type.BUNNY); // Vitesse rapide, portée de vision courte.
    }

    /**
     * Génère le déplacement du lapin.
     * Si aucune menace n'est détectée, le lapin bouge rarement et de manière erratique.
     * En présence de menaces, le lapin cherche à maximiser la distance avec celles-ci.
     *
     * @param mapVivants Carte des entités vivantes.
     * @param grid Carte environnementale (MapEnvironnement).
     * @param row Ligne actuelle du lapin.
     * @param col Colonne actuelle du lapin.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        Random random = new Random();

        // Récupère tous les êtres vivants dans le rayon de vision du lapin
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // Filtre pour ne garder que les menaces (par exemple, loups, ours)
        List<EtreVivant> menaces = vivantsProches.stream()
                .filter(vivant -> vivant.isMenace(this))
                .toList();

        // Si aucune menace n'est détectée
        if (menaces.isEmpty()) {
            // Faible probabilité de bouger (10%)
            if (random.nextDouble() >= 0.1) {
                return; // Le lapin ne bouge pas
            }

            // Déplacement erratique (aléatoire) si aucune menace
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // Si des menaces sont détectées, le lapin cherche à maximiser la distance avec elles
        int[] direction = seDeplacerSelonScore(mapVivants, grid, calculerScoreLapin(menaces));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    /**
     * Définit une fonction de calcul de score pour les déplacements du lapin.
     * Le score est basé sur la distance par rapport aux menaces détectées :
     * le lapin privilégie les positions les plus éloignées des menaces.
     *
     * @param menaces Liste des menaces (loups, ours, etc.).
     * @return Une fonction de calcul du score pour les positions.
     */
    private BiFunction<Integer, Integer, Double> calculerScoreLapin(List<EtreVivant> menaces) {
        return (newRow, newCol) -> {
            double score = 0;

            // Maximiser la distance par rapport à chaque menace
            for (EtreVivant menace : menaces) {
                double distance = Math.pow(newCol - menace.getCol(), 2) + Math.pow(newRow - menace.getRow(), 2);
                score += distance; // Plus la distance est grande, plus le score est élevé
            }

            return score;
        };
    }
}
