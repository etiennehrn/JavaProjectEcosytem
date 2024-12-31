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
            this.grid[row][col].setBaseType(new SandType(SandType.VariantHerbeJaune.CENTRE)) ;
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

                // Vérifiez uniquement si la case actuelle est de type "sable" ou "eau"
                if (currentType instanceof SandType || currentType instanceof EauType) {
                    BaseType top = grid[i - 1][j].getBaseType();
                    BaseType bottom = grid[i + 1][j].getBaseType();
                    BaseType left = grid[i][j - 1].getBaseType();
                    BaseType right = grid[i][j + 1].getBaseType();

                    // D'abord on s'occupe des bordures Sable / Herbe
                    if (currentType instanceof SandType && right instanceof SandType && bottom instanceof SandType
                            && top instanceof HerbeType && left instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT_GAUCHE));
                    } else if (currentType instanceof SandType && left instanceof SandType && bottom instanceof SandType
                            && top instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT_DROITE));
                    } else if (currentType instanceof SandType && left instanceof SandType && top instanceof SandType
                            && bottom instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS_DROITE));
                    } else if (currentType instanceof SandType && right instanceof SandType && top instanceof SandType
                            && bottom instanceof HerbeType && left instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS_GAUCHE));
                    } else if (currentType instanceof SandType && right instanceof SandType && top instanceof HerbeType
                            && bottom instanceof HerbeType && left instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HORIZONTAL_GAUCHE));
                    } else if (currentType instanceof SandType && left instanceof SandType && top instanceof HerbeType
                            && bottom instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HORIZONTAL_DROITE));
                    } else if (currentType instanceof SandType && left instanceof SandType && top instanceof HerbeType
                            && bottom instanceof HerbeType && right instanceof SandType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HORIZONTAL_MILIEU));
                    } else if (currentType instanceof SandType && bottom instanceof SandType && top instanceof HerbeType
                            && left instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_HAUT));
                    } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof HerbeType
                            && left instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_BAS));
                    } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof SandType
                            && left instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_MILIEU));
                    } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof SandType
                            && left instanceof HerbeType && right instanceof SandType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.GAUCHE));
                    } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof SandType
                            && right instanceof HerbeType && left instanceof SandType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.DROIT));
                    } else if (currentType instanceof SandType && left instanceof SandType && right instanceof SandType
                            && bottom instanceof HerbeType && top instanceof SandType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS));
                    } else if (currentType instanceof SandType && left instanceof SandType && right instanceof SandType
                            && top instanceof HerbeType && bottom instanceof SandType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT));
                    } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof HerbeType
                            && left instanceof HerbeType && right instanceof HerbeType) {
                        currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.UNIQUE));
                    }

                    // Puis on s'occupe des bordures Eau / Sable

                    else if (currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType
                            && left instanceof SandType && right instanceof SandType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.UNIQUE));
                    } else if (currentType instanceof EauType && right instanceof SandType && bottom instanceof SandType
                            && top instanceof EauType && left instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.BAS_DROITE));
                    } else if (currentType instanceof EauType && left instanceof SandType && bottom instanceof SandType
                            && top instanceof EauType && right instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.BAS_GAUCHE));
                    } else if (currentType instanceof EauType && left instanceof SandType && top instanceof SandType
                            && bottom instanceof EauType && right instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT_GAUCHE));
                    } else if (currentType instanceof EauType && right instanceof SandType && top instanceof SandType
                            && bottom instanceof EauType && left instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT_DROITE));
                    } else if (currentType instanceof EauType && right instanceof SandType && top instanceof EauType
                            && bottom instanceof EauType && left instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.DROIT));
                    } else if (currentType instanceof EauType && left instanceof SandType && top instanceof EauType
                            && bottom instanceof EauType && right instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.GAUCHE));
                    } else if (currentType instanceof EauType && left instanceof SandType && top instanceof EauType
                            && bottom instanceof EauType && right instanceof SandType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_MILIEU));
                    } else if (currentType instanceof EauType && bottom instanceof SandType && top instanceof EauType
                            && left instanceof EauType && right instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.BAS));
                    } else if (currentType instanceof EauType && top instanceof SandType && bottom instanceof EauType
                            && left instanceof EauType && right instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT));
                    } else if (currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType
                            && left instanceof EauType && right instanceof EauType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_MILIEU));
                    } else if (currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType
                            && left instanceof EauType && right instanceof SandType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_DROITE));
                    } else if (currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType
                            && right instanceof EauType && left instanceof SandType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_GAUCHE));
                    } else if (currentType instanceof EauType && left instanceof SandType && right instanceof SandType
                            && bottom instanceof EauType && top instanceof SandType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_HAUT));
                    } else if (currentType instanceof EauType && left instanceof SandType && right instanceof SandType
                            && top instanceof EauType && bottom instanceof SandType) {
                        currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_BAS));
                    }
                }
            }
        }

        // On s'occupe des bordures strictes (première colonne)
        for (int row = 1; row < rows - 1; row ++){
            Case currentCase = grid[row][0];
            BaseType currentType = currentCase.getBaseType();
            // Vérifiez uniquement si la case actuelle est de type "sable" ou "eau"
            if (currentType instanceof SandType || currentType instanceof EauType) {
                BaseType top = grid[row - 1][0].getBaseType();
                BaseType bottom = grid[row + 1][0].getBaseType();
                BaseType right = grid[row][1].getBaseType();

                if(currentType instanceof EauType && top instanceof EauType && bottom instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS_DROITE));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof EauType && right instanceof SandType){
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT_DROITE));
                }else if(currentType instanceof EauType && top instanceof EauType && bottom instanceof SandType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS));
                }else if(currentType instanceof EauType && top instanceof EauType && bottom instanceof EauType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.DROIT));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof EauType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_MILIEU));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_DROITE));
                }

                // Conditions pour "Herbe" et "Sable"
                else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof SandType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT_DROITE));
                } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof HerbeType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS_DROITE));
                } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof HerbeType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.DROIT));
                } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof HerbeType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS));
                } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT));
                } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof HerbeType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HORIZONTAL_DROITE));
                }
            }
        }

        // On s'occupe des bordures strictes (première ligne)
        for (int col = 1; col < cols - 1; col++) {
            Case currentCase = grid[0][col];
            BaseType currentType = currentCase.getBaseType();

            // Vérifiez uniquement si la case actuelle est de type "sable" ou "eau" ou "herbe"
            if (currentType instanceof SandType || currentType instanceof EauType || currentType instanceof HerbeType) {
                BaseType left = grid[0][col - 1].getBaseType();
                BaseType right = grid[0][col + 1].getBaseType();
                BaseType bottom = grid[1][col].getBaseType();

                // Conditions pour "Eau"
                if (currentType instanceof EauType && left instanceof EauType && bottom instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS_DROITE));
                } else if (currentType instanceof EauType && left instanceof SandType && bottom instanceof EauType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_MILIEU));
                } else if (currentType instanceof EauType && left instanceof EauType && bottom instanceof SandType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS));
                } else if (currentType instanceof EauType && left instanceof EauType && bottom instanceof EauType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.DROIT));
                } else if (currentType instanceof EauType && left instanceof SandType && bottom instanceof EauType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.GAUCHE));
                } else if (currentType instanceof EauType && left instanceof SandType && bottom instanceof SandType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS_GAUCHE));
                } else if (currentType instanceof EauType && left instanceof SandType && bottom instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_BAS));
                }

                // Conditions pour "Herbe" et "Sable"
                else if (currentType instanceof SandType && left instanceof HerbeType && bottom instanceof SandType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_MILIEU));
                } else if (currentType instanceof SandType && left instanceof SandType && bottom instanceof HerbeType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS_DROITE));
                } else if (currentType instanceof SandType && left instanceof HerbeType && bottom instanceof HerbeType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS_GAUCHE));
                } else if (currentType instanceof SandType && left instanceof SandType && bottom instanceof HerbeType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS));
                } else if (currentType instanceof SandType && left instanceof HerbeType && bottom instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.GAUCHE));
                } else if (currentType instanceof SandType && left instanceof SandType && bottom instanceof SandType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.DROIT));
                } else if (currentType instanceof SandType && left instanceof HerbeType && bottom instanceof HerbeType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_BAS));
                }
            }
        }

        // On s'occupe des bordures strictes (dernière colonne)
        for (int row = 1; row < rows - 1; row ++){
            Case currentCase = grid[row][cols-1];
            BaseType currentType = currentCase.getBaseType();
            // Vérifiez uniquement si la case actuelle est de type "sable" ou "eau"
            if (currentType instanceof SandType || currentType instanceof EauType) {
                BaseType top = grid[row - 1][cols - 1].getBaseType();
                BaseType bottom = grid[row + 1][cols - 1].getBaseType();
                BaseType left = grid[row][cols - 2].getBaseType();

                if(currentType instanceof EauType && top instanceof EauType && bottom instanceof SandType && left instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS_GAUCHE));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof EauType && left instanceof SandType){
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT_GAUCHE));
                }else if(currentType instanceof EauType && top instanceof EauType && bottom instanceof SandType && left instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.BAS));
                }else if(currentType instanceof EauType && top instanceof EauType && bottom instanceof EauType && left instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.GAUCHE));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof EauType && left instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType && left instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_MILIEU));
                }else if(currentType instanceof EauType && top instanceof SandType && bottom instanceof SandType && left instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HORIZONTAL_GAUCHE));
                }

                // Conditions pour "Herbe" et "Sable"
                else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof SandType && left instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT_GAUCHE));
                } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof HerbeType && left instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS_GAUCHE));
                } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof HerbeType && left instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.GAUCHE));
                } else if (currentType instanceof SandType && top instanceof SandType && bottom instanceof HerbeType && left instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.BAS));
                } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof SandType && left instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT));
                } else if (currentType instanceof SandType && top instanceof HerbeType && bottom instanceof HerbeType && left instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HORIZONTAL_GAUCHE));
                }
            }
        }

        // On s'occupe des bordures strictes (dernière ligne)
        for (int col = 1; col < cols - 1; col++) {
            Case currentCase = grid[rows - 1][col];
            BaseType currentType = currentCase.getBaseType();

            // Vérifiez uniquement si la case actuelle est de type "sable" ou "eau" ou "herbe"
            if (currentType instanceof SandType || currentType instanceof EauType || currentType instanceof HerbeType) {
                BaseType left = grid[rows - 1][col - 1].getBaseType();
                BaseType right = grid[rows - 1][col + 1].getBaseType();
                BaseType top = grid[rows - 2][col].getBaseType();

                // Conditions pour "Eau"
                if (currentType instanceof EauType && left instanceof EauType && top instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT_DROITE));
                } else if (currentType instanceof EauType && left instanceof SandType && top instanceof EauType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_MILIEU));
                } else if (currentType instanceof EauType && left instanceof EauType && top instanceof SandType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT));
                } else if (currentType instanceof EauType && left instanceof EauType && top instanceof EauType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.DROIT));
                } else if (currentType instanceof EauType && left instanceof SandType && top instanceof EauType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.GAUCHE));
                } else if (currentType instanceof EauType && left instanceof SandType && top instanceof SandType && right instanceof EauType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.HAUT_GAUCHE));
                } else if (currentType instanceof EauType && left instanceof SandType && top instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new EauType(EauType.VariantWater.VERTICAL_HAUT));
                }

                // Conditions pour "Herbe" et "Sable"
                else if (currentType instanceof SandType && left instanceof HerbeType && top instanceof SandType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_MILIEU));
                } else if (currentType instanceof SandType && left instanceof SandType && top instanceof HerbeType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT_DROITE));
                } else if (currentType instanceof SandType && left instanceof HerbeType && top instanceof HerbeType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT_GAUCHE));
                } else if (currentType instanceof SandType && left instanceof SandType && top instanceof HerbeType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.HAUT));
                } else if (currentType instanceof SandType && left instanceof HerbeType && top instanceof SandType && right instanceof SandType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.GAUCHE));
                } else if (currentType instanceof SandType && left instanceof SandType && top instanceof SandType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.DROIT));
                } else if (currentType instanceof SandType && left instanceof HerbeType && top instanceof HerbeType && right instanceof HerbeType) {
                    currentCase.setBaseType(new SandType(SandType.VariantHerbeJaune.VERTICAL_HAUT));
                }
            }
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

