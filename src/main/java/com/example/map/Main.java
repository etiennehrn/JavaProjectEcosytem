package com.example.map;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.input.MouseEvent;

import java.util.Scanner;


public class Main extends javafx.application.Application {
    // Définition de la taille des cases
    private static final int TILE_SIZE = 20;

    // Le jeu
    private MapGeneration mapGen;


    public void start(Stage primaryStage) {
        Player player = new Player();
        Scanner scanner = new Scanner(System.in);

        mapGen = new MapGeneration();
        mapGen.CreateRandomMap(TILE_SIZE);
        mapGen.updateBorders(TILE_SIZE);
        mapGen.Save();
        // Taille de la scène en fonction de la vision du joueur du jeu
        int sceneWidth = (2 * player.getVisionRange()) * TILE_SIZE;
        int sceneHeight = (2 * player.getVisionRange()) * TILE_SIZE;

        Scene scene = new Scene(mapGen.getRoot(), sceneWidth, sceneHeight);
        mapGen.displayMap(TILE_SIZE, player);
        mapGen.getRoot().requestFocus(); //Pour empecher les touches du clavier d'influer sur les boutons

        // Titre de la fenêtre
        primaryStage.setTitle("Map Generator");

        primaryStage.setScene(scene);
        primaryStage.show();

        //Evenement clavier
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP){
                player.setY(Math.min(Math.max(player.getY() - player.getVitesse(), player.getVisionRange()), mapGen.getRows()-player.getVisionRange()));
            }else if(event.getCode() == KeyCode.DOWN){
                player.setY(Math.min(Math.max(player.getY() + player.getVitesse(), player.getVisionRange()), mapGen.getRows()-player.getVisionRange()));
            }else if (event.getCode() == KeyCode.LEFT){
                player.setX(Math.min(Math.max(player.getX() - player.getVitesse(), player.getVisionRange()), mapGen.getCols()-player.getVisionRange()));
            }else if (event.getCode() == KeyCode.RIGHT){
                player.setX(Math.min(Math.max(player.getX() + player.getVitesse(), player.getVisionRange()), mapGen.getCols()-player.getVisionRange()));
            }
        });

        //Boucle principale
        // Création du Timeline pour mise à jour périodique
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    mapGen.displayMap(TILE_SIZE, player);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
        timeline.play(); // Lancer la mise à jour périodique

    }



    public static void main(String[] args) {
        launch(args);
    }
}
