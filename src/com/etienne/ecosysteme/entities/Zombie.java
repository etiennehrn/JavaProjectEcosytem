package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Zombie extends EtreVivant {
    // Nombre total de styles de zombies
    private static final int NUM_ZOMBIE_STYLES = 1;

    // sprites humain pour chaque direction
    private static final Image[][] ZOMBIE_SPRITES_UP = new Image[3][NUM_ZOMBIE_STYLES];
    private static final Image[][] ZOMBIE_SPRITES_DOWN = new Image[3][NUM_ZOMBIE_STYLES];
    private static final Image[][] ZOMBIE_SPRITES_LEFT = new Image[3][NUM_ZOMBIE_STYLES];
    private static final Image[][] ZOMBIE_SPRITES_RIGHT = new Image[3][NUM_ZOMBIE_STYLES];

    // Pour l'animation
    private final int styleIndex;
    private int animationFrame = 0;
    private String lastDirection = "down";

    // On charge les sprites qu'une seule fois avec static donc bien perfo
    static {
        try {
            for (int style = 0; style < NUM_ZOMBIE_STYLES; style++) {
                for (int i = 0; i < 3; i++) {
                    ZOMBIE_SPRITES_UP[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/zombies/zombie" + style +"/zombie" + style +"_up_" + i + ".png")));
                    ZOMBIE_SPRITES_DOWN[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/zombies/zombie" + style +"/zombie" + style +"_down_" + i + ".png")));
                    ZOMBIE_SPRITES_LEFT[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/zombies/zombie" + style +"/zombie" + style +"_left_" + i + ".png")));
                    ZOMBIE_SPRITES_RIGHT[i][style] = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/zombies/zombie" + style +"/zombie" + style +"_right_" + i + ".png")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement des sprites des humains", e);
        }
    }

    // Constructeur
    public Zombie(int row, int col) {
        super(row, col, 1, 1, 30); // Vitesse 6
        // Choisir un style aléatoire pour ce zombie
        Random random = new Random();
        this.styleIndex = random.nextInt(NUM_ZOMBIE_STYLES);
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
        return switch (lastDirection) {
            case "up" -> ZOMBIE_SPRITES_UP[animationFrame][styleIndex];
            case "down" -> ZOMBIE_SPRITES_DOWN[animationFrame][styleIndex];
            case "left" -> ZOMBIE_SPRITES_LEFT[animationFrame][styleIndex];
            case "right" -> ZOMBIE_SPRITES_RIGHT[animationFrame][styleIndex];
            default -> ZOMBIE_SPRITES_DOWN[0][styleIndex];
        };
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Pour l'instant les zombies traque l'humain le plus proche s'il en voie un, sinon mouvement erratique
        // Zombie est cheaté car il peut se déplacer en diag (monde favorable aux zombies, c'est les humaisn qui dovent fuir)

        // On commence par lister les humains proches
        List<EtreVivant> etreVivants = getEtreVivantsDansRayon(mapVivants, grid, getVisionRange(), -1);
        List<EtreVivant> humainsProches = new ArrayList<>();

        for (EtreVivant vivant : etreVivants) {
            if (vivant instanceof Humain) {
                humainsProches.add(vivant);
            }
        }

        // On choisit le plus proche et on se déplace vers lui
        if (!humainsProches.isEmpty()) {
            EtreVivant humainCible = trouverHumainLePlusProche(humainsProches);

            if (humainCible != null) {
                int[] direction = {Integer.signum(humainCible.getRow() - getRow()), Integer.signum(humainCible.getCol() - getCol())};
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (deplacerVers(newRow, newCol, mapVivants, grid)) {
                    lastDirection = getDirectionName(direction);
                    animationFrame = (animationFrame + 1) % 3; // Passer au sprite suivant
                    return; // Déplacement réussi
                }
            }

        }
        // Sinon mouvement erratique
        int[] direction = mouvementErratique(mapVivants, grid, row, col);
        lastDirection = getDirectionName(direction);
        animationFrame = (animationFrame + 1) % 3; // Passer au sprite suivant

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

    // Méthode pour trouver l'humain le plus proche dans la liste
    private EtreVivant trouverHumainLePlusProche(List<EtreVivant> humainsProches) {
        EtreVivant humainPlusProche = null;
        int distanceMin = Integer.MAX_VALUE;

        for (EtreVivant humain : humainsProches) {
            int distance = Math.abs(humain.getRow() - getRow()) + Math.abs(humain.getCol() - getCol());
            if (distance < distanceMin) {
                distanceMin = distance;
                humainPlusProche = humain;
            }
        }
        return humainPlusProche;
    }

}
