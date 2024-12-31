package com.example.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Classe abstraite pour les élements ^possible sur une type de base
public abstract class Element {
    // Texture de l'élément
    private final Image texture;

    // Constructeur
    public Element(Image texture) {
        this.texture = texture;
    }

    // Getter
    public Image getTexture() {
        return texture;
    }
    public abstract String getCode();

    // Méthode pour savoir si l'élément est un obstacle
    public abstract boolean isObstacle();

    // Méthode pour déterminer si l'élément permet de passer un obstacle
    public abstract boolean canPassObstacle();
}
