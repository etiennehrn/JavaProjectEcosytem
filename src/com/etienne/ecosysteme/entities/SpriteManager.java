package com.etienne.ecosysteme.entities;

import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Gestionnaire de sprites pour les entités de l'écosystème.
 * Utilise un cache pour éviter de recharger les sprites plusieurs fois.
 * Implémente le design pattern Singleton pour garantir une seule instance.
 */
public class SpriteManager implements ISpriteManager {

    // Nombre de styles pour les humains, zombies et le joueur.
    // Ces valeurs doivent correspondre à celles définies dans les classes Humain, Zombie et Player.
    private static final int NUM_HUMAN_STYLES = 4;
    private static final int NUM_ZOMBIE_STYLES = 1;
    private static final int NUM_PLAYER_STYLES = 1;

    // Cache contenant tous les sprites chargés.
    private final Map<String, Map<Animaux.Direction, Image[]>> spriteCache = new HashMap<>();

    // Instance unique du gestionnaire (Singleton).
    private static final SpriteManager INSTANCE = new SpriteManager();

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     * Charge tous les sprites au démarrage.
     */
    private SpriteManager() {
        loadAllSprites();
    }

    /**
     * Retourne l'instance unique du SpriteManager.
     *
     * @return L'instance unique du SpriteManager.
     */
    public static SpriteManager getInstance() {
        return INSTANCE;
    }

    /**
     * Charge tous les sprites nécessaires dans le cache.
     * Inclut les sprites pour les animaux, les humains, les zombies et le joueur.
     */
    @Override
    public void loadAllSprites() {
        // Charger les sprites des animaux.
        for (Animaux.Type type : Animaux.Type.values()) {
            String category = "animals";
            loadSpritesForCategory(category, type.name().toLowerCase());
        }

        // Charger les sprites des humains, zombies et du joueur.
        Map<String, Integer> entityConfigs = Map.of(
                "human", NUM_HUMAN_STYLES,
                "zombie", NUM_ZOMBIE_STYLES,
                "player", NUM_PLAYER_STYLES
        );

        for (Map.Entry<String, Integer> entry : entityConfigs.entrySet()) {
            String category = entry.getKey();
            int numStyles = entry.getValue();

            // Charger chaque style pour l'entité
            for (int style = 0; style < numStyles; style++) {
                loadSpritesForCategory(category, category + style);
            }
        }
    }

    /**
     * Charge les sprites pour une catégorie et les stocke dans le cache.
     *
     * @param category  La catégorie d'entité (ex. "animals", "human").
     * @param cacheKey  La clé pour accéder aux sprites dans le cache.
     */
    private void loadSpritesForCategory(String category, String cacheKey) {
        Map<Animaux.Direction, Image[]> sprites = new EnumMap<>(Animaux.Direction.class);

        // Parcourt chaque direction pour charger les frames correspondantes
        for (Animaux.Direction direction : Animaux.Direction.values()) {
            Image[] directionSprites = new Image[3]; // Supposons 3 frames par direction.
            for (int frame = 0; frame < 3; frame++) {
                String path;

                // Détermine le chemin en fonction de la catégorie.
                path = String.format("/ressources/sprites/%s/%s/%s_%s_%d.png",
                        category,
                        cacheKey,
                        cacheKey,
                        direction.name().toLowerCase(),
                        frame);
                System.out.println(path); // Debug : affiche le chemin du fichier.

                // Charge l'image à partir du chemin.
                try {
                    directionSprites[frame] = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement du fichier : " + path);
                    throw e; // Relance l'exception après journalisation.
                }
            }
            sprites.put(direction, directionSprites);
        }

        // Stocke les sprites chargés dans le cache.
        spriteCache.put(cacheKey, sprites);
    }

    /**
     * Récupère les sprites pour un type et une direction donnés.
     *
     * @param type      Le type d'entité (ex. "human0", "zombie0").
     * @param direction La direction pour laquelle récupérer les sprites.
     * @return Un tableau d'images correspondant aux frames de la direction donnée.
     */
    @Override
    public Image[] getSprites(String type, Animaux.Direction direction) {
        return spriteCache.get(type).get(direction);
    }
}
