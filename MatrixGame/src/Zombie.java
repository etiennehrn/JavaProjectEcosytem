import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Zombie extends EtreVivant {
    private static final Image ZOMBIE_IMAGE = new Image(Objects.requireNonNull(Zombie.class.getResourceAsStream("/ressources/sprites/zombie.png")));

    public Zombie(int row, int col) {
        super(row, col);
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

        for (int attempt = 0; attempt < 4; attempt++) {
            int[] direction = directions[(int) (Math.random() * directions.length)];
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // On vérifie si le déplacement est correcte et dans ce cas il est fait
            if (deplacerVers(newRow, newCol, mapVivants, grid)) {
                return;
            }
        }
    }


}
