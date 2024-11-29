import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.util.Objects;


public class Main extends Application {
    // Définition de la taille des cases
    private static final int TILE_SIZE = 30;

    // Matrice pour le layout
    private GridPane grid;

    // Le jeu
    private Game game;

    // Definition chemin de la carte
    private final String mapFilePath = Objects.requireNonNull(getClass().getResource("/ressources/map.txt")).getPath();

    @Override
    public void start(Stage primaryStage) {
        grid = new GridPane();
        game = new Game(mapFilePath);

        game.displayMap(grid, TILE_SIZE);

        // Taille de la scene en fonction de la vision du joueur du jeu
        int visionRange = game.getPlayer().getVisionRange();
        int sceneSize = ((2 * visionRange) + 1)*TILE_SIZE;

        Scene scene = new Scene(grid, sceneSize, sceneSize);

        primaryStage.setTitle("Ecosystème");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Evenements clavier
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP -> game.movePlayer("up");
                case DOWN -> game.movePlayer("down");
                case LEFT -> game.movePlayer("left");
                case RIGHT -> game.movePlayer("right");
                default -> System.out.println("Invalid key");
            }
            // On réactulise l'affichage
            grid.getChildren().clear();
            game.displayMap(grid, TILE_SIZE);
        });

    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        launch(args);
    }


}