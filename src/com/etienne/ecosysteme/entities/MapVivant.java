package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.core.DayNightCycleImpl;
import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.io.IOException;

/**
 * Classe représentant la carte des êtres vivants (MapVivant) dans l'écosystème.
 * Elle gère la population des entités, leur mise à jour et leurs interactions.
 */
public class MapVivant implements IMapVivant {

    // Matrice des êtres vivants sur la carte.
    private EtreVivant[][] mapVivants;
    private int rows; // Nombre de lignes de la carte.
    private int cols; // Nombre de colonnes de la carte.

    /**
     * Constructeur de la classe MapVivant.
     *
     * @param rows Nombre de lignes de la carte.
     * @param cols Nombre de colonnes de la carte.
     */
    public MapVivant(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        mapVivants = new EtreVivant[rows][cols];
    }

    /**
     * Remplit la carte avec des êtres vivants depuis un fichier et en ajoute aléatoirement.
     *
     * @param filePath   Chemin du fichier contenant les données initiales.
     * @param nbHumains  Nombre d'humains à ajouter.
     * @param nbZombies  Nombre de zombies à ajouter.
     * @param nbAnimaux  Nombre d'animaux à ajouter.
     * @param map        Carte de l'environnement.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    @Override
    public void populate(String filePath, int nbHumains, int nbZombies, int nbAnimaux, MapEnvironnement map) throws IOException {
        // Chargement des êtres vivants depuis un fichier.
        PopulateUtil.loadFromFile(filePath, map, this);

        // Ajout aléatoire d'autres entités.
        PopulateUtil.populateRandomly(this, map, nbHumains, nbZombies, nbAnimaux);
    }

    /**
     * Met à jour les positions et les interactions des êtres vivants sur la carte.
     *
     * @param grid           Carte de l'environnement.
     * @param dayNightCycle  Cycle jour/nuit en cours.
     */
    @Override
    public void update(MapEnvironnement grid, DayNightCycleImpl dayNightCycle) {

        // Création d'une copie temporaire de la carte pour éviter les conflits.
        EtreVivant[][] tempMap = new EtreVivant[grid.getRows()][grid.getCols()];
        for (int row = 0; row < grid.getRows(); row++) {
            if (grid.getCols() >= 0) {
                System.arraycopy(mapVivants[row], 0, tempMap[row], 0, grid.getCols());
            }
        }

        // Parcourt chaque cellule de la carte.
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                EtreVivant vivant = tempMap[row][col];

                // Ignore les cases vides.
                if (vivant == null) {
                    continue;
                }

                // Met à jour le déplacement de l'être vivant.
                vivant.updateDeplacement(this, grid, row, col, dayNightCycle);

                // Gestion des interactions spécifiques :
                // - Les zombies transforment les humains proches.
                if (vivant instanceof Zombie zombie) {
                    zombie.transformNearbyHumans(this);
                }

                // - Les loups mangent les lapins proches.
                if (vivant instanceof Wolf wolf) {
                    wolf.transformNearByBunny(this);
                }
            }
        }
    }

    /**
     * Vérifie si une position est dans les limites de la carte.
     *
     * @param row Ligne à vérifier.
     * @param col Colonne à vérifier.
     * @return `true` si la position est dans les limites, sinon `false`.
     */
    @Override
    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }

    /**
     * Retourne l'être vivant présent à une position donnée.
     *
     * @param row Ligne de la position.
     * @param col Colonne de la position.
     * @return L'être vivant présent, ou `null` si la case est vide.
     */
    @Override
    public EtreVivant getEtreVivant(int row, int col) {
        return mapVivants[row][col];
    }

    /**
     * Définit un être vivant à une position donnée.
     *
     * @param row  Ligne de la position.
     * @param col  Colonne de la position.
     * @param etre L'être vivant à placer (ou `null` pour libérer la case).
     */
    @Override
    public void setEtreVivant(int row, int col, EtreVivant etre) {
        mapVivants[row][col] = etre;
    }

    /**
     * Retourne le nombre de lignes de la carte.
     *
     * @return Le nombre de lignes.
     */
    @Override
    public int getRows() {
        return rows;
    }

    /**
     * Définit le nombre de lignes de la carte.
     *
     * @param rows Le nouveau nombre de lignes.
     */
    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     *
     * @return Le nombre de colonnes.
     */
    @Override
    public int getCols() {
        return cols;
    }

    /**
     * Définit le nombre de colonnes de la carte.
     *
     * @param cols Le nouveau nombre de colonnes.
     */
    @Override
    public void setCols(int cols) {
        this.cols = cols;
    }
}
