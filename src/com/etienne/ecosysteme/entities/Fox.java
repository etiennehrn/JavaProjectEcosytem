package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Classe représentant un renard (Fox) dans l'écosystème.
 * Cette classe hérite de la classe Animaux et définit le comportement spécifique
 * et les attributs associés à un renard.
 */
public class Fox extends Animaux {

    // Image statique utilisée pour représenter visuellement le renard.
    private static final Image FOX_IMAGE = new Image(
            Objects.requireNonNull(
                    Fox.class.getResourceAsStream("/ressources/sprites/animals/fox.png")
            )
    );

    /**
     * Constructeur de la classe Fox.
     *
     * @param row La ligne initiale où le renard est placé sur la carte.
     * @param col La colonne initiale où le renard est placé sur la carte.
     */
    public Fox(int row, int col) {
        // Appelle le constructeur parent avec les valeurs spécifiques au renard.
        // Paramètres : position (row, col), points de vie (10), vitesse (1),
        // vision (5), et le type d'animal (FOX).
        super(row, col, 10, 1, 5, Type.FOX);
    }

    /**
     * Retourne une représentation visuelle (sprite) du renard.
     *
     * @param tileSize La taille en pixels d'une case sur la carte.
     * @return Un objet ImageView représentant le sprite du renard.
     */
    @Override
    public ImageView getSprite(int tileSize) {
        // Crée une ImageView pour afficher le sprite du renard.
        ImageView imageView = new ImageView(FOX_IMAGE);
        imageView.setFitWidth(tileSize);  // Définit la largeur de l'image.
        imageView.setFitHeight(tileSize); // Définit la hauteur de l'image.
        return imageView;
    }

    /**
     * Génère les déplacements du renard.
     * Pour l'instant, cette méthode n'implémente aucun comportement spécifique.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La carte de l'environnement.
     * @param row          La ligne actuelle du renard.
     * @param col          La colonne actuelle du renard.
     */
    @Override
    public void gen_deplacement(MapVivant mapVivants, MapEnvironnement grid, int row, int col) {
        // Actuellement, les renards ne se déplacent pas.
    }
}
