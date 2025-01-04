package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Classe représentant un cochon (Pig) dans l'écosystème.
 * Cette classe hérite de la classe Animaux et définit le comportement spécifique
 * et les attributs liés à un cochon.
 */
public class Pig extends Animaux {

    // Image statique utilisée pour représenter visuellement le cochon.
    private static final Image PIG_IMAGE = new Image(
            Objects.requireNonNull(
                    Pig.class.getResourceAsStream("/ressources/sprites/animals/boar.png")
            )
    );

    /**
     * Constructeur de la classe Pig.
     *
     * @param row La ligne initiale où le cochon est placé sur la carte.
     * @param col La colonne initiale où le cochon est placé sur la carte.
     */
    public Pig(int row, int col) {
        // Appelle le constructeur parent avec les valeurs spécifiques au cochon.
        // Paramètres : position (row, col), points de vie (10), vitesse (1),
        // vision (5), et le type d'animal (PIG).
        super(row, col, 10, 1, 5, Type.PIG);
    }

    /**
     * Retourne une représentation visuelle (sprite) du cochon.
     *
     * @param tileSize La taille en pixels d'une case sur la carte.
     * @return Un objet ImageView représentant le sprite du cochon.
     */
    @Override
    public ImageView getSprite(int tileSize) {
        // Crée une ImageView pour afficher le sprite du cochon.
        ImageView imageView = new ImageView(PIG_IMAGE);
        imageView.setFitWidth(tileSize);  // Définit la largeur de l'image.
        imageView.setFitHeight(tileSize); // Définit la hauteur de l'image.
        return imageView;
    }

    /**
     * Génère les déplacements du cochon.
     * Pour l'instant, cette méthode n'implémente aucun comportement spécifique.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La carte de l'environnement.
     * @param row          La ligne actuelle de l'entité.
     * @param col          La colonne actuelle de l'entité.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Actuellement, les cochons ne se déplacent pas.
    }
}
