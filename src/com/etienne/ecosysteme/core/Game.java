package com.etienne.ecosysteme.core;

import com.etienne.ecosysteme.entities.Player;
import com.etienne.ecosysteme.environment.MapEnvironnement;
import com.etienne.ecosysteme.entities.MapVivant;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class Game {
    // Vision du joueur
    private final int visionRange = 15;

    // Pour limiter déplacement du joueuer
    private static final long MOVE_DELAY_MS = 100;
    private long lastMoveTime = 0; // Time du dernier déplacement

    // La map
    private final MapEnvironnement mapEnvironnement;
    private final MapVivant mapVivant;
    private final Player player;

    // Cycle Jour/Nuit
    private final DayNightCycleImpl dayNightCycleImpl;

    // Constructeur
    public Game(String mapFilePath, String mapVivantFilePath) throws IOException {
        mapEnvironnement = new MapEnvironnement(mapFilePath);
        mapVivant = new MapVivant(mapEnvironnement.getRows(), mapEnvironnement.getCols());
        player = new Player(46, 50, visionRange, mapEnvironnement);

        mapVivant.populate(mapVivantFilePath, 0, 0, 0, mapEnvironnement);

        // Initialisation cycle jour.nuit de durée total 240
        dayNightCycleImpl = new DayNightCycleImpl(240);
    }

    // Update position des etres vivants
    public void update() {
        mapVivant.update(mapEnvironnement);
    }

    // Affiche la carte et le temps
    public void displayMap(GridPane gridPane, int titleSize) {
        mapEnvironnement.displayMap(gridPane, titleSize, player, mapVivant, dayNightCycleImpl.getLightingColor());
        displayTime(gridPane);
    }

    // déplacement Joueur
    public void movePlayer(String direction) {
        long currentTime = System.currentTimeMillis(); // Temps actuel en millisecondes

        // Vérifie si le joueur peut se déplacer (respecte le délai minimal)
        if (currentTime - lastMoveTime >= MOVE_DELAY_MS) {
            // Met à jour le temps du dernier déplacement
            lastMoveTime = currentTime;

            // Déplacement du joueur
            boolean moved = switch (direction.toLowerCase()) {
                case "up" -> player.moveUp();
                case "down" -> player.moveDown();
                case "left" -> player.moveLeft();
                case "right" -> player.moveRight();
                default -> false;
                };
        }
    }

    // Affiche l'horloge
    public void displayTime(GridPane gridPane) {
        Text timeDisplay = new Text(dayNightCycleImpl.getFormattedTime());
        timeDisplay.setFill(Color.WHITESMOKE);
        timeDisplay.setStyle(
                "-fx-font-size: 8px;" +          // Taille de la police légèrement augmentée
                        "-fx-font-family: 'Monospaced';" + // Police intégrée qui rappelle le pixel art
                        "-fx-fill: #FFD700;"               // Couleur or pour un effet rétro
        );

        // Créer le rectangle de fond
        Rectangle background = new Rectangle();
        background.setWidth(25);
        background.setHeight(20);
        background.setArcWidth(10); // Coins arrondis
        background.setArcHeight(10);
        background.setFill(Color.BLACK);
        background.setOpacity(0.6);

        // On empile rectange et texte
        StackPane timePane = new StackPane();
        timePane.getChildren().addAll(background, timeDisplay);

        // Positionner le StackPane dans le coin supérieur droit
        gridPane.add(timePane, gridPane.getColumnCount() - 1, 0);
        GridPane.setColumnSpan(timePane, 1);
        GridPane.setRowSpan(timePane, 1);
    }

    // Getter et Setter
    public int getRows() {
        return mapEnvironnement.getRows();
    }
    public int getCols() {
        return mapEnvironnement.getCols();
    }
    public MapEnvironnement getMap() {
        return mapEnvironnement;
    }
    public Player getPlayer() {
        return player;
    }
}

