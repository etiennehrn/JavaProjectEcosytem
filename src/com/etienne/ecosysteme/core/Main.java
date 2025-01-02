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
    // Définition de la taille des cases
    private static final int TILE_SIZE = 25;

    // Matrice pour le layout
    private GridPane grid;

    // Le jeu
    private Game game;

    // Definition chemin de la carte
    private final String mapFilePath = Objects.requireNonNull(getClass().getResource("/ressources/map/map_case/nnew_map1_test.txt")).getPath();
    private final String musicFilePath = Objects.requireNonNull(getClass().getResource("/ressources/audio/background_music.mp3")).toExternalForm();
    private final String iconFilePath = Objects.requireNonNull(getClass().getResource("/ressources/icons/icone1.jpg")).toExternalForm();
    private final String mapVivantFilePath = Objects.requireNonNull(getClass().getResource("/ressources/map/map_vivant/map_vivant.txt")).getPath();

    // Pour le son
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws IOException {

        grid = new GridPane();
        game = new Game(mapFilePath, mapVivantFilePath);

        game.displayMap(grid, TILE_SIZE);

        // Taille de la scene en fonction de la vision du joueur du jeu
        int visionRange = game.getPlayer().getVisionRange();
        int sceneSize = ((2 * visionRange) + 1)*TILE_SIZE;

        Scene scene = new Scene(grid, sceneSize, sceneSize);

        // TITRE
        primaryStage.setTitle("Ecosystème");

        // ICONE
        Image icon = new Image(iconFilePath);
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.show();

        // MuSique
        playBackgroundMusic();

        // Mise à jour des déplacements 200 ms
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            game.update();
            grid.getChildren().clear();
            game.displayMap(grid, TILE_SIZE);
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();


        // Evenements clavier
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP -> game.movePlayer("up");
                case DOWN -> game.movePlayer("down");
                case LEFT -> game.movePlayer("left");
                case RIGHT -> game.movePlayer("right");
                default -> System.out.println("Invalid key");
            }

            // On réactualise l'affichage
            grid.getChildren().clear();
            game.displayMap(grid, TILE_SIZE);
        });

    }

    // Méthode pour jouer la musique d'ambiance
    private void playBackgroundMusic() {

        Media media = new Media(musicFilePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        launch(args);
    }

}