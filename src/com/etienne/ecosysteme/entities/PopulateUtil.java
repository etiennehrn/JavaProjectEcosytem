package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.io.*;
import java.util.Random;


// Classe pour permettre de créer la population sur la map
public class PopulateUtil {

    // Lit une carte des vivants dans un fichier
    public static void loadFromFile(String filePath, MapEnvironnement map, MapVivant mapVivant) throws IOException {
        System.out.println("Loading Map from file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] codes = line.split(" ");

                // Vérification des dimensions
                if (codes.length != mapVivant.getCols()) {
                    throw new IllegalArgumentException("Ligne invalide : le nombre de colonnes ne correspond pas.");
                }

                for (int col = 0; col < mapVivant.getCols(); col++) {
                    String code = codes[col];
                    if (!code.equals(".")) {
                        placeEntityFromCode(code, row, col, map, mapVivant);
                    }
                }
                row++;
            }
            // Vérifier si le fichier contient le bon nombre de lignes
            if (row != mapVivant.getRows()) {
                throw new IllegalArgumentException("Nombre de lignes invalide : ne correspond pas à la taille de la carte.");
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    // Place un etreVivant sur la carte
    private static void placeEntityFromCode(String code, int row, int col, MapEnvironnement map, MapVivant mapVivant)  {
        if (!mapVivant.isWithinBounds(row, col) || map.getCell(row, col).isObstacle()) {
            throw new IllegalArgumentException("Impossible de placer une entité sur une case hors limite ou un obstacle.");
        }

        // Ici le code pour les êtres vivants
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

    // Pour placer une population aléatoirement
    public static void populateRandomly(MapVivant mapVivant, MapEnvironnement map, int nbHumains, int nbZombies, int nbAnimaux) {
        Random random = new Random();

        // Placement des humains
        placeEntities(mapVivant, map, "Humain", nbHumains, random);

        // Placement des zombies
        placeEntities(mapVivant, map, "Zombie", nbZombies, random);

        // Placement des animaux
        placeEntities(mapVivant, map, "Animal", nbAnimaux, random);
    }

    // Pour placer un ensemble d'être vivants par quantité
    private static void placeEntities(MapVivant mapVivant, MapEnvironnement map, String type, int quantity, Random random) {
        for (int i = 0; i < quantity; i++) {
            boolean placed = false;
            int attempts = 0;

            while (!placed && attempts < 10) {
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

    // Place un animal aléatoirement
    private static void placeRandomAnimal(int row, int col, MapVivant mapVivant, Random random) {
        Animaux.Type[] types = Animaux.Type.values();
        Animaux.Type randomType = types[random.nextInt(types.length)];

        switch (randomType) {
            case DEER -> mapVivant.setEtreVivant(row, col, new Deer(row, col));
            case BEAR -> mapVivant.setEtreVivant(row, col, new Bear(row, col));
            case BUNNY -> mapVivant.setEtreVivant(row, col, new Bunny(row, col));
            case WOLF -> mapVivant.setEtreVivant(row, col, new Wolf(row, col));
        }
    }
}
