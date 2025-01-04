package com.etienne.ecosysteme.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    // Taille de chaque case de la grille
    private static final int TILE_SIZE = 25;

    // Grille utilisée pour afficher les éléments du jeu
    private GridPane grid;

    // Instance du jeu
    private Game game;

    // Chemins des fichiers de ressources
    private final String mapFilePath = Objects.requireNonNull(getClass().getResource("/ressources/map/map_case/maplef.txt")).getPath();
    private final String musicFilePath = Objects.requireNonNull(getClass().getResource("/ressources/audio/background_music.mp3")).toExternalForm();
    private final String iconFilePath = Objects.requireNonNull(getClass().getResource("/ressources/icons/icone1.jpg")).toExternalForm();
    private final String mapVivantFilePath = Objects.requireNonNull(getClass().getResource("/ressources/map/map_vivant/maplef_viv.txt")).getPath();

    // Lecteur média pour la musique d'ambiance
    private MediaPlayer mediaPlayer;

    /**
     * Méthode principale de JavaFX pour démarrer l'application.
     * @param primaryStage La fenêtre principale de l'application.
     * @throws IOException Si les fichiers nécessaires ne sont pas accessibles.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Initialisation de la grille et du jeu
        grid = new GridPane();
        game = new Game(mapFilePath, mapVivantFilePath);

        // Affiche la carte initiale dans la grille
        game.displayMap(grid, TILE_SIZE);

        // Calcule la taille de la fenêtre en fonction de la vision du joueur
        int visionRange = game.getPlayer().getVisionRange();
        int sceneSize = ((2 * visionRange) + 1) * TILE_SIZE;

        Scene scene = new Scene(grid, sceneSize, sceneSize);

        // Définit le titre de la fenêtre
        primaryStage.setTitle("Ecosystème");

        // Définit l'icône de l'application
        Image icon = new Image(iconFilePath);
        primaryStage.getIcons().add(icon);

        // Affiche la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.show();

        // Lance la musique d'ambiance
        playBackgroundMusic();

        // Boucle de mise à jour du jeu toutes les 50 millisecondes
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            game.update(); // Met à jour les entités et les événements
            grid.getChildren().clear(); // Efface l'ancienne grille
            game.displayMap(grid, TILE_SIZE); // Réaffiche la nouvelle carte
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE); // Boucle infinie
        gameLoop.play();

        // Gestion des événements clavier pour déplacer le joueur
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP -> game.movePlayer("up");
                case DOWN -> game.movePlayer("down");
                case LEFT -> game.movePlayer("left");
                case RIGHT -> game.movePlayer("right");
                default -> System.out.println("Invalid key"); // Message pour une touche non prise en charge
            }

            // Met à jour l'affichage après un déplacement
            grid.getChildren().clear();
            game.displayMap(grid, TILE_SIZE);
        });
    }

    /**
     * Joue une musique d'ambiance en arrière-plan.
     */
    private void playBackgroundMusic() {
        Media media = new Media(musicFilePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Répétition infinie
        mediaPlayer.setVolume(0.1); // Volume réduit pour un effet discret
        mediaPlayer.play();
    }

    /**
     * Point d'entrée principal pour lancer l'application.
     * @param args Arguments de ligne de commande.
     */
    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }
}
