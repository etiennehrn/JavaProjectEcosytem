import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Deer extends Animaux {
    private static final Image DEER_IMAGE = new Image(Objects.requireNonNull(Deer.class.getResourceAsStream("/ressources/sprites/deer.png")));

    public Deer(int row, int col) {
        super(row, col, 1, 1);
    }

    @Override
    public ImageView getSprite(int tileSize) {
        ImageView imageView = new ImageView(DEER_IMAGE);
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        return imageView;
    }

    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // pas de déplacement pour l'instant
    }
}
