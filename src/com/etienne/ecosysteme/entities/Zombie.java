package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Zombie extends EtreVivant {
    // Nombre total de styles de zombies, attention, il faut que ça corresponde avec SpriteManager
    private static final int NUM_ZOMBIE_STYLES = 1;

    // Pour le sprite
    private final int styleIndex;

    // Constructeur
    public Zombie(int row, int col) {
        super(row, col, 1, 1, 30); // Vitesse 6
        // Choisir un style aléatoire pour ce zombie
        Random random = new Random();
        this.styleIndex = random.nextInt(NUM_ZOMBIE_STYLES);
    }

    // Récupérer le sprite actuel, dépends de taille d'un carré élémentaire
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
        Image[] directionSprites = SpriteManager.getInstance().getSprites(String.format("zombie%d", styleIndex), getLastDirection());
        return directionSprites[getAnimationFrame()];
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Pour l'instant les zombies traque l'humain le plus proche s'il en voie un, sinon mouvement erratique

        // Récupérer les zombies dans le rayon de vision
        List<EtreVivant> zombiesAProximite = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1)
                .stream()
                .filter(e -> e instanceof Zombie)
                .toList();
        if (zombiesAProximite.isEmpty()) {
            // Erratique
            Random random = new Random();
            if (random.nextDouble() >= 0.5) {
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);

            // Mise à jour de l'animation si le déplacement a eu lieu
            if (direction != null) {
                updateAnimation(parseDirection(direction));
            }
        }

        // Déplacer le loup en fonction du score
        int[] direction = seDeplacerSelonScore(mapVivants, grid, (newRow, newCol) -> calculerScoreDeplacement(newRow, newCol, zombiesAProximite));
        if (direction != null) {
            updateAnimation(parseDirection(direction));
        }

    }

    // Calcule le score basé sur la proximité des lapins
    private double calculerScoreDeplacement(int newRow, int newCol, List<EtreVivant> zombies) {
        double score = 0;
        for (EtreVivant zombie : zombies) {
            double distance = Math.pow(newRow - zombie.getRow(), 2) + Math.pow(newCol - zombie.getCol(), 2);
            score += 100 / (distance + 1); // Plus la distance est faible, plus le score est élevé
        }
        return score;
    }

    // Fonction pour que les zombies mangent l'humain pas loin (1 carré de lui)
    public void transformNearbyHumans(MapVivant mapVivants) {
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, 1},
                {0, -1}
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol)) {
                EtreVivant target = mapVivants.getEtreVivant(newRow, newCol);
                if (target instanceof Humain) {
                    // On transforme l'humain en zombie
                    mapVivants.setEtreVivant(newRow, newCol, new Zombie(newRow, newCol));
                }
            }
        }
    }

}
