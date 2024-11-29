public class Player {
    private int row;
    private int col;
    private final int visionRange;
    private final MapEnvironnement mapEnvironnement;


    public Player(int startRow, int startCol, int visionRange, MapEnvironnement mapEnvironnement) {
        this.row = startRow;
        this.col = startCol;
        this.visionRange = visionRange;
        this.mapEnvironnement = mapEnvironnement;

        // On vérifie que la position initiale est valide
        if (mapEnvironnement.getCell(startRow, startCol).isObstacle()) {
            throw new IllegalArgumentException("Position initiale invalide : il y a un obstacle");
        }
    }

    // Getter
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int getVisionRange() {
        return visionRange;
    }

    // Déplacement du joueur
    public boolean moveUp() {
        return moveTo(row - 1, col);
    }
    public boolean moveDown() {
        return moveTo(row + 1, col);
    }
    public boolean moveLeft() {
        return moveTo(row, col - 1);
    }
    public boolean moveRight() {
        return moveTo(row, col + 1);
    }

    private boolean moveTo(int newRow, int newCol) {
        // Position dans les limites de la carte
        if (newRow >= 0  && newRow < mapEnvironnement.getRows() && newCol >= 0  && newCol < mapEnvironnement.getCols()) {
            // Vérifier obstacle
            if (!mapEnvironnement.getCell(newRow, newCol).isObstacle()) {
                this.row = newRow;
                this.col = newCol;
                return true;
            }
        }
        return false;
    }

}
