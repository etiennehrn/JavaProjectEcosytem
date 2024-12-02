import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Bunny extends Animaux {
    private static final Image BUNNY_IMAGE = new Image(Objects.requireNonNull(Bunny.class.getResourceAsStream("/ressources/sprites/animals/bunny.png")));

    public Bunny(int row, int col) {
        super(row, col, 1, 1, 4); // Vitesse 2 pour les lapins, ils sont rapide les fous
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(BUNNY_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Lapin qui font les tapettes en gros, et ils sont très rapide pour fuir
        Random random = new Random();

        // Récupérer tous les êtres vivants dans le rayon de vision
        List<EtreVivant> vivantsProches = getEtreVivantsDansRayon(mapVivants, getVisionRange());
        boolean menacePresente = !vivantsProches.isEmpty();

        // Mouvement erratique si pas de menace, très faible proba de bouger
        if (!menacePresente) {
            if (random.nextDouble() >= 0.1) {
                return; // Pas de déplacement
            }
            int[] direction = mouvementErratique(mapVivants, grid, row, col);
            return;
        }

        int maxDistance = 0;
        int[] bestDirection = null;

        for (int[] direction : EtreVivant.DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mapVivants.isWithinBounds(newRow, newCol) && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
                // On calcule la distance totale par rapport à tous les zombies
                int distanceTotale = 0;
                for (EtreVivant vivant : vivantsProches) {
                    distanceTotale += Math.abs(vivant.getRow() - newRow) + Math.abs(vivant.getCol() - newCol);
                }

                if (distanceTotale > maxDistance) {
                    maxDistance = distanceTotale;
                    bestDirection = direction;
                }
            }
        }

        // On se déplace selon la meilleur direction
        if (bestDirection != null) {
            deplacerVers(row + bestDirection[0], col + bestDirection[1], mapVivants, grid);
        }
    }

}
