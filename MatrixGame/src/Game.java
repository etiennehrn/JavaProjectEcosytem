public class Game {
    private final int[][] map; // Matrice complète représentant la carte
    private final int[][] viewport; // Fenêtre visible
    private final int viewportRows; // Nombre de lignes dans la fenêtre visible
    private final int viewportCols; // Nombre de colonnes dans la fenêtre visible
    private int playerRow; // Position du joueur (ligne)
    private int playerCol; // Position du joueur (colonne)

    public Game(int mapRows, int mapCols, int viewportRows, int viewportCols) {
        this.map = new int[mapRows][mapCols];
        this.viewport = new int[viewportRows][viewportCols];
        this.viewportRows = viewportRows;
        this.viewportCols = viewportCols;
        initializeGame();
    }

    private void initializeGame() {
        // Initialiser la carte complète
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                map[row][col] = 0; // Terrain vide
            }
        }

        // Ajouter des obstacles
        map[5][5] = 2;
        map[8][8] = 2;

        // Positionner le joueur au centre de la carte
        playerRow = map.length / 2;
        playerCol = map[0].length / 2;
        map[playerRow][playerCol] = 1;

        updateViewport(); // Initialiser la fenêtre visible
    }

    public int[][] getViewport() {
        return viewport;
    }

    public void movePlayer(int rowOffset, int colOffset) {
        int newRow = playerRow + rowOffset;
        int newCol = playerCol + colOffset;

        // Vérifier les limites de la carte
        if (newRow >= 0 && newRow < map.length && newCol >= 0 && newCol < map[0].length) {
            // Vérifier si la nouvelle position est libre (pas un obstacle)
            if (map[newRow][newCol] != 2) {
                // Mettre à jour la position du joueur
                map[playerRow][playerCol] = 0; // Ancienne position
                playerRow = newRow;
                playerCol = newCol;
                map[playerRow][playerCol] = 1; // Nouvelle position
                updateViewport(); // Mettre à jour la fenêtre visible
            }
        }
    }

    private void updateViewport() {
        // Calculer les limites de la fenêtre visible
        int startRow = Math.max(0, playerRow - viewportRows / 2);
        int startCol = Math.max(0, playerCol - viewportCols / 2);
        int endRow = Math.min(map.length, startRow + viewportRows);
        int endCol = Math.min(map[0].length, startCol + viewportCols);

        // Copier les données de la carte dans la fenêtre visible
        for (int row = 0; row < viewportRows; row++) {
            for (int col = 0; col < viewportCols; col++) {
                if (startRow + row < endRow && startCol + col < endCol) {
                    viewport[row][col] = map[startRow + row][startCol + col];
                } else {
                    viewport[row][col] = 0; // Cellule vide si hors des limites
                }
            }
        }
    }
}
