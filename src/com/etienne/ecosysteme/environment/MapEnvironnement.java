package com.etienne.ecosysteme.environment;/* Classe qui représente la map */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.etienne.ecosysteme.entities.EtreVivant;
import com.etienne.ecosysteme.entities.MapVivant;
import com.etienne.ecosysteme.entities.Player;
import com.etienne.ecosysteme.entities.Zombie;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MapEnvironnement {
    // Map pour l'environnement (case)
    private Case[][] grid;
    private int rows;
    private int cols;

    // Constructeur
    public MapEnvironnement(String filePath) {
        loadMapFromFile(filePath);
    }

    // Méthode qui charge la map du fichier à filePath
    private void loadMapFromFile(String filePath) {
        System.out.println("Loading Map from file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = reader.lines().toList();

            rows = lines.size();
            cols = lines.getFirst().split(" ").length;
            grid = new Case[rows][cols];

            for (int i = 0; i < rows; i++) {
                String[] values = lines.get(i).split(" ");
                for (int j = 0; j < cols; j++) {
                    // Utilise CaseFactory pour chaque code
                    grid[i][j] = CaseFactory.createCase(values[j]);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Erreur lors de la lecture de la map");
        }
    }

    // Méthode pour afficher la vision de la map du joueur dans un GridPane
    public void displayMap(GridPane gridPane, int titleSize, Player player, MapVivant mapVivant, Paint lightingColor, Pluie pluie) {
        int visionRange = player.getVisionRange();
        int playerRow = player.getRow();
        int playerCol = player.getCol();

        // Limites Affichages
        int windowSize = 2*visionRange + 1;
        int startRow = Math.max(0, Math.min(playerRow - visionRange, getRows() - windowSize));
        int startCol = Math.max(0, Math.min(playerCol - visionRange, getCols() - windowSize));
        int endRow = Math.min(getRows(), startRow + windowSize);
        int endCol = Math.min(getCols(), startCol + windowSize);

        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol ; col < endCol; col++) {
                // Créer un conteneur pour empiler le fond et le joueur
                StackPane cellPane = new StackPane();

                // Fond (case de la grille) avec texture
                ImageView baseImage = new ImageView(grid[row][col].getBaseType().getTexture());
                baseImage.setFitWidth(titleSize);
                baseImage.setFitHeight(titleSize);
                cellPane.getChildren().add(baseImage);

                // Texture de l'élément (si présent)
                if (grid[row][col].getElement() != null) {
                    ImageView elementImage = new ImageView(grid[row][col].getElement().getTexture());
                    elementImage.setFitWidth(titleSize);
                    elementImage.setFitHeight(titleSize);
                    cellPane.getChildren().add(elementImage);
                }

                // Ajoute êtres vivants s'il y en a
                EtreVivant vivant = mapVivant.getEtreVivant(row, col);
                if (vivant != null) {
                    cellPane.getChildren().add(vivant.getSprite(titleSize));

                    // Ajout barre pour le zombie
                    if (vivant instanceof Zombie zombie) {
                        double barWidth = (((double)zombie.getNourriture()) / ((double)Zombie.NOURRITURE_MAX)) * titleSize;
                        Rectangle foodBar = new Rectangle(barWidth, 5); // Hauteur de la jauge fixe à 5
                        foodBar.setFill(Color.GREEN);
                        // Ajouter la jauge en haut de l'entité
                        StackPane.setAlignment(foodBar, Pos.TOP_CENTER);
                        cellPane.getChildren().add(foodBar);

                    }

                }

                if (pluie.isActive()) {

                    // Créer un rectangle translucide pour le fond
                    Rectangle rainOverlay = new Rectangle(titleSize, titleSize);
                    rainOverlay.setFill(Color.LIGHTBLUE);
                    rainOverlay.setOpacity(0.2); // Ajustez la transparence selon vos besoins

                    Random rand = new Random();
                    cellPane.getChildren().add(rainOverlay);

                    Group rainGroup = new Group();
                    for (int i = 1; i < pluie.getGouttePluie(); i++) {
                        double startX = rand.nextDouble() * titleSize/1.3;
                        double startY = rand.nextDouble() * titleSize / 3;
                        double endX = startX + rand.nextDouble() * titleSize / 10;
                        double endY = startY + rand.nextDouble() * titleSize / 2;
                        Line line = new Line(startX, startY, endX, endY);
                        line.setStroke(Color.LIGHTBLUE);
                        line.setStrokeWidth(1);
                        rainGroup.getChildren().add(line);
                    }
                    cellPane.getChildren().add(rainGroup);

                }


                // Vérifier si c'est la position du joueur
                if (row == player.getRow() && col == player.getCol()) {
                    ImageView playerTexture = new ImageView(player.getSprite());
                    playerTexture.setFitWidth(titleSize);
                    playerTexture.setFitHeight(titleSize);

                    // Ajouter la texture du joueur par-dessus le fond
                    cellPane.getChildren().add(playerTexture);
                }

                // Modification luminosité
                Rectangle lighting = new Rectangle(titleSize, titleSize);
                lighting.setFill(lightingColor);
                cellPane.getChildren().add(lighting);

                // Ajouter le conteneur (fond + joueur) au GridPane
                gridPane.add(cellPane, col - startCol, row - startRow);

            }
        }
    }

    /**
     * Vérifie si un être vivant est visible ou détectable entre deux points selon la distance et les obstacles.
     * On utilise l'algorithme de Bresenham (celui qui permet de tracer une ligne dans un espace discret (matriciel)), en O(dist_max).
     *
     * <p>La méthode prend en compte une distance maximale à laquelle l'être vivant est perceptible,
     * même en présence d'obstacles. Si la distance entre le point de départ et le point d'arrivée
     * est inférieure ou égale à cette distance, l'être vivant peut être détecté, même si des obstacles
     * se trouvent sur le chemin.</p>
     *
     * @param grid       la map
     * @param startRow   la ligne de départ
     * @param startCol   la colonne de départ
     * @param endRow     la ligne d'arrivée
     * @param endCol     la colonne d'arrivée
     * @param maxDistance la distance maximale à laquelle l'être vivant peut être détecté
     * @return {@code true} si l'être vivant est détectable, {@code false} sinon
     */
    public static boolean isPathClear(MapEnvironnement grid, int startRow, int startCol, int endRow, int endCol, double maxDistance) {
        // Au cas ou
        if (startCol == endCol && startRow == endRow) {
            return true;
        }
        // On calcule la distance euclidienne
        double distance = Math.pow(startRow - endRow, 2) + Math.pow(startCol - endCol, 2);

        // Dans le cas où la distance est inférieur à maxDistance la détection est possible même avec obstacle
        if (distance <= maxDistance*maxDistance) {
            return true;
        }

        // On cherche si il y a un obstacle sur le chemin
        int dx = Math.abs(endRow - startRow);
        int dy = Math.abs(endCol - startCol);

        int sx = (startRow < endRow) ? 1 : -1;
        int sy = (startCol < endCol) ? 1 : -1;

        int err = dx - dy;

        int x = startRow;
        int y = startCol;

        while (true) {
            if (grid.getCell(x, y).isObstacle()) {
                return false;
            }

            if (x == endRow && y == endCol) {
                break;
            }
            int e2 = 2*err;
            if (e2 > -dy) {
                // Avance en ligne
                err -= dy;
                x+=sx;
            }

            if (e2 < dx) {
                // Avance en colonne
                err += dx;
                y+=sy;
            }
        }
        return true;
    }

    // Getter et Setter
    public Case getCell(int row, int col) {
        return grid[row][col];
    }
    public void setCell(int row, int col, Case value) {
        grid[row][col] = value;
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
}

