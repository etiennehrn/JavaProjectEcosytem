package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;

import java.util.*;
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

public abstract class EtreVivant implements Deplacement {
    // Position
    protected int row;
    protected int col;

    // Pour déplacement de recherche active
    private int directionRow; // Ligne de la direction actuelle
    private int directionCol; // Colonne de la direction actuelle
    private int stepsCurrentDir;

    private int vitesse; // Vitesse en nombre de cycles
    private final CompteurDeplacement compteurDeplacement; // Pour compter les déplacements

    private int nourriture;
    private int visionRange;

    // Variable pour les directions
    public static final int[][] DIRECTIONS = {
            {-1, 0}, // Haut
            {1, 0},  // Bas
            {0, 1},  // Droite
            {0, -1}  // Gauche
    };

    // Nom des directions possibles
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Pour l'animation
    private int animationFrame = 0;
    private Direction lastDirection = Direction.DOWN;
    // Mise à jour de l'animation et de la direction
    protected void updateAnimation(Direction direction) {
        this.lastDirection = direction;
        this.animationFrame = (animationFrame + 1) % 3; // Boucle sur 3 images
    }

    // Méthode pour convertir une direction en chaîne de caractère
    protected static Direction parseDirection(int[] movement) {
        if (movement[0] == -1) return Direction.UP;
        if (movement[0] == 1) return Direction.DOWN;
        if (movement[1] == -1) return Direction.LEFT;
        if (movement[1] == 1) return Direction.RIGHT;
        return Direction.DOWN; // Par défaut
    }


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
        this.compteurDeplacement = new CompteurDeplacement(getVitesse());
        setRandomDirection();
        Random random = new Random();
        this.stepsCurrentDir = random.nextInt(6) + 4; // Entre 4 et 8 pas initiaux

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
    public int getAnimationFrame() {
        return animationFrame;
    }
    public Direction getLastDirection() {
        return lastDirection;
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
    public void setAnimationFrame(int animationFrame) {
        this.animationFrame = animationFrame;
    }
    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }
    protected void setRandomDirection() {
        Random random = new Random();
        int[] randomDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        this.directionRow = randomDirection[0];
        this.directionCol = randomDirection[1];
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
        this.compteurDeplacement.incrementer();
        // On dit si l'être vivant doit se déplacer
        if (this.compteurDeplacement.doitSeDeplacer()) {
            // On se déplace puis on réinitialise le compteur
            gen_deplacement(mapVivant, grid, row, col);
            this.compteurDeplacement.reset();
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
     * Vérifie si cet être vivant est une menace pour un autre être vivant.
     *
     * @param autre l'autre être vivant à considérer
     * @return {@code true} si cet être vivant est une menace, {@code false} sinon
     */
    public boolean isMenace(EtreVivant autre) {
        // Par défaut, un être vivant n'est pas une menace
        return false;
    }

    // Méthode abstraite pour obtenir le sprite
    public abstract ImageView getSprite(int tileSize);

    // Implémentation de l'interface Déplacement
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Comportement générique par défaut, peut être remplacé par les sous-classes
        mouvementErratique(mapVivants, grid, row, col);
    }

    @Override
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

    @Override
    public int[] mouvementErratique(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
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

    @Override
    public int[] seDeplacerSelonScore(MapVivant mapVivants, MapEnvironnement grid, BiFunction<Integer, Integer, Double> calculerScore) {
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

    @Override
    public int[] mouvementCirculaire(MapVivant mapVivants, MapEnvironnement grid, int centralRow, int centralCol) {
        Random random = new Random();

        int dRow = getRow() - centralRow;
        int dCol = getCol() - centralCol;
        int distance = dRow * dRow + dCol * dCol;

        int maxR = 4 * 4; // Distance max d'éloignement
        int minR = 2 * 2; // Distance minimale proche du centre

        int verticalStep = 0;
        int horizontalStep = 0;

        if (distance > maxR) {
            // Trop éloigné, forte chance de revenir vers le centre
            if (Math.abs(dCol) > Math.abs(dRow)) {
                horizontalStep = Integer.signum(centralCol - getCol());
            } else {
                verticalStep = Integer.signum(centralRow - getRow());
            }
        } else if (distance < minR) {
            // Trop proche, tendance à s'éloigner
            if (Math.abs(dCol) > Math.abs(dRow)) {
                horizontalStep = Integer.signum(getCol() - centralCol);
            } else {
                verticalStep = Integer.signum(getRow() - centralRow);
            }
        } else {
            // Mouvement aléatoire dans une direction verticale ou horizontale
            if (random.nextBoolean()) {
                verticalStep = random.nextBoolean() ? 1 : -1;
            } else {
                horizontalStep = random.nextBoolean() ? 1 : -1;
            }
        }

        // Calcul des nouvelles coordonnées
        int newRow = getRow() + verticalStep;
        int newCol = getCol() + horizontalStep;

        // Vérifie si le déplacement est valide
        if (deplacerVers(newRow, newCol, mapVivants, grid)) {
            return new int[]{verticalStep, horizontalStep};
        }

        // Pas de déplacement si impossible
        return null;
    }

    @Override
    public int[] rechercheActive(MapVivant mapVivants, MapEnvironnement grid) {
        Random random = new Random();

        // On vérifie si la direction actuelle doit être changée
        if (this.stepsCurrentDir <= 0) {
            setRandomDirection();
            this.stepsCurrentDir = random.nextInt(6) + 4; // Entre 4 et 8 pas
        }

        int newRow = getRow() + directionRow;
        int newCol = getCol() + directionCol;

        // On vérifie si le déplacement est possible
        if (deplacerVers(newRow, newCol, mapVivants, grid)) {
            stepsCurrentDir--;
            return new int[]{directionRow, directionCol};
        }

        // On change la direction, du moins on essaie
        setRandomDirection();
        this.stepsCurrentDir = random.nextInt(6) + 4;

        // Pas de déplacement possible
        return null;
    }
}