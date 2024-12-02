import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Wolf extends Animaux {
    private static final Image WOLF_IMAGE = new Image(Objects.requireNonNull(Wolf.class.getResourceAsStream("/ressources/sprites/animals/wolf.png")));

    public Wolf(int row, int col) {
        super(row, col, 10, 1, 5);
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(WOLF_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Chasse les lapins et se déplace bcp sur la map
    }
}
