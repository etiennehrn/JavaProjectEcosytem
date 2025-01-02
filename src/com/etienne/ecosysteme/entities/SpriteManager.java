package com.etienne.ecosysteme.entities;

import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpriteManager implements ISpriteManager{
    // Nombre de styles (pour zombies et humains), attention, il faut que ça corresponde avec ceux définit dans Humain et Zombie et Player
    private static final int NUM_HUMAN_STYLES = 4;
    private static final int NUM_ZOMBIE_STYLES = 1;
    private static final int NUM_PLAYER_STYLES = 1;

    // Le cache de tous les sprites
    private final Map<String, Map<Animaux.Direction, Image[]>> spriteCache = new HashMap<>();
    private static final SpriteManager INSTANCE = new SpriteManager();

    // Constructeur privé pour ne pas avoir plusieurs instances
    private SpriteManager() {
        loadAllSprites();
    }

    // Getter de l'instance
    public static SpriteManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void loadAllSprites() {
        // Charger les animaux
        for (Animaux.Type type : Animaux.Type.values()) {
            String category = "animals";
            loadSpritesForCategory(category, type.name().toLowerCase());
        }

        // Charger les humains, zombies et le joueur
        Map<String, Integer> entityConfigs = Map.of(
                "human", NUM_HUMAN_STYLES,
                "zombie", NUM_ZOMBIE_STYLES,
                "player", NUM_PLAYER_STYLES
        );

        for (Map.Entry<String, Integer> entry : entityConfigs.entrySet()) {
            String category = entry.getKey();
            int numStyles = entry.getValue();
            for (int style = 0; style < numStyles; style++) {
                loadSpritesForCategory(category, category + style);
            }
        }
    }

    // Méthode utilitaire pour charger les sprites
    private void loadSpritesForCategory(String category, String cacheKey) {
        Map<Animaux.Direction, Image[]> sprites = new EnumMap<>(Animaux.Direction.class);

        for (Animaux.Direction direction : Animaux.Direction.values()) {
            Image[] directionSprites = new Image[3]; // Supposons 3 frames par direction
            for (int frame = 0; frame < 3; frame++) {
                String path;
                if (category.equals("animals")) {
                    // Chemin pour les animaux
                    path = String.format("/ressources/sprites/%s/%s/%s_%s_%d.png",
                            category,
                            cacheKey,
                            cacheKey,
                            direction.name().toLowerCase(),
                            frame);
                    System.out.println(path);
                } else {
                    // Chemin pour les humains, zombies et le joueur
                    path = String.format("/ressources/sprites/%s/%s/%s_%s_%d.png",
                            category,
                            cacheKey,
                            cacheKey,
                            direction.name().toLowerCase(),
                            frame);
                    System.out.println(path);

                }

                try {
                    directionSprites[frame] = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement du fichier : " + path);
                    throw e;
                }
            }
            sprites.put(direction, directionSprites);
        }
        spriteCache.put(cacheKey, sprites);
    }

    // Récupérer les sprites pour un type d'animal et une direction
    @Override
    public Image[] getSprites(String type, Animaux.Direction direction) {
        return spriteCache.get(type).get(direction);
    }
}
