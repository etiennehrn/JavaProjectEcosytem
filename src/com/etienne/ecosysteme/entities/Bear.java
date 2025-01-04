package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import java.util.Random;

/**
 * Classe représentant un ours (Bear) dans l'écosystème.
 * Les ours ont un comportement solitaire, gardent leur territoire, et font peur aux autres entités.
 */
public class Bear extends Animaux {

    // Position centrale du territoire de l'ours
    private final int centralRow; // Ligne centrale
    private final int centralCol; // Colonne centrale

    // Générateur aléatoire pour les déplacements erratiques
    private final Random random = new Random();

    /**
     * Constructeur de la classe Bear.
     * Initialise un ours avec une position centrale, une vitesse fixe et une portée de vision spécifique.
     *
     * @param row Ligne initiale de l'ours.
     * @param col Colonne initiale de l'ours.
     */
    public Bear(int row, int col) {
        super(row, col, 40, 1, 10, Type.BEAR); // Appel au constructeur de la classe Animaux
        this.centralRow = row; // Définit la position centrale de l'ours
        this.centralCol = col;
    }

    /**
     * Génère le déplacement de l'ours.
     * L'ours tente d'abord un mouvement circulaire autour de son territoire central.
     * Si aucun mouvement circulaire n'est possible, il adopte un déplacement erratique.
     *
     * @param mapVivants Carte des entités vivantes.
     * @param grid Carte environnementale (MapEnvironnement).
     * @param row Ligne actuelle de l'ours.
     * @param col Colonne actuelle de l'ours.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Déplacement circulaire autour du territoire central
        int[] directionCercle = mouvementCirculaire(mapVivants, grid, this.centralRow, this.centralCol);
        if (directionCercle != null) {
            // Met à jour l'animation de déplacement basée sur la direction
            updateAnimation(parseDirection(directionCercle));
        } else {
            // Si le déplacement circulaire échoue, tente un mouvement erratique
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                // Met à jour l'animation pour le déplacement erratique
                updateAnimation(parseDirection(direction));
            }
        }
    }
}
