package com.etienne.ecosysteme.environment;

import com.etienne.ecosysteme.entities.MapVivant;

public interface IPrecipitation {
    /**
     * Active les précipitations.
     */
    void demarrer();

    /**
     * Désactive les précipitations.
     */
    void arreter();

    /**
     * Applique les effets des précipitations sur les entités vivantes.
     */
    void appliquerEffets(MapVivant mapVivant);

    /**
     * Stoppe les effets des précipitations sur les entités vivantes.
     */
    void stopperEffets(MapVivant mapVivant);



}
