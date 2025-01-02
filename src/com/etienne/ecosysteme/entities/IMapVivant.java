package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.core.DayNightCycleImpl;
import com.etienne.ecosysteme.environment.MapEnvironnement;
import java.io.IOException;

public interface IMapVivant {

    /**
     * Ajoute des entités (humains, zombies, animaux) sur la carte.
     *
     * @param filePath Chemin vers le fichier de configuration.
     * @param nbHumains Nombre d'humains à ajouter.
     * @param nbZombies Nombre de zombies à ajouter.
     * @param nbAnimaux Nombre d'animaux à ajouter.
     * @param map Carte de l'environnement.
     * @throws IOException En cas d'erreur lors de la lecture du fichier.
     */
    void populate(String filePath, int nbHumains, int nbZombies, int nbAnimaux, MapEnvironnement map) throws IOException;

    /**
     * Met à jour les déplacements et interactions des entités sur la carte.
     *
     * @param grid          Carte de l'environnement.
     * @param dayNightCycle Cycle jour/nuit.
     */
    void update(MapEnvironnement grid, DayNightCycleImpl dayNightCycle);

    /**
     * Vérifie si une position (row, col) est dans les limites de la carte.
     *
     * @param row Ligne à vérifier.
     * @param col Colonne à vérifier.
     * @return `true` si la position est valide, `false` sinon.
     */
    boolean isWithinBounds(int row, int col);

    /**
     * Obtient une entité vivante à une position donnée.
     *
     * @param row Ligne de la position.
     * @param col Colonne de la position.
     * @return L'entité vivante à cette position, ou `null` si aucune entité n'est présente.
     */
    EtreVivant getEtreVivant(int row, int col);

    /**
     * Place une entité vivante à une position donnée.
     *
     * @param row Ligne de la position.
     * @param col Colonne de la position.
     * @param etre L'entité vivante à placer.
     */
    void setEtreVivant(int row, int col, EtreVivant etre);

    /**
     * Obtient le nombre de lignes de la carte.
     *
     * @return Le nombre de lignes.
     */
    int getRows();

    /**
     * Définit le nombre de lignes de la carte.
     *
     * @param rows Le nombre de lignes.
     */
    void setRows(int rows);

    /**
     * Obtient le nombre de colonnes de la carte.
     *
     * @return Le nombre de colonnes.
     */
    int getCols();

    /**
     * Définit le nombre de colonnes de la carte.
     *
     * @param cols Le nombre de colonnes.
     */
    void setCols(int cols);
}
