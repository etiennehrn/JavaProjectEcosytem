package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * Classe représentant un humain (Humain) dans l'écosystème.
 * Cette classe hérite de EtreVivant et implémente des comportements spécifiques,
 * comme la fuite des zombies ou un mouvement erratique en leur absence.
 */
public class Humain extends EtreVivant {

    // Vitesse minimale et maximale des humains.
    private static final int MIN_VITESSE = 2;
    private static final int MAX_VITESSE = 8;

    // Nombre total de styles disponibles pour les humains (doit correspondre avec SpriteManager).
    private static final int NUM_HUMAN_STYLES = 4;

    // Index du style de l'humain (déterminé aléatoirement).
    private final int styleIndex;

    /**
     * Constructeur de la classe Humain.
     * Initialise la position, la vitesse et le style visuel de l'humain.
     *
     * @param row La ligne initiale où l'humain est placé sur la carte.
     * @param col La colonne initiale où l'humain est placé sur la carte.
     */
    public Humain(int row, int col) {
        super(row, col, getRandomVitesse(), 1, 12); // Vitesse, points de vie, vision range
        Random random = new Random();
        this.styleIndex = random.nextInt(NUM_HUMAN_STYLES); // Sélectionne un style aléatoire.
    }

    /**
     * Génère une vitesse aléatoire pour un humain.
     *
     * @return Une vitesse entre MIN_VITESSE et MAX_VITESSE.
     */
    private static int getRandomVitesse() {
        Random random = new Random();
        return random.nextInt((MAX_VITESSE - MIN_VITESSE) + 1) + MIN_VITESSE;
    }

    /**
     * Récupère le sprite visuel actuel de l'humain.
     *
     * @param tileSize La taille des cases sur la carte.
     * @return Une ImageView représentant l'humain.
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
     * @return L'image actuelle représentant l'humain.
     */
    private Image getCurrentSprite() {
        Image[] directionSprites = SpriteManager.getInstance()
                .getSprites(String.format("human%d", styleIndex), getLastDirection());
        return directionSprites[getAnimationFrame()];
    }

    /**
     * Génère le déplacement de l'humain.
     * L'humain fuit les zombies à proximité ou effectue un mouvement erratique
     * s'il n'y a pas de menace.
     *
     * @param mapVivants La carte des entités vivantes.
     * @param grid       La carte de l'environnement.
     * @param row        La ligne actuelle de l'humain.
     * @param col        La colonne actuelle de l'humain.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        Random random = new Random();

        // Récupère les entités vivantes dans le rayon de vision.
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // Filtre les menaces (zombies uniquement).
        List<EtreVivant> menaces = vivantsProches.stream()
                .filter(vivant -> vivant instanceof Zombie)
                .toList();

        if (menaces.isEmpty()) {
            // Mouvement erratique avec une faible probabilité de bouger.
            if (random.nextDouble() >= 0.1) {
                return; // Pas de déplacement.
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // Se déplace pour maximiser la distance avec les menaces.
        int[] direction = seDeplacerSelonScore(mapVivants, grid, calculerScoreHumain(menaces));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    /**
     * Calcule un score pour chaque position potentielle, maximisant
     * la distance par rapport aux menaces.
     *
     * @param menaces Liste des entités menaçantes (zombies).
     * @return Une BiFunction pour calculer le score d'une position donnée.
     */
    private BiFunction<Integer, Integer, Double> calculerScoreHumain(List<EtreVivant> menaces) {
        return (newRow, newCol) -> {
            double score = 0;

            // Maximiser la distance par rapport à chaque menace.
            for (EtreVivant menace : menaces) {
                double distance = Math.pow(newCol - menace.getCol(), 2) + Math.pow(newRow - menace.getRow(), 2);
                score += distance;
            }
            return score;
        };
    }

    /**
     * Facteur de vitesse de l'humain la nuit (ralenti).
     *
     * @return Le facteur de vitesse la nuit (1.5x plus lent).
     */
    @Override
    public double getFacteurVitesseNuit() {
        return 1.5;
    }

    /**
     * Facteur de vitesse de l'humain le jour (normal).
     *
     * @return Le facteur de vitesse le jour (1x).
     */
    @Override
    public double getFacteurVitesseJour() {
        return 1;
    }
}
