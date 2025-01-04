package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.core.DayNightCycleImpl;
import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Classe abstraite représentant un être vivant dans la simulation d'écosystème.
 *
 * <p>Un {@code EtreVivant} est un élément mobile de la simulation, caractérisé par :</p>
 * <ul>
 *   <li>Une position dans une grille (ligne et colonne).</li>
 *   <li>Une vitesse de déplacement.</li>
 *   <li>Un compteur pour l'affichage des animations.</li>
 *   <li>Un rayon de vision permettant de détecter les entités à proximité.</li>
 *   <li>Une quantité de nourriture gérant son état de survie.</li>
 * </ul>
 *
 * <p>Les sous-classes doivent implémenter :</p>
 * <ul>
 *   <li>{@link #getSprite(int)} : retourne l'apparence graphique de l'entité.</li>
 *   <li>{@link #gen_deplacement(MapVivant, MapEnvironnement, int, int)} : génère le comportement de déplacement.</li>
 * </ul>
 */
public abstract class EtreVivant implements IDeplacement {

    // Position de l'entité sur la carte
    protected int row;
    protected int col;

    // Variables pour les déplacements
    private int directionRow; // Direction actuelle (ligne)
    private int directionCol; // Direction actuelle (colonne)
    private int stepsCurrentDir; // Nombre de pas dans la direction actuelle

    // Caractéristiques de l'entité
    private int vitesse; // Fréquence des déplacements (en cycles)
    private final CompteurDeplacement compteurDeplacement; // Compteur pour gérer les cycles de déplacement
    private int nourriture; // Quantité de nourriture disponible
    private int visionRange; // Rayon de vision (en unités de grille)

    // Directions possibles pour les déplacements
    public static final int[][] DIRECTIONS = {
            {-1, 0}, // Haut
            {1, 0},  // Bas
            {0, 1},  // Droite
            {0, -1}  // Gauche
    };

    // Enumération pour représenter les directions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Variables pour l'animation graphique
    private int animationFrame = 0; // Indice de l'image d'animation
    private Direction lastDirection = Direction.DOWN; // Dernière direction suivie

    /**
     * Met à jour l'animation et la direction de l'entité.
     *
     * @param direction La direction actuelle.
     */
    protected void updateAnimation(Direction direction) {
        this.lastDirection = direction;
        this.animationFrame = (animationFrame + 1) % 3; // Cycle entre 3 frames
    }

    /**
     * Convertit un tableau de déplacement en direction.
     *
     * @param movement Tableau contenant les informations de déplacement [deltaRow, deltaCol].
     * @return La direction correspondante.
     */
    protected static Direction parseDirection(int[] movement) {
        if (movement[0] == -1) return Direction.UP;
        if (movement[0] == 1) return Direction.DOWN;
        if (movement[1] == -1) return Direction.LEFT;
        if (movement[1] == 1) return Direction.RIGHT;
        return Direction.DOWN; // Direction par défaut
    }

    /**
     * Constructeur de la classe {@code EtreVivant}.
     *
     * <p>Initialise un être vivant avec les caractéristiques spécifiées.</p>
     *
     * @param row          La position initiale en ligne.
     * @param col          La position initiale en colonne.
     * @param vitesse      La vitesse de déplacement (en cycles).
     * @param nourriture   La quantité initiale de nourriture.
     * @param visionRange  Le rayon de vision de l'entité.
     */
    public EtreVivant(int row, int col, int vitesse, int nourriture, int visionRange) {
        this.row = row;
        this.col = col;
        this.vitesse = vitesse;
        this.nourriture = nourriture;
        this.visionRange = visionRange;
        this.compteurDeplacement = new CompteurDeplacement(getVitesse());
        setRandomDirection();

        // Nombre de pas initiaux dans une direction aléatoire
        Random random = new Random();
        this.stepsCurrentDir = random.nextInt(6) + 4; // Entre 4 et 8 pas
    }

    // ** GETTERS **

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

    public int getAnimationFrame() {
        return animationFrame;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    // ** SETTERS **

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
        this.compteurDeplacement.setVitesse(vitesse);
    }

    public void setNourriture(int nourriture) {
        this.nourriture = nourriture;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public void setAnimationFrame(int animationFrame) {
        this.animationFrame = animationFrame;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    /**
     * Définit une direction aléatoire pour l'entité.
     */
    protected void setRandomDirection() {
        Random random = new Random();
        int[] randomDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        this.directionRow = randomDirection[0];
        this.directionCol = randomDirection[1];
    }

    /**
     * Met à jour le déplacement de l'être vivant en fonction du cycle actuel.
     *
     * <p>Gère l'incrémentation du compteur de déplacement et détermine
     * si un déplacement est nécessaire. Si oui, appelle la méthode abstraite
     * {@link #gen_deplacement(MapVivant, MapEnvironnement, int, int)}
     * pour exécuter un mouvement spécifique, puis réinitialise le compteur.</p>
     *
     * @param mapVivant   La carte contenant tous les êtres vivants.
     * @param grid        L'environnement représentant la grille de simulation.
     * @param row         La ligne cible pour le déplacement.
     * @param col         La colonne cible pour le déplacement.
     * @param dayNightCycle Le cycle jour/nuit actuel.
     */
    public void updateDeplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col, DayNightCycleImpl dayNightCycle) {
        // Ajuste la vitesse selon le cycle jour/nuit
        adjustVitesse(dayNightCycle.getCurrentCycle());

        // Incrémente le compteur de déplacement
        this.compteurDeplacement.incrementer();

        // Vérifie si un déplacement doit être effectué
        if (this.compteurDeplacement.doitSeDeplacer()) {
            // Appelle la méthode de déplacement spécifique et réinitialise le compteur
            gen_deplacement(mapVivant, grid, row, col);
            this.compteurDeplacement.reset();
        }
    }

    /**
     * Ajuste la vitesse de l'être vivant en fonction du cycle jour/nuit.
     *
     * @param cycle Le cycle actuel (jour, crépuscule, aurore, nuit).
     */
    public void adjustVitesse(DayNightCycleImpl.Cycle cycle) {
        switch (cycle) {
            case JOUR -> compteurDeplacement.setVitesse((int) (getVitesse() * getFacteurVitesseJour())); // Vitesse spécifique au jour
            case CREPUSCULE, AURORE -> compteurDeplacement.setVitesse(getVitesse()); // Pas de changement pour ces cycles
            case NUIT -> compteurDeplacement.setVitesse((int) (getVitesse() * getFacteurVitesseNuit())); // Vitesse spécifique à la nuit
            default -> throw new IllegalStateException("Unexpected value: " + cycle);
        }
    }

    /**
     * Définit le facteur de vitesse par défaut la nuit.
     * Les sous-classes peuvent redéfinir cette méthode pour des comportements spécifiques.
     *
     * @return Le facteur de vitesse (par défaut : 2x).
     */
    protected double getFacteurVitesseNuit() {
        return 2;
    }

    /**
     * Définit le facteur de vitesse par défaut le jour.
     * Les sous-classes peuvent redéfinir cette méthode pour des comportements spécifiques.
     *
     * @return Le facteur de vitesse (par défaut : 1x).
     */
    protected double getFacteurVitesseJour() {
        return 1;
    }

    /**
     * Récupère une liste des êtres vivants situés dans un rayon défini autour de cet être vivant.
     * Prend en compte les obstacles et la distance maximale de perception.
     *
     * @param mapVivant   La carte contenant tous les êtres vivants.
     * @param grid        La grille de simulation représentant l'environnement.
     * @param visionRange Le rayon dans lequel rechercher les êtres vivants.
     * @param maxDistance La distance maximale de perception.
     * @return Une liste des êtres vivants visibles ou détectables dans le rayon.
     */
    public List<EtreVivant> getEtreVivantsDansRayon(MapVivant mapVivant, MapEnvironnement grid, int visionRange, double maxDistance) {
        List<EtreVivant> etresVivants = new ArrayList<>();

        // Détermine les limites du rayon de recherche
        int startRow = Math.max(0, getRow() - visionRange);
        int startCol = Math.max(0, getCol() - visionRange);
        int endRow = Math.min(mapVivant.getRows() - 1, getRow() + visionRange);
        int endCol = Math.min(mapVivant.getCols() - 1, getCol() + visionRange);

        // Parcourt les cases dans le rayon pour identifier les êtres vivants
        for (int r = startRow; r <= endRow; r++) {
            for (int c = startCol; c <= endCol; c++) {
                double distance = Math.pow(getRow() - r, 2) + Math.pow(getCol() - c, 2);

                // Vérifie si la case est dans le rayon de vision
                if (distance <= Math.pow(visionRange, 2)) {
                    EtreVivant vivant = mapVivant.getEtreVivant(r, c);

                    // Ajoute les êtres vivants visibles et non bloqués par des obstacles
                    if (vivant != null && vivant != this && MapEnvironnement.isPathClear(grid, getRow(), getCol(), r, c, maxDistance)) {
                        etresVivants.add(vivant);
                    }
                }
            }
        }
        return etresVivants;
    }

    /**
     * Vérifie si cet être vivant est une menace pour un autre être vivant.
     *
     * @param autre L'autre être vivant à considérer.
     * @return {@code true} si cet être vivant est une menace, sinon {@code false}.
     */
    public boolean isMenace(EtreVivant autre) {
        // Par défaut, un être vivant n'est pas une menace
        return false;
    }

    /**
     * Méthode abstraite pour obtenir l'apparence graphique de l'être vivant.
     *
     * @param tileSize La taille des cases sur la carte.
     * @return Une ImageView représentant le sprite de l'entité.
     */
    public abstract ImageView getSprite(int tileSize);

    // Implémentation de l'interface Déplacement
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Comportement par défaut : déplacement erratique
        mouvementErratique(mapVivants, grid, row, col);
    }

    /**
     * Déplace l'entité vers une position donnée si possible.
     *
     * @param newRow       La nouvelle ligne cible.
     * @param newCol       La nouvelle colonne cible.
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La grille représentant l'environnement.
     * @return {@code true} si le déplacement est réussi, sinon {@code false}.
     */
    @Override
    public boolean deplacerVers(int newRow, int newCol, MapVivant mapVivants, MapEnvironnement grid) {
        // Vérifie si la position est valide
        if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getCols() &&
                !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
            // Met à jour la position
            mapVivants.setEtreVivant(row, col, null);
            row = newRow;
            col = newCol;
            mapVivants.setEtreVivant(row, col, this);
            return true;
        }
        return false; // Déplacement impossible
    }

    /**
     * Génère un déplacement aléatoire.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La grille représentant l'environnement.
     * @param row          La ligne actuelle de l'entité.
     * @param col          La colonne actuelle de l'entité.
     * @return Un tableau contenant la direction du déplacement, ou {@code null} si aucun déplacement n'a eu lieu.
     */
    @Override
    public int[] mouvementErratique(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Mélange les directions pour ajouter de l'aléatoire
        List<int[]> shuffledDirections = Arrays.asList(EtreVivant.DIRECTIONS);
        Collections.shuffle(shuffledDirections);

        for (int[] direction : shuffledDirections) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (deplacerVers(newRow, newCol, mapVivants, grid)) {
                return direction; // Déplacement réussi
            }
        }
        return null; // Aucun déplacement possible
    }

    /**
     * Se déplace vers une position en maximisant un score calculé pour chaque direction.
     *
     * @param mapVivants     La carte des entités vivantes.
     * @param grid           La grille représentant l'environnement.
     * @param calculerScore  Fonction calculant un score pour chaque position.
     * @return Un tableau contenant la direction du meilleur déplacement, ou {@code null} si aucun déplacement n'a eu lieu.
     */
    @Override
    public int[] seDeplacerSelonScore(MapVivant mapVivants, MapEnvironnement grid, BiFunction<Integer, Integer, Double> calculerScore) {
        int[] bestDirection = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int[] direction : DIRECTIONS) {
            int newRow = getRow() + direction[0];
            int newCol = getCol() + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
                double score = calculerScore.apply(newRow, newCol);
                if (score > bestScore) {
                    bestScore = score;
                    bestDirection = direction;
                }
            }
        }

        if (bestDirection != null && deplacerVers(row + bestDirection[0], col + bestDirection[1], mapVivants, grid)) {
            return bestDirection;
        }
        return null; // Aucun déplacement possible
    }

    /**
     * Effectue un mouvement circulaire autour d'une position centrale.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La grille représentant l'environnement.
     * @param centralRow   La ligne centrale.
     * @param centralCol   La colonne centrale.
     * @return Un tableau contenant la direction du déplacement, ou {@code null} si aucun déplacement n'a eu lieu.
     */
    @Override
    public int[] mouvementCirculaire(MapVivant mapVivants, MapEnvironnement grid, int centralRow, int centralCol) {
        Random random = new Random();

        int dRow = getRow() - centralRow;
        int dCol = getCol() - centralCol;
        int distance = dRow * dRow + dCol * dCol;

        int maxR = 4 * 4; // Distance maximale
        int minR = 2 * 2; // Distance minimale

        int verticalStep = 0;
        int horizontalStep = 0;

        if (distance > maxR) {
            if (Math.abs(dCol) > Math.abs(dRow)) {
                horizontalStep = Integer.signum(centralCol - getCol());
            } else {
                verticalStep = Integer.signum(centralRow - getRow());
            }
        } else if (distance < minR) {
            if (Math.abs(dCol) > Math.abs(dRow)) {
                horizontalStep = Integer.signum(getCol() - centralCol);
            } else {
                verticalStep = Integer.signum(getRow() - centralRow);
            }
        } else {
            if (random.nextBoolean()) {
                verticalStep = random.nextBoolean() ? 1 : -1;
            } else {
                horizontalStep = random.nextBoolean() ? 1 : -1;
            }
        }

        int newRow = getRow() + verticalStep;
        int newCol = getCol() + horizontalStep;

        if (deplacerVers(newRow, newCol, mapVivants, grid)) {
            return new int[]{verticalStep, horizontalStep};
        }
        return null; // Aucun déplacement possible
    }

    /**
     * Effectue une recherche active dans une direction spécifique.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La grille représentant l'environnement.
     * @return Un tableau contenant la direction du déplacement, ou {@code null} si aucun déplacement n'a eu lieu.
     */
    @Override
    public int[] rechercheActive(MapVivant mapVivants, MapEnvironnement grid) {
        Random random = new Random();

        if (this.stepsCurrentDir <= 0) {
            setRandomDirection();
            this.stepsCurrentDir = random.nextInt(6) + 4; // Entre 4 et 8 pas
        }

        int newRow = getRow() + directionRow;
        int newCol = getCol() + directionCol;

        if (deplacerVers(newRow, newCol, mapVivants, grid)) {
            stepsCurrentDir--;
            return new int[]{directionRow, directionCol};
        }

        setRandomDirection();
        this.stepsCurrentDir = random.nextInt(6) + 4;
        return null; // Aucun déplacement possible
    }
}