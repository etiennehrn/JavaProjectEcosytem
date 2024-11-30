import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Fox extends Animaux {
    private static final Image FOX_IMAGE = new Image(Objects.requireNonNull(Fox.class.getResourceAsStream("/ressources/sprites/fox.png")));

    public Fox(int row, int col) {
        super(row, col, 1, 1);
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(FOX_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // pas de déplacement pour l'instant
    }
}
