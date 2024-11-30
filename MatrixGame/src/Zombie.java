import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Zombie extends EtreVivant {
    // Attribut d'un zombie
    private int searchRadius;


    private static final Image ZOMBIE_IMAGE = new Image(Objects.requireNonNull(Zombie.class.getResourceAsStream("/ressources/sprites/zombie.png")));

    public Zombie(int row, int col) {
        super(row, col);
        searchRadius = 50;
    }

    @Override
    public ImageView getSprite(int titleSize) {
        ImageView imageView = new ImageView(ZOMBIE_IMAGE);
        imageView.setFitWidth(titleSize);
        imageView.setFitHeight(titleSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // On pourra implémenter les fuites ou déplacements spéciaux ici
        int[][] directions = {
                {-1, 0}, // Haut
                {1, 0},  // Bas
                {0, 1},  // Gauche
                {0, -1}  // Droite
        };

        // Cheche humain proche
        int[] targetPosition = findNearestHuman(mapVivants, grid, row, col, searchRadius);
        if (targetPosition != null) {
            // On va vers l'humain
            int newRow = row + Integer.signum(targetPosition[0]-row);
            int newCol = col + Integer.signum(targetPosition[1]-col);

            if (deplacerVers(newRow, newCol, mapVivants, grid)) {
                return;
            }
        }

        // Sinon mouvement erratique
        List<int[]> shuffledDirections = Arrays.asList(directions);
        Collections.shuffle(shuffledDirections);

        for (int[] direction : shuffledDirections) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (deplacerVers(newRow, newCol, mapVivants, grid)) {
                return;
            }
        }



    }


    // Fonction pour que les zombies mangent l'humain pas loin
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

    // Fonction qui cherche les humains pas trop loin, renvoie seulement la position
    private int[] findNearestHuman(MapVivant mapVivants, MapEnvironnement grid, int row, int col, int radius) {
        int closestDistance = Integer.MAX_VALUE;
        int[] closestHumans = null;

        for (int i = Math.max(0, row-radius); i <= Math.min(grid.getRows()-1, row+radius); i++) {
            for (int j = Math.max(0, row-radius); j <= Math.min(grid.getCols() -1, col + radius); j++) {
                EtreVivant target = mapVivants.getEtreVivant(i, j);
                if (target instanceof Humain) {
                    // La fameuse distance de manhattan qui sert dans autre chose que des exos
                    int distance = Math.abs(i - row) + Math.abs(j - col);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestHumans = new int[]{i, j};
                    }

                }
            }
        }
        return closestHumans;
    }


}
