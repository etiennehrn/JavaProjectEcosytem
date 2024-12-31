package com.example.map;

import processing.core.PApplet;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.map.HerbeType.VariantHerbe;

public class MapGeneration extends PApplet {
    //Paramètre de la map
    public GridPane gridPane;
    private Case[][] grid;
    private int rows;
    private int cols;
    private Float[][] mat;
    public float scl;
    public final VBox root;



    public MapGeneration(){
        this.gridPane = new GridPane();
        this.rows = 800; //à modifier plus tard
        this.cols = 800; // à modifier plus tard
        this.grid = new Case[rows][cols];
        this.mat = new Float[rows][cols];
        this.scl = 0.07f;
        this.root = new VBox();
        root.getChildren().addAll(gridPane);

    }

    // Fonction pour créer la carte aléatoire
    public void CreateRandomMap(int tileSize) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                // Utilisation du bruit de Perlin pour générer des valeurs
                this.mat[i][j] = noise(i * scl, j * scl); // Multiplie pour avoir des valeurs entre 0 et 255
                this.grid[i][j] = new Case(new HerbeType(VariantHerbe.CLAIR), null);
                this.setCase(i, j);
            }
        }
    }

    public void setCase(int row, int col) {
        float v = this.mat[row][col];
        if(v<0.4){
            // Eau
            this.grid[row][col].setBaseType(new EauType(EauType.VariantWater.CENTRE)) ;
        }else if(v < 0.6){
            // Sable
            this.grid[row][col].setBaseType(new SableType(SableType.VariantSable.CENTRE)) ;
        }else if(v < 0.7){
            // Herbe
            this.grid[row][col].setBaseType(new HerbeType(VariantHerbe.CLAIR)) ;
        }else{
            // Foret
            this.grid[row][col].setElement(new ArbreElement(ArbreElement.VariantArbre.CLAIR)) ;
        }

    }


    // Méthode pour afficher la vision de la map dans un GridPane
    public void displayMap(int titleSize, Player player) {
        gridPane.getChildren().clear();

        int startCol = Math.max(player.getX() - player.getVisionRange(), 0);
        int startRow = Math.max(player.getY() - player.getVisionRange(), 0);
        int endCol = Math.min(player.getX() + player.getVisionRange(), cols);
        int endRow = Math.min(player.getY() + player.getVisionRange(), rows);

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
        String filePath = "random_map.txt";

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

    public void updateBorders(int tileSize) {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                Case currentCase = grid[i][j];
                BaseType currentType = currentCase.getBaseType();

                // Modifier si la case actuelle est de type "sable" ou "eau"
                if (currentType instanceof SableType || currentType instanceof EauType) {
                    BaseType top = grid[i - 1][j].getBaseType();
                    BaseType bottom = grid[i + 1][j].getBaseType();
                    BaseType left = grid[i][j - 1].getBaseType();
                    BaseType right = grid[i][j + 1].getBaseType();

                    Class<? extends BaseType> oppositeType = null;
                    if (currentType instanceof EauType) {
                        oppositeType = SableType.class;
                    } else if (currentType instanceof SableType) {
                        oppositeType = HerbeType.class;
                    }

                    int mask = 0;

                    if (top.getClass().equals(oppositeType)) {
                        mask |= 0b0001;
                    }
                    if (bottom.getClass().equals(oppositeType)) {
                        mask |= 0b0010;
                    }
                    if (left.getClass().equals(oppositeType)) {
                        mask |= 0b0100;
                    }
                    if (right.getClass().equals(oppositeType)) {
                        mask |= 0b1000;
                    }
                    bitMasking(currentCase, mask);
                }
            }
        }
        updateBorderRow(0);
        updateBorderRow(rows - 1);
        updateBorderCol(0);
        updateBorderCol(cols - 1);

    }

    private void updateBorderRow(int row) {

        for (int col = 1; col < cols - 1; col++) {
            Case currentCase = grid[row][col];
            BaseType currentType = currentCase.getBaseType();

            Class<? extends BaseType> oppositeType = null;
            if (currentType instanceof EauType) {
                oppositeType = SableType.class;
            } else if (currentType instanceof SableType) {
                oppositeType = HerbeType.class;
            }

            BaseType left = grid[row][col - 1].getBaseType();
            BaseType right = grid[row][col + 1].getBaseType();

            int mask = 0;

            if(row == 0){
                // On considère que le type de la case au dessus est le même que la case actuelle
                BaseType bottom = grid[row + 1][col].getBaseType();
                if (bottom.getClass().equals(oppositeType)) mask |= 0b0010;
                if (left.getClass().equals(oppositeType)) mask |= 0b0100;
                if (right.getClass().equals(oppositeType)) mask |= 0b1000;
            } else if (row == rows - 1){
                // On considère que le type de la case en dessous est le même que la case actuelle
                BaseType top = grid[row - 1][col].getBaseType();
                if (top.getClass().equals(oppositeType)) mask |= 0b0001;
                if (left.getClass().equals(oppositeType)) mask |= 0b0100;
                if (right.getClass().equals(oppositeType)) mask |= 0b1000;
            }
            bitMasking(currentCase, mask);
        }
    }

    private void updateBorderCol(int col) {
        for (int row = 1; row < rows - 1; row++) {
            Case currentCase = grid[row][col];
            BaseType currentType = currentCase.getBaseType();

            Class<? extends BaseType> oppositeType = null;
            if (currentType instanceof EauType) {
                oppositeType = SableType.class;
            } else if (currentType instanceof SableType) {
                oppositeType = HerbeType.class;
            }

            BaseType top = grid[row - 1][col].getBaseType();
            BaseType bottom = grid[row + 1][col].getBaseType();

            int mask = 0;

            if(col == 0){
                // On considère que le type de la case au dessus est le même que la case actuelle
                BaseType right = grid[row][col + 1].getBaseType();
                if (top.getClass().equals(oppositeType)) mask |= 0b0001;
                if (bottom.getClass().equals(oppositeType)) mask |= 0b0010;
                if (right.getClass().equals(oppositeType)) mask |= 0b1000;
            }else if(col == cols - 1){
                // On considère que le type de la case au dessus est le même que la case actuelle
                BaseType left = grid[row][col - 1].getBaseType();
                if (top.getClass().equals(oppositeType)) mask |= 0b0001;
                if (bottom.getClass().equals(oppositeType)) mask |= 0b0010;
                if (left.getClass().equals(oppositeType)) mask |= 0b0100;
            }
            bitMasking(currentCase, mask);
        }
    }
    public void bitMasking(Case currentCase, int neigbhors) {
        if (currentCase.getBaseType() instanceof EauType) {
            EauType.VariantWater variant = switch (neigbhors) {
                case 0b1111 -> EauType.VariantWater.UNIQUE;
                case 0b0001 -> EauType.VariantWater.HAUT;
                case 0b0010 -> EauType.VariantWater.BAS;
                case 0b0100 -> EauType.VariantWater.GAUCHE;
                case 0b1000 -> EauType.VariantWater.DROITE;
                case 0b0011 -> EauType.VariantWater.HORIZONTAL_MILIEU;
                case 0b0111 -> EauType.VariantWater.HORIZONTAL_GAUCHE;
                case 0b1011 -> EauType.VariantWater.HORIZONTAL_DROITE;
                case 0b1100 -> EauType.VariantWater.VERTICAL_MILIEU;
                case 0b1110 -> EauType.VariantWater.VERTICAL_BAS;
                case 0b1101 -> EauType.VariantWater.VERTICAL_HAUT;
                case 0b0110 -> EauType.VariantWater.BAS_GAUCHE;
                case 0b1010 -> EauType.VariantWater.BAS_DROITE;
                case 0b0101 -> EauType.VariantWater.HAUT_GAUCHE;
                case 0b1001 -> EauType.VariantWater.HAUT_DROITE;
                default -> EauType.VariantWater.CENTRE;
            };
            currentCase.setBaseType(new EauType(variant));
        } else if (currentCase.getBaseType() instanceof SableType) {
            SableType.VariantSable variant = switch (neigbhors) {
                case 0b1111 -> SableType.VariantSable.UNIQUE;
                case 0b0001 -> SableType.VariantSable.HAUT;
                case 0b0010 -> SableType.VariantSable.BAS;
                case 0b0100 -> SableType.VariantSable.GAUCHE;
                case 0b1000 -> SableType.VariantSable.DROITE;
                case 0b0011 -> SableType.VariantSable.HORIZONTAL_MILIEU;
                case 0b0111 -> SableType.VariantSable.HORIZONTAL_GAUCHE;
                case 0b1011 -> SableType.VariantSable.HORIZONTAL_DROITE;
                case 0b1100 -> SableType.VariantSable.VERTICAL_MILIEU;
                case 0b1110 -> SableType.VariantSable.VERTICAL_BAS;
                case 0b1101 -> SableType.VariantSable.VERTICAL_HAUT;
                case 0b0110 -> SableType.VariantSable.BAS_GAUCHE;
                case 0b1010 -> SableType.VariantSable.BAS_DROITE;
                case 0b0101 -> SableType.VariantSable.HAUT_GAUCHE;
                case 0b1001 -> SableType.VariantSable.HAUT_DROITE;
                default -> SableType.VariantSable.CENTRE;
            };
            currentCase.setBaseType(new SableType(variant));
        }
    }




    //Getters
    public Case[][] getGrid() {
        return grid;
    }
    public VBox getRoot() {
        return root;
    }
    public int getRows() {return rows;
    }
    public int getCols() {
        return cols;
    }
}

