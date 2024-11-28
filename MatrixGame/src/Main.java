import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
    // Définition de la taille des cases
    private static final int TITLE_SIZE = 10;

    // Définition de la taille de la matrice de jeu
    private static final int MAP_ROW = 80;
    private static final int MAP_COL = 80;

    // Matrice pour le layout
    private GridPane grid;

    // La map
    private int[][] map;

    @Override
    public void start(Stage primaryStage) {
        initializeMap();
        grid = new GridPane();
        initializeMap();
        Scene scene = new Scene(grid, MAP_ROW*TITLE_SIZE, MAP_COL*TITLE_SIZE);

        primaryStage.setTitle("Ecosystème");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateGrid(); // Afficher la partie initiale de la carte

    }

    private void initializeMap() {
        this.map = new int[MAP_ROW][MAP_COL];
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                map[row][col] = 0; // Terrain vide
            }
        }

        // Ajouter des obstacles
        map[5][5] = 2;
        map[8][8] = 2;
    }

    private void updateGrid() {
        grid.getChildren().clear();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                Rectangle rect = new Rectangle(TITLE_SIZE, TITLE_SIZE);
                if(map[row][col] == 2) {
                    rect.setFill(Color.RED);
                }
                else if(map[row][col] == 0) {
                    rect.setFill(Color.FLORALWHITE);
                }
                grid.add(rect, col, row);
            }
        }
    }
}