package com.etienne.ecosysteme.entities;

public class CompteurDeplacement {
    // Classe qui gére le compteur des déplacements d'un être vivant
    private int compteur = 0;
    private int vitesse;

    // Constructeur
    public CompteurDeplacement(int vitesse) {
        if (vitesse <= 0) {
            throw new IllegalArgumentException("La vitesse doit être supérieure à zéro.");
        }
        this.vitesse = vitesse;
    }

    // Incrément le compteur
    public void incrementer() {
        this.compteur++;
    }

    // Reset le compteur
    public void reset() {
        this.compteur = 0;
    }

    // Vérifie si l'être vivant doit se déplacer
    public boolean doitSeDeplacer() {
        return this.compteur >= this.vitesse;
    }

    // Getter
    public int getVitesse() {
        return vitesse;
    }

    // Setter
    public void setVitesse(int nouvelleVitesse) {
        if (nouvelleVitesse <= 0) {
            throw new IllegalArgumentException("La vitesse doit être supérieure à zéro.");
        }
        this.vitesse = nouvelleVitesse;
    }
}
