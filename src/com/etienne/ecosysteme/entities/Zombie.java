package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

/**
 * Classe représentant un zombie (Zombie) dans l'écosystème.
 * Les zombies poursuivent les humains proches ou se déplacent de manière erratique
 * en leur absence. Ils ont également un système de gestion de la nourriture.
 */
public class Zombie extends EtreVivant implements IGestionNourriture {

    // Nombre total de styles de zombies (doit correspondre à SpriteManager).
    private static final int NUM_ZOMBIE_STYLES = 1;

    // Quantité minimale et maximale de nourriture.
    public static final int NOURRITURE_MAX = 500;
    public static final int NOURRITURE_MIN = 0;

    // Index du style visuel du zombie (déterminé aléatoirement).
    private final int styleIndex;

    /**
     * Constructeur de la classe Zombie.
     * Initialise la position, les caractéristiques et le style visuel du zombie.
     *
     * @param row La ligne initiale où le zombie est placé sur la carte.
     * @param col La colonne initiale où le zombie est placé sur la carte.
     */
    public Zombie(int row, int col) {
        super(row, col, 4, 300, 30); // Vitesse, points de vie, vision range.
        Random random = new Random();
        this.styleIndex = random.nextInt(NUM_ZOMBIE_STYLES); // Style visuel aléatoire.
    }

    /**
     * Récupère le sprite visuel actuel du zombie.
     *
     * @param tileSize La taille des cases sur la carte.
     * @return Une ImageView représentant le zombie.
     */
    @Override
    public ImageView getSprite(int tileSize) {
        Image sprite = getCurrentSprite();
        ImageView imageView = new ImageView(sprite);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    /**
     * Récupère le sprite actuel en fonction de la direction et de l'animation.
     *
     * @return L'image actuelle représentant le zombie.
     */
    private Image getCurrentSprite() {
        Image[] directionSprites = SpriteManager.getInstance()
                .getSprites(String.format("zombie%d", styleIndex), getLastDirection());
        return directionSprites[getAnimationFrame()];
    }

    /**
     * Génère le déplacement du zombie.
     * Si un humain est visible, le zombie le poursuit. Sinon, il se déplace de manière erratique.
     *
     * @param mapVivants La carte des entités vivantes.
     * @param grid       La carte de l'environnement.
     * @param row        La ligne actuelle du zombie.
     * @param col        La colonne actuelle du zombie.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Recherche d'humains dans le rayon de vision.
        List<EtreVivant> humainsAProximite = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1)
                .stream()
                .filter(e -> e instanceof Humain)
                .toList();

        if (humainsAProximite.isEmpty()) {
            // Déplacement erratique si aucun humain n'est visible.
            int[] directionRecherche = rechercheActive(mapVivants, grid);
            if (directionRecherche != null) {
                updateAnimation(parseDirection(directionRecherche));
                consommerNourriture(1, mapVivants);
                return;
            }

            // Mouvement aléatoire avec faible probabilité.
            Random random = new Random();
            if (random.nextDouble() >= 0.5) {
                return; // Pas de déplacement.
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                consommerNourriture(1, mapVivants);
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // Poursuite des humains.
        int[] direction = seDeplacerSelonScore(mapVivants, grid,
                (newRow, newCol) -> calculerScoreDeplacement(newRow, newCol, humainsAProximite));
        if (direction != null) {
            consommerNourriture(1, mapVivants);
            updateAnimation(parseDirection(direction));
        }
    }

    /**
     * Calcule un score pour une position donnée en fonction de la proximité aux humains.
     * Plus un humain est proche, plus le score est élevé.
     *
     * @param newRow  La ligne de la position cible.
     * @param newCol  La colonne de la position cible.
     * @param humains Liste des humains à proximité.
     * @return Le score calculé pour la position.
     */
    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> humains) {
        double score = 0;
        for (EtreVivant humain : humains) {
            double distance = Math.pow(newRow - humain.getRow(), 2) + Math.pow(newCol - humain.getCol(), 2);
            score += 100 / (distance + 1); // Inversement proportionnel à la distance.
        }
        return score;
    }

    /**
     * Transforme les humains proches en zombies s'ils sont à une distance d'un carré.
     *
     * @param mapVivants La carte des entités vivantes.
     */
    public void transformNearbyHumans(MapVivant mapVivants) {
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, 1}, {0, -1} // Haut, bas, droite, gauche.
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol)) {
                EtreVivant target = mapVivants.getEtreVivant(newRow, newCol);
                if (target instanceof Humain) {
                    // Transformation de l'humain en zombie.
                    mapVivants.setEtreVivant(newRow, newCol, new Zombie(newRow, newCol));
                    manger(200); // Gain de nourriture.
                }
            }
        }
    }

    /**
     * Retourne le facteur de vitesse du zombie la nuit (plus rapide).
     *
     * @return Le facteur de vitesse (0.8x).
     */
    @Override
    public double getFacteurVitesseNuit() {
        return 0.8;
    }

    /**
     * Retourne le facteur de vitesse du zombie le jour (plus lent).
     *
     * @return Le facteur de vitesse (2x).
     */
    @Override
    public double getFacteurVitesseJour() {
        return 2;
    }

    /**
     * Consomme une quantité de nourriture et vérifie l'état du zombie.
     *
     * @param quantite    La quantité de nourriture consommée.
     * @param mapVivants  La carte des entités vivantes.
     */
    @Override
    public void consommerNourriture(int quantite, MapVivant mapVivants) {
        this.setNourriture(Math.max(NOURRITURE_MIN, this.getNourriture() - quantite));
        verifierEtatNourriture(mapVivants);
    }

    /**
     * Augmente la quantité de nourriture du zombie.
     *
     * @param gain La quantité de nourriture gagnée.
     */
    @Override
    public void manger(int gain) {
        this.setNourriture(Math.min(NOURRITURE_MAX, this.getNourriture() + gain));
    }

    /**
     * Vérifie l'état de la nourriture. Si elle est insuffisante, le zombie meurt ou ralentit.
     *
     * @param mapVivants La carte des entités vivantes.
     */
    @Override
    public void verifierEtatNourriture(MapVivant mapVivants) {
        if (this.getNourriture() <= 0) {
            mourir(mapVivants);
        } else if (this.getNourriture() <= 15) {
            setVitesse(Math.max(1, getVitesse() + 1)); // Ralentit si la nourriture est faible.
        }
    }

    /**
     * Gère la mort du zombie en le supprimant de la carte.
     *
     * @param mapVivants La carte des entités vivantes.
     */
    private void mourir(MapVivant mapVivants) {
        mapVivants.setEtreVivant(this.getRow(), this.getCol(), null);
    }
}
