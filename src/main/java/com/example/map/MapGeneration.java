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
                float noiseValue = noise(i * scl, j * scl); // Multiplie pour avoir des valeurs entre 0 et 255
                this.grid[i][j] = new Case(new HerbeType(VariantHerbe.CLAIR), null);
                this.setCase(i, j, noiseValue);
            }
        }
    }

    public void setCase(int row, int col, float noiseValue) {
        if(noiseValue<0.4){
            // Eau
            this.grid[row][col] = CaseFactory.createCase("02");
        }else if(noiseValue < 0.6){
            // Sable
            this.grid[row][col] = CaseFactory.createCase("18");
        }else if(noiseValue < 0.7){
            // Herbe
            this.grid[row][col] = CaseFactory.createCase("00");
        }else{
            // Foret
            this.grid[row][col] = CaseFactory.createCase("00_A");
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Case currentCase = grid[i][j];
                BaseType currentType = currentCase.getBaseType();

                // Modifier si la case actuelle est de type "sable" ou "eau"
                if (currentType instanceof SableType || currentType instanceof EauType) {

                    // Récupérer les voisins en tenant compte des bordures
                    BaseType top = getNeighbor(i - 1, j); // Voisin du dessus
                    BaseType bottom = getNeighbor(i + 1, j); // Voisin du dessous
                    BaseType left = getNeighbor(i, j - 1); // Voisin de gauche
                    BaseType right = getNeighbor(i, j + 1); // Voisin de droite

                    Class<? extends BaseType> oppositeType = null;
                    if (currentType instanceof EauType) {
                        oppositeType = SableType.class;
                    } else if (currentType instanceof SableType) {
                        oppositeType = HerbeType.class;
                    }

                    int mask = 0;

                    if (top != null && top.getClass().equals(oppositeType)) {
                        mask |= 0b0001;
                    }
                    if (bottom != null && bottom.getClass().equals(oppositeType)) {
                        mask |= 0b0010;
                    }
                    if (left != null && left.getClass().equals(oppositeType)) {
                        mask |= 0b0100;
                    }
                    if (right != null && right.getClass().equals(oppositeType)) {
                        mask |= 0b1000;
                    }
                    bitMasking(i, j, mask);
                }
            }
        }

    }

    private BaseType getNeighbor(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return grid[row][col].getBaseType();
        }
        return null; // Retourne null si la position est hors limites
    }

    public void bitMasking(int i, int j, int neigbhors) {
        Case currentCase = grid[i][j];
        Element element = currentCase.getElement();
        if (currentCase.getBaseType() instanceof EauType) {
            String code = switch (neigbhors) {
                case 0b1111 -> "17"; //UNIQUE
                case 0b0001 -> "05"; // HAUT
                case 0b0010 -> "06"; // BAS
                case 0b0100 -> "03"; // GAUCHE
                case 0b1000 -> "04"; // DROITE
                case 0b0011 -> "16"; //HORIZONTAL_MILIEU
                case 0b0111 -> "14"; // HORIZONTAL_GAUCHE
                case 0b1011 -> "15"; // HORIZONTAL_DROITE
                case 0b1100 -> "13"; // VERTICAL_MILIEU
                case 0b1110 -> "12"; // VERTICAL_BAS
                case 0b1101 -> "11"; // VERTICAL_HAUT
                case 0b0110 -> "09"; // BAS_GAUCHE
                case 0b1010 -> "10"; // BAS_DROITE
                case 0b0101 -> "07"; // HAUT_GAUCHE
                case 0b1001 -> "08"; // HAUT_DROITE
                default -> "02";// CENTRE
            };
            if(element != null) grid[i][j] = CaseFactory.createCase(code + "_" + element.getCode());
            else grid[i][j] = CaseFactory.createCase(code);
        } else if (currentCase.getBaseType() instanceof SableType) {
            String code = switch (neigbhors) {
                case 0b1111 -> "33"; // UNIQUE
                case 0b0001 -> "21"; // HAUT
                case 0b0010 -> "22"; // BAS
                case 0b0100 -> "19"; // GAUCHE
                case 0b1000 -> "20"; // DROITE
                case 0b0011 -> "32"; // HORIZONTAL_MILIEU
                case 0b0111 -> "30"; // HORIZONTAL_GAUCHE
                case 0b1011 -> "31"; // HORIZONTAL_DROITE
                case 0b1100 -> "29"; // VERTICAL_MILIEU
                case 0b1110 -> "28"; // VERTICAL_BAS
                case 0b1101 -> "27"; // VERTICAL_HAUT
                case 0b0110 -> "25"; // BAS_GAUCHE
                case 0b1010 -> "26"; // BAS_DROITE
                case 0b0101 -> "23"; // HAUT_GAUCHE
                case 0b1001 -> "24"; // HAUT_DROITE
                default -> "18"; // CENTRE
            };
            if(element != null) grid[i][j] = CaseFactory.createCase(code + "_" + element.getCode());
            else grid[i][j] = CaseFactory.createCase(code);
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

