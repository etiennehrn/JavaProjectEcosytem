package com.example.map;


import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.*;

import static com.example.map.HerbeType.VariantHerbe;

public class MapCreation {
    //Paramètre de la map
    public GridPane gridPane;
    private Case[][] grid;
    private int rows;
    private int cols;

    //Gestion des boutons pour selectionner les BaseTypes et Element
    public final Selectionneur selectionneur;



    //Gestion des clics
    private String BaseTypeSelected;
    private String ElementSelected;

    // Conteneur principal
    private final VBox root;

    public MapCreation(){
        this.gridPane = new GridPane();
        this.rows = 100; //à modifier plus tard
        this.cols = 100; // à modifier plus tard
        this.grid = new Case[rows][cols];
        this.BaseTypeSelected = null;
        this.ElementSelected = null;

        // Initialiser les boutons
        this.selectionneur = new Selectionneur();
        selectionneur.Initialisation();


        // Conteneur principal
        this.root = new VBox();

        // Créer une MenuBar et y ajouter les menus BaseType et Element
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(selectionneur.BaseType, selectionneur.Element);

        // Créer un HBox pour organiser les éléments horizontalement (si vous avez besoin de boutons supplémentaires ou autres)
        HBox buttonsRow = new HBox(10);  // 10 pixels d'espacement entre les boutons
        buttonsRow.getChildren().add(menuBar);  // Ajoutez la MenuBar dans HBox

        // Ajouter la MenuBar et la grille dans le VBox
        root.getChildren().addAll(buttonsRow, gridPane);  // Vous pouvez ajouter gridPane si nécessaire
    }

    public void Initialisation(int titleSize){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.grid[i][j] = new Case(new HerbeType(VariantHerbe.CLAIR), null); //Par défaut, on place de l'herbe partout
            }
        }
    }

    public void SetBaseType(int row, int col) {
        if (selectionneur.BaseTypeSelected != null && row >= 0 && row < rows && col >= 0 && col < cols) {
            BaseType baseType = selectionneur.BaseTypeSelected;
            this.grid[row][col].setBaseType(baseType);
        }
    }

    public void SetElement(int row, int col) {
        if (selectionneur.ElementSelected != null && row >= 0 && row < rows && col >= 0 && col < cols) {
            Element element = selectionneur.ElementSelected;
            this.grid[row][col].setElement(element);
        }
    }


    // Méthode pour afficher la vision de la map dans un GridPane
    public void displayMap(int titleSize, Player player) {
        gridPane.getChildren().clear();

        int startCol = Math.max(Math.min(player.getX()-player.getVisionRange(),cols-player.getVisionRange()), 0);
        int startRow = Math.max(Math.min(player.getY()-player.getVisionRange(),rows-player.getVisionRange()), 0);
        int endCol = Math.min(Math.max(player.getX()+player.getVisionRange(), player.getVisionRange()), cols);
        int endRow = Math.max(Math.max(player.getY()+player.getVisionRange(), player.getVisionRange()), rows);

        // Limites Affichages
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                // Créer un conteneur pour empiler les types de bases et les éléments
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

                // Ajouter le conteneur au GridPane
                gridPane.add(cellPane, col, row);
            }
        }
    }
    public void Save() {
        String filePath = "map.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    writer.write(grid[i][j].getCode() + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }


    //Getters

    public String getElementSelected() {
        return ElementSelected;
    }

    public String getBaseTypeSelected() {
        return BaseTypeSelected;
    }

    public Case[][] getGrid() {
        return grid;
    }

    public int getRows() {return rows;
    }
    public int getCols() {return cols;
    }
    public VBox getRoot() {
        return root;
    }
}

