import javafx.scene.layout.GridPane;

public class Game {
    // Vision du joueur
    private final int visionRange = 10;

    private final MapEnvironnement mapEnvironnement;
    private final MapVivant mapVivant;
    private final Player player;

    public Game(String mapFilePath) {
        mapEnvironnement = new MapEnvironnement(mapFilePath);
        mapVivant = new MapVivant(mapEnvironnement.getRows(), mapEnvironnement.getCols());
        player = new Player(10, 10, visionRange, mapEnvironnement);

        mapVivant.populate(10, 60, mapEnvironnement);
    }

    public void update() {
        mapVivant.update(mapEnvironnement);
    }
    public void displayMap(GridPane gridPane, int titleSize) {
        mapEnvironnement.displayMap(gridPane, titleSize, player, mapVivant);
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

}

