public class Player {
    private int row;
    private int col;
    private final int visionRange;
    private final Map map;


    public Player(int startRow, int startCol, int visionRange, Map map) {
        this.row = startRow;
        this.col = startCol;
        this.visionRange = visionRange;
        this.map = map;

        // On vérifie que la position initiale est valide
        if (map.getCell(startRow, startCol).isObstacle()) {
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
        if (newRow >= 0  && newRow < map.getRows() && newCol >= 0  && newCol < map.getCols()) {
            // Vérifier obstacle
            if (!map.getCell(newRow, newCol).isObstacle()) {
                this.row = newRow;
                this.col = newCol;
                return true;
            }
        }
        return false;
    }

}
