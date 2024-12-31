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
    private MouseHandler mouseHandler = new MouseHandler();
    // Définition de la taille des cases
    private static final int TILE_SIZE = 20;

    // Le jeu
    private MapCreation map;
    private MapGeneration mapGen;

    private void handleMouseInteraction(MouseEvent event, Player player, MapCreation map) {
        MouseHandler.handleMouseInteraction(event, player, map, TILE_SIZE);
    }

    public void start(Stage primaryStage) {
        Player player = new Player();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez-vous générer une carte aléatoire (1) ou la créer vous-même (2) ? (Répondez 1 ou 2)");
        String reponse = scanner.nextLine().toLowerCase();

        if (reponse.equals("2")) {
            map = new MapCreation();
            map.Initialisation(TILE_SIZE);

            // Taille de la scène en fonction de la vision du joueur du jeu
            int sceneWidth = (2 * player.getVisionRange()) * TILE_SIZE;
            int sceneHeight = (2 * player.getVisionRange()+1) * TILE_SIZE; //Pour les boutons

            Scene scene = new Scene(map.getRoot(), sceneWidth, sceneHeight);
            map.displayMap(TILE_SIZE, player);
            map.getRoot().requestFocus(); //Pour empecher les touches du clavier d'influer sur les boutons

            // Titre de la fenêtre
            primaryStage.setTitle("Map Generator");

            primaryStage.setScene(scene);
            primaryStage.show();


            // Événements souris
            scene.setOnMousePressed(event -> MouseHandler.handleMouseInteraction(event, player, map, TILE_SIZE));
            scene.setOnMouseDragged(event -> MouseHandler.handleMouseInteraction(event, player, map, TILE_SIZE));

            //Evenement clavier
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.UP){
                    player.setY(Math.min(Math.max(player.getY() - player.getVitesse(), player.getVisionRange()), map.getRows()-player.getVisionRange()));
                }else if(event.getCode() == KeyCode.DOWN){
                    player.setY(Math.min(Math.max(player.getY() + player.getVitesse(), player.getVisionRange()), map.getRows()-player.getVisionRange()));
                }else if (event.getCode() == KeyCode.LEFT){
                    player.setX(Math.min(Math.max(player.getX() - player.getVitesse(), player.getVisionRange()), map.getCols()-player.getVisionRange()));
                }else if (event.getCode() == KeyCode.RIGHT){
                    player.setX(Math.min(Math.max(player.getX() + player.getVitesse(), player.getVisionRange()), map.getCols()-player.getVisionRange()));
                }
            });

            //Boucle principale
            // Création du Timeline pour mise à jour périodique
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(50), e -> {
                        map.displayMap(TILE_SIZE, player);
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
            timeline.play(); // Lancer la mise à jour périodique
        } else if (reponse.equals("1")) {
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


    }


    public static void main(String[] args) {
        launch(args);
    }
}
