import javafx.scene.image.ImageView;

public abstract class Animaux extends EtreVivant {
    public enum Type {
        DEER, BEAR, BOAR, FOX, WOLF, BUNNY
    }

    private int vitesse;
    private int nourriture;

    public Animaux(int row, int col, int vitesse, int nourriture) {
        super(row, col);
        this.vitesse = vitesse;
        this.nourriture = nourriture;
    }

    // Getter & Setter
    public int getVitesse() {
        return vitesse;
    }
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
    public int getNourriture() {
        return nourriture;
    }
    public void setNourriture(int nourriture) {
        this.nourriture = nourriture;
    }

    public abstract void gen_deplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col);

    @Override
    public abstract ImageView getSprite(int tileSize);

}
