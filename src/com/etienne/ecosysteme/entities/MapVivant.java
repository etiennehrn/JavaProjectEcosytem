package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.core.DayNightCycleImpl;
import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.io.IOException;

public class MapVivant implements IMapVivant{
    private EtreVivant[][] mapVivants;
    private int rows;
    private int cols;

    // Constructeur
    public MapVivant(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        mapVivants = new EtreVivant[rows][cols];
    }

    @Override
    public void populate(String filePath, int nbHumains, int nbZombies, int nbAnimaux, MapEnvironnement map) throws IOException {
        PopulateUtil.loadFromFile(filePath, map, this);
        PopulateUtil.populateRandomly(this, map, nbHumains, nbZombies, nbAnimaux);
    }

    @Override
    public void update(MapEnvironnement grid, DayNightCycleImpl dayNightCycle) {

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

                // Déplacement de chaque être vivant (la prise en compte réelle est dans le cylce)
                vivant.updateDeplacement(this, grid, row, col, dayNightCycle);

                // Actions spécifiques

                // Zombie mange humain
                if (vivant instanceof Zombie zombie) {
                    zombie.transformNearbyHumans(this);
                }
                // Loup mange lapin
                if (vivant instanceof Wolf wolf) {
                    wolf.transformNearByBunny(this);
                }

            }
        }
    }

    @Override
    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }

    @Override
    public EtreVivant getEtreVivant(int row, int col) {
        return mapVivants[row][col];
    }

    @Override
    public void setEtreVivant(int row, int col, EtreVivant etre) {
        mapVivants[row][col] = etre;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    public void setCols(int cols) {
        this.cols = cols;
    }

}
