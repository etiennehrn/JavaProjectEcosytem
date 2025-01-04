package com.etienne.ecosysteme.entities;

/**
 * Classe qui gère le compteur de déplacements d'un être vivant.
 * Elle permet de contrôler la fréquence des déplacements en fonction de la vitesse définie.
 */
public class CompteurDeplacement {

    // Compteur interne qui suit le nombre d'incréments
    private int compteur = 0;

    // Vitesse définissant la fréquence de déplacement (plus la vitesse est élevée, plus le déplacement est fréquent)
    private int vitesse;

    /**
     * Constructeur de la classe CompteurDeplacement.
     *
     * @param vitesse Vitesse de déplacement. Doit être strictement supérieure à zéro.
     * @throws IllegalArgumentException Si la vitesse est inférieure ou égale à zéro.
     */
    public CompteurDeplacement(int vitesse) {
        if (vitesse <= 0) {
            throw new IllegalArgumentException("La vitesse doit être supérieure à zéro.");
        }
        this.vitesse = vitesse;
    }

    /**
     * Incrémente le compteur interne.
     * Cette méthode est appelée à chaque étape de mise à jour pour suivre l'avancement.
     */
    public void incrementer() {
        this.compteur++;
    }

    /**
     * Réinitialise le compteur à zéro.
     * Cette méthode est appelée une fois que l'entité s'est déplacée.
     */
    public void reset() {
        this.compteur = 0;
    }

    /**
     * Vérifie si l'entité doit se déplacer.
     * L'entité se déplace uniquement si le compteur atteint ou dépasse la vitesse définie.
     *
     * @return true si l'entité doit se déplacer, false sinon.
     */
    public boolean doitSeDeplacer() {
        return this.compteur >= this.vitesse;
    }

    /**
     * Retourne la vitesse actuelle de l'entité.
     *
     * @return Vitesse actuelle.
     */
    public int getVitesse() {
        return vitesse;
    }

    /**
     * Modifie la vitesse de déplacement de l'entité.
     *
     * @param nouvelleVitesse Nouvelle vitesse. Doit être strictement supérieure à zéro.
     * @throws IllegalArgumentException Si la nouvelle vitesse est inférieure ou égale à zéro.
     */
    public void setVitesse(int nouvelleVitesse) {
        if (nouvelleVitesse <= 0) {
            throw new IllegalArgumentException("La vitesse doit être supérieure à zéro.");
        }
        this.vitesse = nouvelleVitesse;
    }
}
