import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Bear extends Animaux {
    private static final Image BEAR_IMAGE = new Image(Objects.requireNonNull(Bear.class.getResourceAsStream("/ressources/sprites/bear.png")));

    public Bear(int row, int col) {
        super(row, col, 1, 1);
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
        // pas de déplacement pour l'instant
    }
}
