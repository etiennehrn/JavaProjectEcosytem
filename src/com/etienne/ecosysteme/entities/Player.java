package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;
import java.util.Random;

public class Player {
    // Caractèristiques joueur
    private int row;
    private int col;
    private final int visionRange;

    // Map sur laquelle il évolue
    private final MapEnvironnement mapEnvironnement;

    // Pour l'animation de son déplacement, attention, doit correspondre avec SpriteManager
    private static final int NUM_PLAYER_STYLES = 1;
    private int styleIndex = 0;
    private int animationFrame = 0;
    private EtreVivant.Direction lastDirection = EtreVivant.Direction.DOWN; // La direction du pelo

    // Pour le son lors de ses déplacements
    private static final Media MOVE_SOUND;
    private static final MediaPlayer MOVE_PLAYER;

    // Téléchargement des ressources
    static {
        try {
            MOVE_SOUND = new Media(Objects.requireNonNull(Player.class.getResource("/ressources/audio/Player_Sound/move1.mp3")).toExternalForm());
            MOVE_PLAYER = new MediaPlayer(MOVE_SOUND);

        } catch (Exception e) {
            throw new RuntimeException("Erreur chargement sons", e);
        }

    }

    //Constructeur
    public Player(int startRow, int startCol, int visionRange, MapEnvironnement mapEnvironnement) {
        this.row = startRow;
        this.col = startCol;
        this.visionRange = visionRange;
        this.mapEnvironnement = mapEnvironnement;

        // On vérifie que la position initiale est valide
        if (mapEnvironnement.getCell(startRow, startCol).isObstacle()) {
            throw new IllegalArgumentException("Position initiale invalide : il y a un obstacle");
        }
        Random random = new Random();
        this.styleIndex = random.nextInt(NUM_PLAYER_STYLES);

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
        lastDirection = EtreVivant.Direction.UP;
        return moveTo(row - 1, col);
    }
    public boolean moveDown() {
        lastDirection = EtreVivant.Direction.DOWN;
        return moveTo(row + 1, col);
    }
    public boolean moveLeft() {
        lastDirection = EtreVivant.Direction.LEFT;
        return moveTo(row, col - 1);
    }
    public boolean moveRight() {
        lastDirection = EtreVivant.Direction.RIGHT;
        return moveTo(row, col + 1);
    }

    // DéplaceVers
    private boolean moveTo(int newRow, int newCol) {
        // Position dans les limites de la carte
        if (newRow >= 0  && newRow < mapEnvironnement.getRows() && newCol >= 0  && newCol < mapEnvironnement.getCols()) {
            // Vérifier obstacle
            if (!mapEnvironnement.getCell(newRow, newCol).isObstacle()) {
                this.row = newRow;
                this.col = newCol;
                animationFrame = (animationFrame + 1) % 3; // Sprite d'après

                // Son
                playMoveSound();
                return true;
            }
        }
        return false;
    }

    // Obtenir le sprite actuel basé sur la direction et l'animation
    public Image getSprite() {
        Image[] directionSprites = SpriteManager.getInstance().getSprites(String.format("player%d", styleIndex), lastDirection);
        return directionSprites[animationFrame];
    }

    // Pour jouer le bruit du déplacement
    private void playMoveSound() {
        /* Moins couteux, mais ne joue pas quand c'est rapide
        MOVE_PLAYER.stop();
        MOVE_PLAYER.play();
        */
        MediaPlayer movePlayer = new MediaPlayer(MOVE_SOUND);
        movePlayer.setVolume(0.1);
        movePlayer.play();
    }

}
