package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Humain extends EtreVivant {
    // Vitesse minimale et maximale des humains
    private static final int MIN_VITESSE = 1;
    private static final int MAX_VITESSE = 5;

    // Nombre total de styles d'humains
    private static final int NUM_HUMAN_STYLES = 4;

    // sprites humain pour chaque direction
    private static final Image[][] HUMAN_SPRITES_UP = new Image[3][NUM_HUMAN_STYLES];
    private static final Image[][] HUMAN_SPRITES_DOWN = new Image[3][NUM_HUMAN_STYLES];
    private static final Image[][] HUMAN_SPRITES_LEFT = new Image[3][NUM_HUMAN_STYLES];
    private static final Image[][] HUMAN_SPRITES_RIGHT = new Image[3][NUM_HUMAN_STYLES];

    // Pour l'animation
    private final int styleIndex;
    private int animationFrame = 0;
    private String lastDirection = "down";
    // On charge les sprites qu'une seule fois avec static donc bien perfo
    static {
        try {
            for (int style = 0; style < NUM_HUMAN_STYLES; style++) {
                for (int i = 0; i < 3; i++) {
                    HUMAN_SPRITES_UP[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/humans/human" + style +"/human" + style + "_up_" + i + ".png")));
                    HUMAN_SPRITES_DOWN[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/humans/human" + style +"/human" + style + "_down_" + i + ".png")));
                    HUMAN_SPRITES_LEFT[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/humans/human" + style +"/human" + style + "_left_" + i + ".png")));
                    HUMAN_SPRITES_RIGHT[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/humans/human" + style +"/human" + style + "_right_" + i + ".png")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement des sprites des humains", e);
        }
    }

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

    // Obtenir le scripte actuelle
    public Image getCurrentSprite() {
        switch (lastDirection) {
            case "up":
                return HUMAN_SPRITES_UP[animationFrame][styleIndex];
            case "down":
                return HUMAN_SPRITES_DOWN[animationFrame][styleIndex];
            case "left":
                return HUMAN_SPRITES_LEFT[animationFrame][styleIndex];
            case "right":
                return HUMAN_SPRITES_RIGHT[animationFrame][styleIndex];
            default:
                return HUMAN_SPRITES_DOWN[0][styleIndex];
        }
    }


    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Actuellement les humains ont peur des zombies et bougent dans la direction opposé

        // On récupère les êtres vivants autours puis on filtre pour garder les zombies
        List<EtreVivant> etreVivants = getEtreVivantsDansRayon(mapVivants, getVisionRange());
        List<EtreVivant> zombiesProches = new ArrayList<>();

        for (EtreVivant vivant : etreVivants) {
            if (vivant instanceof Zombie) {
                zombiesProches.add(vivant);
            }
        }

        int maxDistance = 0;
        int[] bestDirection = null;

        for (int[] direction : EtreVivant.DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
                // On calcule la distance totale par rapport à tous les zombies
                int distanceTotale = 0;
                for (EtreVivant zombie : zombiesProches) {
                    distanceTotale += Math.abs(zombie.getRow() - newRow) + Math.abs(zombie.getCol() - newCol);
                }

                if (distanceTotale > maxDistance) {
                    maxDistance = distanceTotale;
                    bestDirection = direction;
                }
            }
        }

        // On se déplace selon la meilleur direction
        if (bestDirection != null) {
            if (deplacerVers(row + bestDirection[0], col + bestDirection[1], mapVivants, grid))
            {
                lastDirection = getDirectionName(bestDirection);
                animationFrame = (animationFrame + 1) % 3; // Passer au sprite suivant
            }

        }
    }
}
