package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

public class Humain extends EtreVivant {
    // Vitesse minimale et maximale des humains
    private static final int MIN_VITESSE = 3;
    private static final int MAX_VITESSE = 8;

    // Nombre total de styles d'humains, attention, il faut que ça corresponde avec SpriteManager
    private static final int NUM_HUMAN_STYLES = 4;

    // Pour le sprite
    private final int styleIndex;


    // Constructeur
    public Humain(int row, int col) {
        super(row, col, getRandomVitesse(), 1, 12); // Vitesse 5, visionRange 5
        // Choisir un style aléatoire pour cet humain
        Random random = new Random();
        this.styleIndex = random.nextInt(NUM_HUMAN_STYLES);
    }

    // Méthode pour générer une vitesse aléatoire entre MIN_VITESSE et MAX_VITESSE
    private static int getRandomVitesse() {
        Random random = new Random();
        return random.nextInt((MAX_VITESSE - MIN_VITESSE) + 1) + MIN_VITESSE;
    }

    // Récupérer le sprite actuel
    @Override
    public ImageView getSprite(int tileSize) {
        Image sprite = getCurrentSprite();
        ImageView imageView = new ImageView(sprite);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    // Obtenir le sprite actuel basé sur la direction et l'animation
    private Image getCurrentSprite() {
        Image[] directionSprites = SpriteManager.getInstance().getSprites(String.format("human%d", styleIndex), getLastDirection());
        return directionSprites[getAnimationFrame()];
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Actuellement les humains ont peur des zombies et bougent dans la direction opposée
        Random random = new Random();

        // Récupérer tous les êtres vivants dans le rayon de vision
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);

        // On filtre pour ne garder que les menaces
        List<EtreVivant> menaces = vivantsProches.stream().filter(vivant -> vivant instanceof Zombie).toList();
        // Mouvement erratique si pas de menace, très faible proba de bouger
        if (menaces.isEmpty()) {
            if (random.nextDouble() >= 0.1) {
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
            return;
        }

        // On calcule le déplacement en fonction des menaces
        int[] direction = seDeplacerSelonScore(mapVivants, grid, calculerScoreHumain(menaces));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }
    }

    // On définit une biFunction pour calculer le score pour les déplacements, c'est ici qu'on pourra mettre des comportements spécifiques.
    private BiFunction<Integer, Integer, Double> calculerScoreHumain(List<EtreVivant> menaces) {
        return (newRow, newCol) -> {
            double score = 0;

            // On maximise la distance par rapport aux menaces
            for (EtreVivant menace : menaces) {
                double distance = Math.pow(newCol - menace.getCol(), 2) + Math.pow(newRow-menace.getRow(), 2);
                score += distance;
            }
            return score;
        };
    }

    // Lent la nuit
    @Override
    public double getFacteurVitesseNuit() {
        return 1.5;
    }

    // Normal le jour
    @Override
    public double getFacteurVitesseJour() {
        return 1;
    }
}
