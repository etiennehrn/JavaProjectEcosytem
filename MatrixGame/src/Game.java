import javafx.scene.layout.GridPane;

public class Game {
    // Vision du joueur
    private final int visionRange = 10;

    private Map map
    private Player player;

    public Game(String mapFilePath) {
        map = new Map(mapFilePath);
        player = new Player(10, 10, visionRange, map);
    }


    public void displayMap(GridPane gridPane, int titleSize) {
        map.displayMap(gridPane, titleSize, player);
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
        return map.getRows();
    }
    public int getCols() {
        return map.getCols();
    }
    public Map getMap() {
        return map;
    }
    public Player getPlayer() {
        return player;
    }

}

