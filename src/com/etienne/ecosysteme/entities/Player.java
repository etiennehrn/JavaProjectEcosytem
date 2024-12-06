package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class Player {
    // Caractèristiques joeueur
    private int row;
    private int col;
    private final int visionRange;

    // Map sur laquelle il évolue
    private final MapEnvironnement mapEnvironnement;

    // Sprites
    private static final Image[] PLAYER_SPRITES_UP = new Image[3];
    private static final Image[] PLAYER_SPRITES_DOWN = new Image[3];
    private static final Image[] PLAYER_SPRITES_LEFT = new Image[3];
    private static final Image[] PLAYER_SPRITES_RIGHT = new Image[3];

    // Pour l'animation de son déplacement
    private int animationFrame = 0;
    private String lastDirection = "down"; // La direction du pelo

    // Pour le son lors de ses déplacements
    private static final Media MOVE_SOUND;
    private static final MediaPlayer MOVE_PLAYER;

    // Téléchargement des ressources
    static {
        try {
            for (int i = 0; i < 3; i++) {
                PLAYER_SPRITES_UP[i] = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("/ressources/sprites/player/player1/player_up_" + i + ".png")));
                PLAYER_SPRITES_DOWN[i] = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("/ressources/sprites/player/player1/player_down_" + i + ".png")));
                PLAYER_SPRITES_LEFT[i] = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("/ressources/sprites/player/player1/player_left_" + i + ".png")));
                PLAYER_SPRITES_RIGHT[i] = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("/ressources/sprites/player/player1/player_right_" + i + ".png")));
            }

            // Chargement du son de déplacement
            MOVE_SOUND = new Media(Objects.requireNonNull(Player.class.getResource("/ressources/audio/Player_Sound/move1.mp3")).toExternalForm());
            MOVE_PLAYER = new MediaPlayer(MOVE_SOUND);

        } catch (Exception e) {
            throw new RuntimeException("Erreur chargement sprites", e);
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
        lastDirection = "up";
        return moveTo(row - 1, col);
    }
    public boolean moveDown() {
        lastDirection = "down";
        return moveTo(row + 1, col);
    }
    public boolean moveLeft() {
        lastDirection = "left";
        return moveTo(row, col - 1);
    }
    public boolean moveRight() {
        lastDirection = "right";
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

    // Renvoie l'image
    public Image getCurrentSprite() {
        switch (lastDirection) {
            case "up":
                return PLAYER_SPRITES_UP[animationFrame];
            case "down":
                return PLAYER_SPRITES_DOWN[animationFrame];
            case "left":
                return PLAYER_SPRITES_LEFT[animationFrame];
            case "right":
                return PLAYER_SPRITES_RIGHT[animationFrame];
            default:
                return PLAYER_SPRITES_DOWN[0];
        }
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
