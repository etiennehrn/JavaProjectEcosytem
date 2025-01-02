package com.etienne.ecosysteme.core;

import javafx.scene.paint.Color;

/**
 * Interface représentant un cycle jour/nuit dans un écosystème.
 * Cette interface définit les méthodes nécessaires pour gérer les transitions entre les cycles
 * et fournir des informations liées à l'heure et aux couleurs d'éclairage.
 */
public interface DayNightCycleInterface {

    /**
     * Démarre le cycle jour/nuit.
     * Cette méthode initialise et lance le cycle, permettant à l'horloge interne de commencer à évoluer.
     */
    void startCycle();

    /**
     * Récupère le temps actuel sous forme normalisée.
     * Le temps est exprimé entre 0 et 1, où 0 correspond à minuit et 1 correspond à minuit du jour suivant.
     *
     * @return une valeur double représentant le temps normalisé.
     */
    double getNormalizedTime();

    /**
     * Obtient le cycle actuel (jour, crépuscule, nuit ou aurore).
     * Basé sur le temps normalisé, cette méthode détermine le cycle en cours.
     *
     * @return une valeur de type {@link DayNightCycleImpl.Cycle}.
     */
    DayNightCycleImpl.Cycle getCurrentCycle();

    /**
     * Fournit la couleur d'éclairage correspondant au cycle actuel.
     * Cette couleur peut être utilisée pour appliquer des effets visuels
     * afin de simuler les changements d'éclairage dans l'environnement.
     *
     * @return une instance de {@link Color} représentant la couleur d'éclairage actuelle.
     */
    Color getLightingColor();

    /**
     * Retourne l'heure actuelle sous forme formatée (HH:mm).
     * Cette méthode permet d'afficher l'heure comme une chaîne lisible par l'utilisateur.
     *
     * @return une chaîne de caractères au format HH:mm.
     */
    String getFormattedTime();
}
