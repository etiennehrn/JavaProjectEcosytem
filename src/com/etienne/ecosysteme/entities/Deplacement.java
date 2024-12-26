package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.util.function.BiFunction;

public interface Deplacement {
    // Méthode générique qui générer les déplacements
    void gen_deplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col);

    // Méthode générique qui fait le déplacement et renvoie true s'il est valide (sinon ne fais rien et renvoie false)
    boolean deplacerVers(int newRow, int newCol, MapVivant mapVivants, MapEnvironnement grid);

    // Méthode qui génére un mouvement érratique
    int[] mouvementErratique(MapVivant mapVivants, MapEnvironnement grid, int row, int col);

    /**
     * Calcule et effectue un déplacement basé sur un score attribué à chaque direction possible.
     * ATTENTION ON MAXIMISE LE SCORE
     *
     * @param mapVivants    la carte des êtres vivants
     * @param grid          l'environnement de la simulation
     * @param calculerScore une fonction qui calcule un score pour une position donnée
     */
    int[] seDeplacerSelonScore(MapVivant mapVivants, MapEnvironnement grid, BiFunction<Integer, Integer, Double> calculerScore);

    // Pour le mouvement circulaire érratique
    int[] mouvementCirculaire(MapVivant mapVivants, MapEnvironnement grid, int centralRow, int centralCol);

    // Déplacement de recherche active sans vision
    int[] rechercheActive(MapVivant mapVivants, MapEnvironnement grid);
}
