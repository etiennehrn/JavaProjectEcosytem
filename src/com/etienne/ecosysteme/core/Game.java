package com.etienne.ecosysteme.core;

import com.etienne.ecosysteme.entities.Player;
import com.etienne.ecosysteme.environment.MapEnvironnement;
import com.etienne.ecosysteme.entities.MapVivant;
import com.etienne.ecosysteme.environment.Pluie;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Random;
import java.io.IOException;

public class Game {

    // Portée de vision du joueur
    private final int visionRange = 15;

    // Délai minimum entre deux déplacements du joueur (en millisecondes)
    private static final long MOVE_DELAY_MS = 80;
    private long lastMoveTime = 0; // Temps du dernier déplacement

    // Carte de l'environnement et des entités
    private final MapEnvironnement mapEnvironnement;
    private final MapVivant mapVivant;

    // Joueur contrôlé par l'utilisateur
    private final Player player;

    // Gestion des cycles jour/nuit
    private final DayNightCycleImpl dayNightCycleImpl;

    // Gestion des précipitations (pluie)
    private final Pluie pluie;

    // Générateur aléatoire pour gérer les événements comme la pluie
    private final Random random = new Random();
    private static final double PROBABILITE_DEBUT_PLUIE = 0.001; // Probabilité de démarrage de la pluie
    private static final double PROBABILITE_FIN_PLUIE = 0.001;  // Probabilité d'arrêt de la pluie

    /**
     * Constructeur de la classe Game.
     * Initialise les cartes, le joueur, et les cycles naturels.
     *
     * @param mapFilePath Chemin du fichier de la carte environnementale.
     * @param mapVivantFilePath Chemin du fichier des entités vivantes.
     * @throws IOException En cas d'erreur de lecture des fichiers.
     */
    public Game(String mapFilePath, String mapVivantFilePath) throws IOException {
        mapEnvironnement = new MapEnvironnement(mapFilePath);
        mapVivant = new MapVivant(mapEnvironnement.getRows(), mapEnvironnement.getCols());
        player = new Player(46, 50, visionRange, mapEnvironnement);

        // Chargement des entités sur la carte
        mapVivant.populate(mapVivantFilePath, 0, 0, 0, mapEnvironnement);

        // Initialisation du cycle jour/nuit avec une durée totale de 240 unités
        dayNightCycleImpl = new DayNightCycleImpl(240);

        // Initialisation des précipitations
        pluie = new Pluie();
    }

    /**
     * Met à jour la position des entités vivantes et gère les événements environnementaux.
     */
    public void update() {
        mapVivant.update(mapEnvironnement, dayNightCycleImpl);

        // Vérifier si la pluie doit commencer
        if (!pluie.isActive() && random.nextDouble() < PROBABILITE_DEBUT_PLUIE) {
            pluie.demarrer();
            System.out.println("Pluie");
            pluie.appliquerEffets(mapVivant);
        }

        // Vérifier si la pluie doit s'arrêter
        if (pluie.isActive() && random.nextDouble() < PROBABILITE_FIN_PLUIE) {
            pluie.arreter();
            pluie.stopperEffets(mapVivant);
        }
    }

    /**
     * Affiche la carte et le temps dans une interface graphique.
     *
     * @param gridPane Le conteneur graphique pour afficher la carte.
     * @param titleSize Taille des cellules affichées.
     */
    public void displayMap(GridPane gridPane, int titleSize) {
        mapEnvironnement.displayMap(gridPane, titleSize, player, mapVivant, dayNightCycleImpl.getLightingColor(), pluie);
        displayTime(gridPane);
    }

    /**
     * Gère le déplacement du joueur en respectant un délai minimum entre les mouvements.
     *
     * @param direction Direction du déplacement ("up", "down", "left", "right").
     */
    public void movePlayer(String direction) {
        long currentTime = System.currentTimeMillis(); // Temps actuel en millisecondes

        // Vérifie si le joueur peut se déplacer (respecte le délai minimal)
        if (currentTime - lastMoveTime >= MOVE_DELAY_MS) {
            lastMoveTime = currentTime; // Mise à jour du dernier déplacement

            // Déplacement du joueur dans la direction spécifiée
            boolean moved = switch (direction.toLowerCase()) {
                case "up" -> player.moveUp();
                case "down" -> player.moveDown();
                case "left" -> player.moveLeft();
                case "right" -> player.moveRight();
                default -> false;
            };
        }
    }

    /**
     * Affiche l'heure actuelle dans l'interface graphique.
     *
     * @param gridPane Le conteneur graphique pour afficher l'horloge.
     */
    public void displayTime(GridPane gridPane) {
        Text timeDisplay = new Text(dayNightCycleImpl.getFormattedTime());
        timeDisplay.setFill(Color.WHITESMOKE);
        timeDisplay.setStyle(
                "-fx-font-size: 8px;" +
                        "-fx-font-family: 'Monospaced';" +
                        "-fx-fill: #FFD700;"
        );

        // Créer le rectangle de fond
        Rectangle background = new Rectangle();
        background.setWidth(25);
        background.setHeight(20);
        background.setArcWidth(10); // Coins arrondis
        background.setArcHeight(10);
        background.setFill(Color.BLACK);
        background.setOpacity(0.6);

        // Empile rectangle et texte dans un StackPane
        StackPane timePane = new StackPane();
        timePane.getChildren().addAll(background, timeDisplay);

        // Positionner le StackPane dans le coin supérieur droit
        gridPane.add(timePane, gridPane.getColumnCount() - 1, 0);
        GridPane.setColumnSpan(timePane, 1);
        GridPane.setRowSpan(timePane, 1);
    }

    /**
     * Retourne le nombre de lignes de la carte.
     * @return Nombre de lignes.
     */
    public int getRows() {
        return mapEnvironnement.getRows();
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     * @return Nombre de colonnes.
     */
    public int getCols() {
        return mapEnvironnement.getCols();
    }

    /**
     * Retourne l'objet représentant la carte environnementale.
     * @return Carte environnementale.
     */
    public MapEnvironnement getMap() {
        return mapEnvironnement;
    }

    /**
     * Retourne le joueur.
     * @return Joueur.
     */
    public Player getPlayer() {
        return player;
    }
}
