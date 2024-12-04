package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class EtreVivant {
    protected int row;
    protected int col;
    private int vitesse; // Vitesse en nombre de cycle
    private int compteurDeplacement = 0; // Pour compter les déplacements
    private int nourriture;
    private int visionRange;

    // Variable pour les directions
    public static final int[][] DIRECTIONS = {
            {-1, 0}, // Haut
            {1, 0},  // Bas
            {0, 1},  // Droite
            {0, -1}  // Gauche
    };

    // Constructeur
    public EtreVivant(int row, int col, int vitesse, int nourriture, int visionRange) {
        this.row = row;
        this.col = col;
        this.vitesse = vitesse;
        this.nourriture = nourriture;
        this.visionRange = visionRange;
    }

    // Getter
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int getVitesse() {
        return vitesse;
    }
    public int getNourriture() {
        return nourriture;
    }
    public int getVisionRange() {
        return visionRange;
    }

    // Setter
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
    public void setNourriture(int nourriture) {
        this.nourriture = nourriture;
    }
    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    // Compteur
    public void incrementerCompteur() {
        compteurDeplacement++;
    }
    public void resetCompteur() {
        compteurDeplacement = 0;
    }
    public boolean doitSeDeplacer() {
        return compteurDeplacement >= vitesse;
    }

    // Méthode pour faire l'update des déplacements (prends en compte le cycle)
    public void updateDeplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col) {
        // On commence par incrémenter le compteur
        incrementerCompteur();

        // On dit si l'être vivant doit se déplacer
        if (doitSeDeplacer()) {
            // On se déplace puis on reset le compteur
            gen_deplacement(mapVivant, grid, row, col);
            resetCompteur();
        }
    }

    // Méthode qui renvoie la liste des êtres vivants autour de l'être vivant avec visionRange (On peux faire varier vision range)
    public List<EtreVivant> getEtreVivantsDansRayon(MapVivant mapVivant, int visionRange) {
        List<EtreVivant> etresVivants = new ArrayList<>();

        int startRow = Math.max(0, getRow() - visionRange);
        int startCol = Math.max(0, getCol() - visionRange);
        int endRow = Math.min(mapVivant.getRows()-1, getRow() + visionRange);
        int endCol = Math.min(mapVivant.getCols()-1, getCol() + visionRange);

        for (int r = startRow; r <= endRow; r++) {
            for (int c = startCol; c <= endCol; c++) {
                double distance = Math.pow(getRow() - r, 2) + Math.pow(getCol() - c, 2);
                if (distance <= Math.pow(visionRange, 2) && r != getRow() && c != getCol()) {
                    EtreVivant vivant = mapVivant.getEtreVivant(r, c);
                    if (vivant != null) {
                        etresVivants.add(vivant);
                    }
                }

            }
        }
        return etresVivants;
    }

    // Méthode qui génére un mouvement érratique
    protected int[] mouvementErratique(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Mélanger les directions pour plus d'aléatoire
        List<int[]> shuffledDirections = Arrays.asList(EtreVivant.DIRECTIONS);
        Collections.shuffle(shuffledDirections);


        for (int[] direction : shuffledDirections) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (deplacerVers(newRow, newCol, mapVivants, grid)) {
                return direction; // Déplacement réussi
            }
        }
        return null;
    }

    // Méthode pour obtenir le nom d'une direction, utile dans l'affichage
    protected String getDirectionName(int[] direction) {
        if (direction == null) {
            return "down";
        }
        if (direction[0] == -1 && direction[1] == 0) return "up";
        if (direction[0] == 1 && direction[1] == 0) return "down";
        if (direction[0] == 0 && direction[1] == -1) return "left";
        if (direction[0] == 0 && direction[1] == 1) return "right";
        return "down"; // Par défaut
    }

    // Méthode abstraite pour obtenir le sprite
    public abstract ImageView getSprite(int tileSize);

    // Méthode générique qui générer les déplacements
    public abstract void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col);

    // Méthode générique qui fais le déplacement et renvoie true si il est valide (sinon ne fais rien et renvoie false)
    public boolean deplacerVers(int newRow, int newCol, MapVivant mapVivants, MapEnvironnement grid) {
        // On vérifie si la position est valide
        if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getCols() && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
            // Déplacer l'être vivant
            mapVivants.setEtreVivant(row, col, null);
            row = newRow;
            col = newCol;
            mapVivants.setEtreVivant(row, col, this);
            return true; // Déplacement réussi
        }
        return false; // Déplacement impossible
    }

}
