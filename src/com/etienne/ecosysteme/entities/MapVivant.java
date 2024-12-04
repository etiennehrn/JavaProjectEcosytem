package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.io.IOException;

public class MapVivant {
    private EtreVivant[][] mapVivants;
    private int rows;
    private int cols;

    // Constructeur
    public MapVivant(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        mapVivants = new EtreVivant[rows][cols];
    }

    // On mets des humains et des zombies et des animaux sur la carte et on charge la map_vivant de base
    public void populate(String filePath, int nbHumains, int nbZombies, int nbAnimaux, MapEnvironnement map) throws IOException {
        // Groupe de bitches pour étudier en haut à droite
        PopulateUtil.loadFromFile(filePath, map, this);
        PopulateUtil.populateRandomly(this, map, nbHumains, nbZombies, nbAnimaux);
    }

    // On fais la mise à jour des déplacements
    public void update(MapEnvironnement grid) {

        // Copier temporairement la carte pour éviter les conflits lors d'un cycle
        EtreVivant[][] tempMap = new EtreVivant[grid.getRows()][grid.getCols()];
        for (int row = 0; row < grid.getRows(); row++) {
            if (grid.getCols() >= 0) System.arraycopy(mapVivants[row], 0, tempMap[row], 0, grid.getCols());
        }

        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                EtreVivant vivant = tempMap[row][col];

                // Case vide
                if (vivant == null) {
                    continue;
                }

                // Déplacement de chaque être vivants (la prise en compte réelle est dans le cylce)
                vivant.updateDeplacement(this, grid, row, col);

                // Actions spécifiques
                if (vivant instanceof Zombie zombie) {
                    zombie.transformNearbyHumans(this);
                }
            }
        }
    }

    // Pour savoir si l'etre vivant à row et bound est bien sur la carte
    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }

    // Getter et setter
    public EtreVivant getEtreVivant(int row, int col) {
        return mapVivants[row][col];
    }
    public void setEtreVivant(int row, int col, EtreVivant etre) {
        mapVivants[row][col] = etre;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public int getCols() {
        return cols;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }

}
