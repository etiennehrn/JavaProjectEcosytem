import javafx.scene.layout.GridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Game {
    // Vision du joueur
    private final int visionRange = 15;

    private final MapEnvironnement mapEnvironnement;
    private final MapVivant mapVivant;
    private final Player player;

    // Gestion Cylcle jour/nuit
    public enum Cycle {
        JOUR, CREPUSCULE, NUIT, AURORE
    }

    private Cycle cycle = Cycle.JOUR;
    private int cycleDuration = 50;
    private int timeCounter;
    private Timeline cycleTimeline;

    public Game(String mapFilePath) {
        mapEnvironnement = new MapEnvironnement(mapFilePath);
        mapVivant = new MapVivant(mapEnvironnement.getRows(), mapEnvironnement.getCols());
        player = new Player(50, 50, visionRange, mapEnvironnement);

        mapVivant.populate(20, 20, 10, mapEnvironnement);

        // Début du cycle
        startDayNightCycle();
    }

    public void update() {
        mapVivant.update(mapEnvironnement);
    }
    public void displayMap(GridPane gridPane, int titleSize) {
        mapEnvironnement.displayMap(gridPane, titleSize, player, mapVivant, getLightingColor());
    }

    public void movePlayer(String direction) {
        switch (direction.toLowerCase()) {
            case "up" -> player.moveUp();
            case "down" -> player.moveDown();
            case "left" -> player.moveLeft();
            case "right" -> player.moveRight();
            default -> System.out.println("Invalid direction");
        }
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

    // Gestion Cycle JOUR./NUIT


    public Cycle getCycle() {
        return cycle;
    }

    private void startDayNightCycle() {
        cycleTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeCounter++;
            if (timeCounter >= cycleDuration) {
                timeCounter = 0;
                advanceCycle();
            }
        }));
        cycleTimeline.setCycleCount(Timeline.INDEFINITE);
        cycleTimeline.play();
    }

    private void advanceCycle() {

        switch (cycle) {
            case JOUR -> cycle = Cycle.CREPUSCULE;
            case CREPUSCULE -> cycle = Cycle.NUIT;
            case NUIT -> cycle = Cycle.AURORE;
            case AURORE -> cycle = Cycle.JOUR;
        }

    }

    // Méthode pour gérer la couleur en fct du cycle
    public Color getLightingColor() {

        return switch (cycle) {
            case JOUR -> Color.TRANSPARENT;
            case CREPUSCULE -> Color.rgb(255, 140, 0, 0.3);
            case NUIT -> Color.rgb(0, 0, 50, 0.5);
            case AURORE -> Color.rgb(255, 223, 186, 0.3);
        };
    }
}

