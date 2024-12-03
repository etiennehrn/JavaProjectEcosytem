package com.etienne.ecosysteme.environment;/* Classe qui représente la map */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.etienne.ecosysteme.entities.EtreVivant;
import com.etienne.ecosysteme.entities.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
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

    /*
    // Méthode pour convertir Value en Case
    private Case parseCase(String value) {
        return switch (value) {
            case "0" -> herbeCase;
            case "1" -> murCase;
            case "2" -> herbeArbreCase;
            case "3" -> herbeBuissonCase;
            case "4" -> herbeCaillouxCase;
            case "5" -> herbeTroncCase;
            case "6" -> dalleCase;
            case "7" -> herbeChampigonCase;
            case "8" -> eauCase;
            case "9" -> nenupharEauCase;
            default -> throw new IllegalArgumentException("Le valeur de case n'existe pas");
        };
    }
    */


    // Méthode pour afficher la vision de la map du joueur dans un GridPane
    public void displayMap(GridPane gridPane, int titleSize, Player player, MapVivant mapVivant, Paint lightingColor) {
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

                // Ajoute êtres vivants si il y en a
                EtreVivant vivant = mapVivant.getEtreVivant(row, col);
                if (vivant != null) {
                    cellPane.getChildren().add(vivant.getSprite(titleSize));
                }

                // Vérifier si c'est la position du joueur
                if (row == player.getRow() && col == player.getCol()) {
                    ImageView playerTexture = new ImageView(player.getCurrentSprite());
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

