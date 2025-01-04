package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.io.*;
import java.util.Random;

/**
 * Classe utilitaire pour gérer la population d'entités sur une carte.
 * Permet de charger des entités depuis un fichier ou de les placer aléatoirement.
 */
public class PopulateUtil {

    /**
     * Charge une population d'êtres vivants depuis un fichier texte.
     *
     * @param filePath   Le chemin du fichier contenant la population.
     * @param map        La carte de l'environnement.
     * @param mapVivant  La carte des entités vivantes.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    public static void loadFromFile(String filePath, MapEnvironnement map, MapVivant mapVivant) throws IOException {
        System.out.println("Loading Map from file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                String[] codes = line.split(" ");

                // Vérifie si le nombre de colonnes correspond à celui de la carte
                if (codes.length != mapVivant.getCols()) {
                    throw new IllegalArgumentException("Ligne invalide : le nombre de colonnes ne correspond pas.");
                }

                // Parcourt chaque cellule de la ligne et place les entités correspondantes
                for (int col = 0; col < mapVivant.getCols(); col++) {
                    String code = codes[col];
                    if (!code.equals(".")) { // Les cellules avec "." sont ignorées
                        placeEntityFromCode(code, row, col, map, mapVivant);
                    }
                }
                row++;
            }

            // Vérifie si le nombre de lignes correspond à celui de la carte
            if (row != mapVivant.getRows()) {
                throw new IllegalArgumentException("Nombre de lignes invalide : ne correspond pas à la taille de la carte.");
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    /**
     * Place une entité vivante sur la carte à partir d'un code.
     *
     * @param code        Le code représentant l'entité.
     * @param row         La ligne où placer l'entité.
     * @param col         La colonne où placer l'entité.
     * @param map         La carte de l'environnement.
     * @param mapVivant   La carte des entités vivantes.
     */
    private static void placeEntityFromCode(String code, int row, int col, MapEnvironnement map, MapVivant mapVivant) {
        if (!mapVivant.isWithinBounds(row, col) || map.getCell(row, col).isObstacle()) {
            throw new IllegalArgumentException("Impossible de placer une entité sur une case hors limite ou un obstacle.");
        }

        // Place l'entité en fonction du code fourni
        switch (code) {
            case "A" -> mapVivant.setEtreVivant(row, col, new Humain(row, col));
            case "B" -> mapVivant.setEtreVivant(row, col, new Zombie(row, col));
            case "C" -> mapVivant.setEtreVivant(row, col, new Deer(row, col));
            case "D" -> mapVivant.setEtreVivant(row, col, new Bear(row, col));
            case "E" -> mapVivant.setEtreVivant(row, col, new Wolf(row, col));
            case "F" -> mapVivant.setEtreVivant(row, col, new Bunny(row, col));
            default -> throw new IllegalArgumentException("Code inconnu : " + code);
        }
    }

    /**
     * Remplit une carte avec une population générée aléatoirement.
     *
     * @param mapVivant   La carte des entités vivantes.
     * @param map         La carte de l'environnement.
     * @param nbHumains   Nombre d'humains à placer.
     * @param nbZombies   Nombre de zombies à placer.
     * @param nbAnimaux   Nombre d'animaux à placer.
     */
    public static void populateRandomly(MapVivant mapVivant, MapEnvironnement map, int nbHumains, int nbZombies, int nbAnimaux) {
        Random random = new Random();

        // Place les humains
        placeEntities(mapVivant, map, "Humain", nbHumains, random);

        // Place les zombies
        placeEntities(mapVivant, map, "Zombie", nbZombies, random);

        // Place les animaux
        placeEntities(mapVivant, map, "Animal", nbAnimaux, random);
    }

    /**
     * Place un ensemble d'entités sur la carte en fonction de leur type et quantité.
     *
     * @param mapVivant   La carte des entités vivantes.
     * @param map         La carte de l'environnement.
     * @param type        Le type d'entité ("Humain", "Zombie", "Animal").
     * @param quantity    Le nombre d'entités à placer.
     * @param random      Générateur de nombres aléatoires.
     */
    private static void placeEntities(MapVivant mapVivant, MapEnvironnement map, String type, int quantity, Random random) {
        for (int i = 0; i < quantity; i++) {
            boolean placed = false;
            int attempts = 0;

            while (!placed && attempts < 10) { // Limite à 10 essais pour éviter les boucles infinies
                int row = random.nextInt(mapVivant.getRows());
                int col = random.nextInt(mapVivant.getCols());

                if (!map.getCell(row, col).isObstacle() && mapVivant.getEtreVivant(row, col) == null) {
                    switch (type) {
                        case "Humain" -> mapVivant.setEtreVivant(row, col, new Humain(row, col));
                        case "Zombie" -> mapVivant.setEtreVivant(row, col, new Zombie(row, col));
                        case "Animal" -> placeRandomAnimal(row, col, mapVivant, random);
                        default -> throw new IllegalArgumentException("Type inconnu : " + type);
                    }
                    placed = true;
                }
                attempts++;
            }
        }
    }

    /**
     * Place un animal aléatoire sur la carte.
     *
     * @param row         La ligne où placer l'animal.
     * @param col         La colonne où placer l'animal.
     * @param mapVivant   La carte des entités vivantes.
     * @param random      Générateur de nombres aléatoires.
     */
    private static void placeRandomAnimal(int row, int col, MapVivant mapVivant, Random random) {
        Animaux.Type[] types = Animaux.Type.values();
        Animaux.Type randomType = types[random.nextInt(types.length)];

        // Place un animal en fonction du type généré aléatoirement
        switch (randomType) {
            case DEER -> mapVivant.setEtreVivant(row, col, new Deer(row, col));
            case BEAR -> mapVivant.setEtreVivant(row, col, new Bear(row, col));
            case BUNNY -> mapVivant.setEtreVivant(row, col, new Bunny(row, col));
            case WOLF -> mapVivant.setEtreVivant(row, col, new Wolf(row, col));
        }
    }
}
