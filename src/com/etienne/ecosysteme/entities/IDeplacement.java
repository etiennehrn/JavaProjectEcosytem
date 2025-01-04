package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;

import java.util.function.BiFunction;

/**
 * Interface définissant les méthodes pour les déplacements des entités dans l'écosystème.
 * Permet d'unifier et de structurer les comportements de déplacement dans différents contextes.
 */
public interface IDeplacement {

    /**
     * Génère les déplacements d'une entité.
     *
     * @param mapVivant   La carte des entités vivantes (présence des autres êtres vivants).
     * @param grid        La carte de l'environnement de la simulation.
     * @param row         La ligne actuelle de l'entité.
     * @param col         La colonne actuelle de l'entité.
     */
    void gen_deplacement(MapVivant mapVivant, MapEnvironnement grid, int row, int col);

    /**
     * Déplace une entité vers une position donnée si elle est valide.
     * Si le déplacement est possible, il est effectué, et la méthode retourne `true`.
     * Sinon, aucun déplacement n'est effectué, et la méthode retourne `false`.
     *
     * @param newRow       La nouvelle ligne cible.
     * @param newCol       La nouvelle colonne cible.
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La carte de l'environnement.
     * @return `true` si le déplacement est effectué, `false` sinon.
     */
    boolean deplacerVers(int newRow, int newCol, MapVivant mapVivants, MapEnvironnement grid);

    /**
     * Génère un mouvement aléatoire erratique pour une entité.
     * L'entité choisit une direction de manière non déterministe.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La carte de l'environnement.
     * @param row          La ligne actuelle de l'entité.
     * @param col          La colonne actuelle de l'entité.
     * @return Un tableau contenant les coordonnées [nouvelleLigne, nouvelleColonne].
     */
    int[] mouvementErratique(MapVivant mapVivants, MapEnvironnement grid, int row, int col);

    /**
     * Effectue un déplacement basé sur un score attribué à chaque direction possible.
     * Le score est calculé pour maximiser un objectif spécifique, comme trouver de la nourriture ou éviter des obstacles.
     *
     * @param mapVivants    La carte des entités vivantes.
     * @param grid          La carte de l'environnement.
     * @param calculerScore Une fonction qui calcule un score pour une position donnée (ligne, colonne).
     * @return Un tableau contenant les coordonnées [nouvelleLigne, nouvelleColonne].
     */
    int[] seDeplacerSelonScore(MapVivant mapVivants, MapEnvironnement grid, BiFunction<Integer, Integer, Double> calculerScore);

    /**
     * Génère un mouvement circulaire erratique autour d'un point central.
     * Permet de simuler un comportement de patrouille ou de recherche.
     *
     * @param mapVivants    La carte des entités vivantes.
     * @param grid          La carte de l'environnement.
     * @param centralRow    La ligne centrale autour de laquelle se fait le mouvement.
     * @param centralCol    La colonne centrale autour de laquelle se fait le mouvement.
     * @return Un tableau contenant les coordonnées [nouvelleLigne, nouvelleColonne].
     */
    int[] mouvementCirculaire(MapVivant mapVivants, MapEnvironnement grid, int centralRow, int centralCol);

    /**
     * Effectue un déplacement de recherche active sans vision directe.
     * Utilisé pour simuler un comportement de quête dans une zone inconnue.
     *
     * @param mapVivants   La carte des entités vivantes.
     * @param grid         La carte de l'environnement.
     * @return Un tableau contenant les coordonnées [nouvelleLigne, nouvelleColonne].
     */
    int[] rechercheActive(MapVivant mapVivants, MapEnvironnement grid);
}
