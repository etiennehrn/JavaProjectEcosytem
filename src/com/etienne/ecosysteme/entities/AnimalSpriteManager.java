package com.etienne.ecosysteme.entities;

import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnimalSpriteManager {
    // Le cache de tous les sprites
    private final Map<Animaux.Type, Map<Animaux.Direction, Image[]>> spriteCache = new HashMap<>();
    private static final AnimalSpriteManager INSTANCE = new AnimalSpriteManager();

    // Constructeur privé pour ne pas avoir plusieurs instances
    private AnimalSpriteManager() {
        loadAllSprites();
    }

    // Getter de l'instance
    public static AnimalSpriteManager getInstance() {
        return INSTANCE;
    }

    // Charge tous les sprites au démarrage
    private void loadAllSprites() {
        for (Animaux.Type type : Animaux.Type.values()) {
            Map<Animaux.Direction, Image[]> sprites = new EnumMap<>(Animaux.Direction.class);

            for (Animaux.Direction direction : Animaux.Direction.values()) {
                Image[] directionSprites = new Image[3];
                for (int i = 0; i < 3; i++) {
                    String path = String.format("/ressources/sprites/animals/%s/%s_%s_%d.png", type.name().toLowerCase(), type.name().toLowerCase(), direction.name().toLowerCase(), i);
                    System.out.println(path);
                    directionSprites[i] = new Image(Objects.requireNonNull(AnimalSpriteManager.class.getResourceAsStream(path)));
                }
                sprites.put(direction, directionSprites);
            }
            spriteCache.put(type, sprites);
        }
    }

    // Récupérer les sprites pour un type d'animal et une direction
    public Image[] getSprites(Animaux.Type type, Animaux.Direction direction) {
        return spriteCache.get(type).get(direction);
    }
}
