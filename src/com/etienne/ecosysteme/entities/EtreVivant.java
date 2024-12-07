package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;



/**
 * Classe abstraite représentant un être vivant dans la simulation d'écosystème.
 *
 * <p>Un {@code EtreVivant} est un élément mobile de la simulation, caractérisé par :
 * <ul>
 *   <li>Une position dans une grille (ligne et colonne).</li>
 *   <li>Une vitesse déterminant la fréquence des déplacements.</li>
 *   <li>Un compteur de déplacement pour l'affichage des sprites.</li>
 *   <li>Un rayon de vision permettant de détecter les autres êtres vivants à proximité.</li>
 *   <li>Une quantité de nourriture qu'il peut consommer ou utiliser.</li>
 * </ul>
 * </p>
 *
 * <p>Cette classe sert de base pour implémenter différents types d'êtres vivants,
 * chacun ayant des comportements spécifiques définis dans les sous-classes.
 * Elle gère les aspects communs comme les déplacements, la gestion des ressources,
 * et l'interaction avec l'environnement.</p>
 *
 * <p>Les classes dérivées doivent implémenter les méthodes abstraites suivantes :
 * <ul>
 *   <li>{@link #getSprite(int)} : retourne l'apparence graphique de l'être vivant.</li>
 *   <li>{@link #gen_deplacement(MapVivant, MapEnvironnement, int, int)} : génère le comportement de déplacement spécifique.</li>
 * </ul>
 * </p>
 *
 */

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

    /**
     * Constructeur de la classe {@code EtreVivant}.
     *
     * <p>Initialise un être vivant avec les attributs spécifiés, notamment sa position,
     * sa vitesse de déplacement, sa quantité de nourriture, et son rayon de vision.</p>
     *
     * @param row          la position initiale en ligne de l'être vivant
     * @param col          la position initiale en colonne de l'être vivant
     * @param vitesse      la vitesse de déplacement, exprimée en cycles nécessaires pour un déplacement
     * @param nourriture   la quantité initiale de nourriture disponible pour cet être vivant
     * @param visionRange  le rayon de vision de l'être vivant, exprimé en unités de grille
     */
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

    /**
     * Incrémente le compteur de déplacement de l'être vivant.
     *
     * <p>Cette méthode est utilisée pour suivre le nombre de cycles écoulés depuis
     * le dernier déplacement. Lorsque le compteur atteint la vitesse définie,
     * l'être vivant peut se déplacer.</p>
     */
    public void incrementerCompteur() {
        compteurDeplacement++;
    }

    /**
     * Réinitialise le compteur de déplacement de l'être vivant.
     *
     * <p>Cette méthode est appelée après qu'un déplacement a été effectué
     * pour recommencer le suivi des cycles nécessaires avant le prochain déplacement.</p>
     */
    public void resetCompteur() {
        compteurDeplacement = 0;
    }

    /**
     * Vérifie si l'être vivant doit se déplacer.
     *
     * @return {@code true} si le compteur de déplacement est supérieur ou égal
     *         à la vitesse définie, sinon {@code false}
     */
    public boolean doitSeDeplacer() {
        return compteurDeplacement >= vitesse;
    }


    /**
     * Met à jour le déplacement de l'être vivant en fonction du cycle actuel.
     *
     * <p>Cette méthode gère l'incrémentation du compteur de déplacement et détermine
     * si l'être vivant doit se déplacer en fonction de sa vitesse. Si un déplacement
     * est nécessaire, elle appelle la méthode abstraite {@link #gen_deplacement(MapVivant, MapEnvironnement, int, int)}
     * pour générer un mouvement spécifique, puis réinitialise le compteur.</p>
     *
     * @param mapVivant   la carte contenant tous les êtres vivants
     * @param grid        l'environnement représentant la grille de simulation
     * @param row         la ligne cible pour le déplacement
     * @param col         la colonne cible pour le déplacement
     */
    public void updateDeplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col) {
        // On commence par incrémenter le compteur
        incrementerCompteur();

        // On dit si l'être vivant doit se déplacer
        if (doitSeDeplacer()) {
            // On se déplace puis on réinitialise le compteur
            gen_deplacement(mapVivant, grid, row, col);
            resetCompteur();
        }
    }

    /**
     * Récupère une liste des êtres vivants situés dans un rayon défini autour de cet être vivant,
     * en tenant compte des obstacles et de la distance maximale de perception.
     *
     * @param mapVivant   la carte contenant tous les êtres vivants
     * @param visionRange le rayon dans lequel rechercher les autres êtres vivants
     * @param maxDistance la distance maximale à laquelle un être vivant peut être détecté
     * @return une liste des êtres vivants visibles ou détectables dans le rayon
     */

    public List<EtreVivant> getEtreVivantsDansRayon(MapVivant mapVivant, MapEnvironnement grid, int visionRange, double maxDistance) {
        List<EtreVivant> etresVivants = new ArrayList<>();

        int startRow = Math.max(0, getRow() - visionRange);
        int startCol = Math.max(0, getCol() - visionRange);
        int endRow = Math.min(mapVivant.getRows()-1, getRow() + visionRange);
        int endCol = Math.min(mapVivant.getCols()-1, getCol() + visionRange);

        for (int r = startRow; r <= endRow; r++) {
            for (int c = startCol; c <= endCol; c++) {
                double distance = Math.pow(getRow() - r, 2) + Math.pow(getCol() - c, 2);
                if (distance <= Math.pow(visionRange, 2)) {
                    EtreVivant vivant = mapVivant.getEtreVivant(r, c);
                    if (vivant != null && vivant != this && MapEnvironnement.isPathClear(grid, getRow(), getCol(), r, c, maxDistance)) {
                        etresVivants.add(vivant);
                    }
                }

            }
        }
        return etresVivants;
    }

    /**
     * Calcule et effectue un déplacement basé sur un score attribué à chaque direction possible.
     * ATTENTION ON MAXIMISE LE SCORE
     *
     * @param mapVivants    la carte des êtres vivants
     * @param grid          l'environnement de la simulation
     * @param vivantsAConsidérer la liste des êtres vivants à prendre en compte pour le calcul du score
     * @param calculerScore une fonction qui calcule un score pour une position donnée
     */
    protected int[] seDeplacerSelonScore(MapVivant mapVivants, MapEnvironnement grid, List<EtreVivant> vivantsAConsidérer, BiFunction<Integer, Integer, Double> calculerScore) {
        int[] bestDirection = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int[] direction : DIRECTIONS) {
            int newRow = getRow() + direction[0];
            int newCol = getCol() + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
                // On calcule le score pour cette position
                double score = calculerScore.apply(newRow, newCol);
                if (score > bestScore) {
                    bestScore = score;
                    bestDirection = direction;
                }
            }
        }

        // Se déplacer dans la meilleure direction trouvée
        if (bestDirection != null) {
            if(deplacerVers(row + bestDirection[0], col + bestDirection[1], mapVivants, grid))
            {
                return bestDirection;
            }
        }
        return null;
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

    // Méthode générique qui fait le déplacement et renvoie true s'il est valide (sinon ne fais rien et renvoie false)
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

    /**
     * Vérifie si cet être vivant est une menace pour un autre être vivant.
     *
     * @param autre l'autre être vivant à considérer
     * @return {@code true} si cet être vivant est une menace, {@code false} sinon
     */
    public boolean isMenace(EtreVivant autre) {
        // Par défaut, un être vivant n'est pas une menace
        return false;
    }

}
