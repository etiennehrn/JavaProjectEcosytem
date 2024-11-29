import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Humain extends EtreVivant {
    private static final Image HUMAN_IMAGE = new Image(Objects.requireNonNull(Humain.class.getResourceAsStream("/ressources/sprites/human.png")));

    public Humain(int row, int col) {
        super(row, col);
    }

    @Override
    public ImageView getSprite(int titleSize) {
        ImageView imageView = new ImageView(HUMAN_IMAGE);
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
                {0, -1},  // Droite
                {-1, -1},
                {1, -1},
                {-1, 1},
                {-1, -1},
        };

        // Liste pour sotcker les zombies proches les posistions
        List<int[]> zombiesProches = new ArrayList<>();

        // On cherche les zombies proches
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && mapVivants.getEtreVivant(newRow, newCol) instanceof Zombie) {
                zombiesProches.add(new int[]{newRow, newCol});
            }
        }

        int maxDistance = -1;
        int[] bestDirection = null;

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
                // On calcule la distance par rapport aux zombies
                int distance = 0;
                for (int[] zombie : zombiesProches) {
                    int zombieRow = zombie[0];
                    int zombieCol = zombie[1];
                    distance += Math.abs(zombieRow - newRow) + Math.abs(zombieCol - newCol);
                }

                if (distance > maxDistance) {
                    maxDistance = distance;
                    bestDirection = direction;
                }

            }
        }

        // O,n se déplace selon la meilleur direction
        if (bestDirection != null) {
            deplacerVers(row + bestDirection[0], col + bestDirection[1], mapVivants, grid);
        }
    }


}
