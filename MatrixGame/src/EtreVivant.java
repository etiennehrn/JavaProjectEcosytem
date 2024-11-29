import javafx.scene.image.ImageView;

public abstract class EtreVivant {
    protected int row;
    protected int col;

    public EtreVivant(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getter
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    // Setter
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Méthode abstraite pour obtenir le sprite
    public abstract ImageView getSprite(int tileSize);

    // Méthode déplacement
    public abstract void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col);

    // Méthode générique qui fais le déplacement et renvoie true si il est valide (sinon ne fais rien et renvoie false)
    public boolean deplacerVers(int newRow, int newCol, MapVivant mapVivants, MapEnvironnement grid) {
        // On vérifie si la position est valide
        if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getCols() && !grid.getCell(newRow, newCol).isObstacle() && mapVivants.getEtreVivant(newRow, newCol) == null) {
            // Déplacer l'être vivant
            mapVivants.setEtreVivant(row, col, null);
            row = newRow;
            col = newCol;
            mapVivants.setEtreVivant(row, col, this);
            return true; // Déplacement réussi
        }
        return false; // Déplacement impossible
    }


}
