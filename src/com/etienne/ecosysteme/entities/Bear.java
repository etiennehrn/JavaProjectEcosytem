package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Bear extends Animaux {
    // Les ours ont un comportement solitaire,ils font peur aux autres et garde leur terrain
    private static final Image BEAR_IMAGE = new Image(Objects.requireNonNull(Bear.class.getResourceAsStream("/ressources/sprites/animals/bear.png")));

    // Position centrale Ours
    private final int centralRow;
    private final int centralCol;

    private final Random random = new Random();

    public Bear(int row, int col) {
        super(row, col, 30, 1, 5);
        this.centralRow = row;
        this.centralCol = col;
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(BEAR_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, getVisionRange());
        List<EtreVivant> autresOurs = new ArrayList<>();

        // Filtrer pour garder uniquement les autres ours
        for (EtreVivant vivant : vivantsProches) {
            if (vivant instanceof Bear && vivant != this) {
                autresOurs.add(vivant);
            }
        }

        // Si un autre ours est proche, l'ours s'éloigne
        if (!autresOurs.isEmpty()) {
            EtreVivant oursProche = trouverOursLePlusProche(autresOurs);

            if (oursProche != null) {
                int verticalStep = -Integer.signum(oursProche.getRow() - getRow());
                int horizontalStep = -Integer.signum(oursProche.getCol() - getCol());

                // Tenter de s'éloigner
                if (deplacerVers(row + verticalStep, col + horizontalStep, mapVivants, grid)) {
                    return; // Déplacement réussi sinon on verra plus tard
                }
            }
        }
        // Sinon, mouvement lent et circulaire
        mouvementLentCirculaire(mapVivants, grid, row, col);
    }
    // Fonction pour trouver l'ours le plus proche dans une liste
    private EtreVivant trouverOursLePlusProche(List<EtreVivant> autresOurs) {
        EtreVivant oursLePlusProche = null;
        int distanceMin = Integer.MAX_VALUE;

        for (EtreVivant ours : autresOurs) {
            int distance = Math.abs(ours.getRow() - getRow()) + Math.abs(ours.getCol() - getCol());
            if (distance < distanceMin) {
                distanceMin = distance;
                oursLePlusProche = ours;
            }
        }
        return oursLePlusProche;
    }

    // Mouvement lent et circulaire autour de la zone centrale
    private void mouvementLentCirculaire(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Générer un mouvement légèrement circulaire avec un peu d'aléatoire
        int verticalStep = random.nextBoolean() ? 1 : -1;
        int horizontalStep = random.nextBoolean() ? 1 : -1;

        // Favoriser les déplacements vers la zone centrale
        if (random.nextDouble() > 0.7) {
            verticalStep = Integer.signum(centralRow - getRow());
        }
        if (random.nextDouble() > 0.7) {
            horizontalStep = Integer.signum(centralCol - getCol());
        }

        // Essayer le déplacement
        if (!deplacerVers(row + verticalStep, col + horizontalStep, mapVivants, grid)) {
            // Si échoue, mouvement aléatoire
            int[] randomDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            deplacerVers(row + randomDirection[0], col + randomDirection[1], mapVivants, grid);
        }
    }

}
