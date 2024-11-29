/* Classe qui représente une case de la map, ça peut-etre plein de choses herbes, mur, eau, etc... */
import javafx.scene.image.Image;
import java.util.Objects;

public class Case {
    public enum Type {
        HERBE, MUR
    }

    private static final Image HERBE_TEXTURE;
    private static final Image MUR_TEXTURE;

    static {
        try {
            HERBE_TEXTURE = new Image(Objects.requireNonNull(
                    Case.class.getResourceAsStream("/ressources/textures/herbe.jpg"),
                    "Texture herbe.jpg introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."
            ));

            MUR_TEXTURE = new Image(Objects.requireNonNull(
                    Case.class.getResourceAsStream("/ressources/textures/mur.jpg"),
                    "Texture mur.jpg introuvable ! Assurez-vous que le fichier est dans le dossier ressources/textures."
            ));
        } catch (NullPointerException e) {
            throw new RuntimeException("Erreur lors du chargement des textures : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors du chargement des textures.", e);
        }
    }

    private final Type type;
    private final Image texture;

    public Case(Type type) {
        this.type = type;
        this.texture = switch (type) {
            case HERBE -> HERBE_TEXTURE;
            case MUR -> MUR_TEXTURE;
        };
    }

    // Getter
    public Image getTexture() {
        return texture;
    }
    public Type getType() {
        return type;
    }
    public boolean isObstacle() {
        return type == Type.MUR;
    }
}
