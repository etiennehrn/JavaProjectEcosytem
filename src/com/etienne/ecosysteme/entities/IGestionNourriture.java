package com.etienne.ecosysteme.entities;

public interface IGestionNourriture {
    /**
     * Consomme une certaine quantité de nourriture.
     *
     * @param quantite   La quantité de nourriture consommée.
     * @param mapVivants Map des vivants
     */
    void consommerNourriture(int quantite, MapVivant mapVivants);

    /**
     * Augmente la nourriture après avoir mangé.
     *
     * @param gain La quantité de nourriture gagnée.
     */
    void manger(int gain);

    /**
     * Vérifie et applique les effets de la quantité actuelle de nourriture.
     */
    void verifierEtatNourriture(MapVivant mapVivants);
}
