package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Classe représentant un loup (Wolf) dans l'écosystème.
 * Cette classe hérite de la classe Animaux et implémente des comportements spécifiques
 * tels que la chasse aux lapins, la recherche et les déplacements.
 */
public class Wolf extends Animaux {

    /**
     * Constructeur de la classe Wolf.
     *
     * @param row La ligne initiale où le loup est placé sur la carte.
     * @param col La colonne initiale où le loup est placé sur la carte.
     */
    public Wolf(int row, int col) {
        // Appelle le constructeur parent avec les attributs spécifiques au loup.
        // Paramètres : position (row, col), points de vie (5), vitesse (1),
        // vision (12), et le type d'animal (WOLF).
        super(row, col, 5, 1, 12, Type.WOLF);
    }

    /**
     * Génère les déplacements du loup en fonction de son environnement et de ses cibles potentielles.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La carte de l'environnement.
     * @param row          La ligne actuelle du loup.
     * @param col          La colonne actuelle du loup.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Récupère les lapins dans le rayon de vision du loup.
        List<EtreVivant> lapinsAProximite = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1)
                .stream()
                .filter(e -> e instanceof Bunny) // Filtre uniquement les lapins.
                .toList();

        if (lapinsAProximite.isEmpty()) {
            // Si aucun lapin n'est proche, le loup effectue une recherche active.
            int[] directionRecherche = rechercheActive(mapVivants, grid);

            if (directionRecherche != null) {
                // Met à jour l'animation pour refléter la direction.
                updateAnimation(parseDirection(directionRecherche));
                return;
            }

            // Sinon, effectue un mouvement erratique.
            Random random = new Random();
            if (random.nextDouble() >= 0.5) {
                return; // Pas de déplacement.
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);

            // Met à jour l'animation si un déplacement a eu lieu.
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // Si des lapins sont proches, le loup se déplace en fonction du score attribué aux positions.
        int[] direction = seDeplacerSelonScore(mapVivants, grid,
                (newRow, newCol) -> calculerScoreDeplacement(newRow, newCol, lapinsAProximite));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    /**
     * Calcule le score d'une position en fonction de la proximité des lapins.
     * Plus un lapin est proche, plus le score de la position est élevé.
     *
     * @param newRow La ligne de la position cible.
     * @param newCol La colonne de la position cible.
     * @param lapins La liste des lapins à proximité.
     * @return Le score calculé pour la position.
     */
    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> lapins) {
        double score = 0;
        for (EtreVivant lapin : lapins) {
            double distance = Math.pow(newRow - lapin.getRow(), 2) + Math.pow(newCol - lapin.getCol(), 2);
            score += 100 / (distance + 1); // Inversement proportionnel à la distance (évite division par zéro).
        }
        return score;
    }

    /**
     * Transforme les lapins proches en espaces vides (simulation de prédation).
     *
     * @param mapVivants La carte des entités vivantes.
     */
    public void transformNearByBunny(MapVivant mapVivants) {
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol)) {
                EtreVivant target = mapVivants.getEtreVivant(newRow, newCol);
                if (target instanceof Bunny) {
                    // Supprime le lapin de la carte (simulation de la chasse).
                    mapVivants.setEtreVivant(newRow, newCol, null);
                }
            }
        }
    }

    /**
     * Détermine si le loup représente une menace pour une autre entité.
     * Les loups sont des menaces pour les lapins, les cerfs et les ours.
     *
     * @param autre L'entité à comparer.
     * @return `true` si le loup est une menace pour l'entité, sinon `false`.
     */
    @Override
    public boolean isMenace(EtreVivant autre) {
        return (autre instanceof Bunny) || (autre instanceof Deer) || (autre instanceof Bear);
    }
}
