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

/**
 * La classe <code>MapGeneration</code> représente une carte générée de manière procédurale.
 * Elle permet de créer, d'afficher et de sauvegarder une carte aléatoire composée de différents types de terrains.
 * Elle utilise la bibliothèque Processing pour le rendu graphique et JavaFX pour l'interface utilisateur.
 */

public class MapGeneration extends PApplet {
    /** Le conteneur principal de la carte, un GridPane de JavaFX */
    public GridPane gridPane;

    /** La grille représentant les cases de la carte */
    private Case[][] grid;

    /** Le nombre de lignes dans la carte */
    private int rows;

    /** Le nombre de colonnes dans la carte */
    private int cols;

    /** Le facteur d'échelle utilisé pour afficher les cases */
    public float scl;

    /** Le conteneur principal pour l'affichage, un VBox JavaFX */
    public final VBox root;

    /**
     * Constructeur de la classe <code>MapGeneration</code>.
     * Initialise les paramètres de la carte, y compris la taille de la grille, l'échelle, et crée le conteneur principal.
     */
    public MapGeneration(){
        this.gridPane = new GridPane();
        this.rows = 800; //à modifier plus tard
        this.cols = 800; // à modifier plus tard
        this.grid = new Case[rows][cols];
        this.scl = 0.07f;
        this.root = new VBox();
        root.getChildren().addAll(gridPane);

    }

    /**
     * Crée une carte aléatoire en remplissant chaque case de la grille avec des types de terrains
     * générés à partir de bruit de Perlin.
     *
     * @param tileSize La taille des tuiles dans la carte.
     */
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

    /**
     * Définit le type de terrain pour une case donnée en fonction de la valeur du bruit de Perlin.
     *
     * @param row L'indice de la ligne de la case.
     * @param col L'indice de la colonne de la case.
     * @param noiseValue La valeur du bruit de Perlin utilisée pour déterminer le type de terrain.
     */
    public void setCase(int row, int col, float noiseValue) {
        if(noiseValue<0.4){
            // Eau
            this.grid[row][col] = CaseFactory.createCase("02"); //Eau centrée
        }else if(noiseValue < 0.6){
            // Sable
            this.grid[row][col] = CaseFactory.createCase("18"); //Sable centrée
        }else if(noiseValue < 0.7){
            // Herbe
            this.grid[row][col] = CaseFactory.createCase("00"); //Herbe centrée
        }else{
            // Foret
            this.grid[row][col] = CaseFactory.createCase("00_A"); //Arbre sur herbe centrée
        }

    }

    /**
     * Affiche la carte en utilisant JavaFX, affichant uniquement la portion visible en fonction de la position du joueur.
     *
     * @param titleSize La taille des tuiles à afficher.
     * @param player L'objet représentant le joueur qui détermine la portion visible de la carte.
     */
    public void displayMap(int titleSize, Player player) {
        gridPane.getChildren().clear();

        // Limites Affichages
        int startCol = Math.max(player.getX() - player.getVisionRange(), 0);
        int startRow = Math.max(player.getY() - player.getVisionRange(), 0);
        int endCol = Math.min(player.getX() + player.getVisionRange(), cols);
        int endRow = Math.min(player.getY() + player.getVisionRange(), rows);


        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                // Créer un conteneur pour empiler les basetypes et les elements
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

    /**
     * Sauvegarde la carte dans un fichier texte sous forme de codes de terrain.
     * Chaque case de la carte est enregistrée avec son code correspondant.
     */
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

    /**
     * Met à jour les bordures de certaines cases (e.g., eau ou sable) en fonction de leurs voisins.
     *
     * @param tileSize La taille des tuiles.
     */
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

    /**
     * Obtient le voisin d'une case à une position donnée dans la grille.
     *
     * @param row La ligne de la case dont on veut obtenir le voisin.
     * @param col La colonne de la case dont on veut obtenir le voisin.
     * @return Le type de la case voisine, ou null si la case est hors des limites de la grille.
     */
    private BaseType getNeighbor(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return grid[row][col].getBaseType();
        }
        return null; // Retourne null si la position est hors limites
    }

    /**
     * Applique un masque de bits pour déterminer le type de case en fonction de ses voisins.
     * Utilise des codes spécifiques pour chaque type de terrain (eau, sable) et les relations
     * avec les cases voisines pour ajuster l'apparence des cases.
     *
     * @param i L'index de la ligne de la case à modifier.
     * @param j L'index de la colonne de la case à modifier.
     * @param neigbhors Un entier représentant un masque de bits des voisins (haut, bas, gauche, droite).
     *                  Chaque bit indique si un voisin possède un type de terrain opposé.
     *                  Par exemple : pour une case d'eau, son type de terrain opposé est le sable ; pour une case de sable, son type de terrain opposé est l'herbe.
     *                  Exemple : pour une case de type "eau", si son masque de voisins est 0b1111, alors cette case d'eau est entourée par quatre terrains de type sable..
     */
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
    /**
     * Retourne la grille actuelle qui contient toutes les cases générées dans la carte.
     *
     * @return Un tableau 2D représentant la grille de cases.
     */
    public Case[][] getGrid() {
        return grid;
    }

    /**
     * Retourne le conteneur principal de l'interface utilisateur pour la carte.
     * Ce conteneur contient la grille et est utilisé pour l'affichage de la carte.
     *
     * @return Le conteneur VBox qui contient la grille de la carte.
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Retourne le nombre de lignes de la carte.
     *
     * @return Le nombre de lignes de la carte (rows).
     */
    public int getRows() {return rows;
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     *
     * @return Le nombre de colonnes de la carte (cols).
     */
    public int getCols() {
        return cols;
    }
}

