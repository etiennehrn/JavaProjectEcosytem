package com.etienne.ecosysteme.core;

// Classe qui gère les cycles jour/nuit

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DayNightCycleImpl implements DayNightCycleInterface {

    // Enumération pour les différents cycles possibles
    public enum Cycle {
        JOUR, CREPUSCULE, NUIT, AURORE
    }

    // Durée totale d'un cycle (en secondes)
    private final int totalCycleDuration;

    // Compteur pour suivre l'avancement du temps dans le cycle
    private int timeCounter;

    // Objet Timeline pour gérer les mises à jour périodiques
    private Timeline cycleTimeline;

    /**
     * Constructeur de la classe.
     * Initialise la durée du cycle et démarre automatiquement le cycle.
     * @param totalCycleDuration Durée totale du cycle en secondes.
     */
    public DayNightCycleImpl(int totalCycleDuration) {
        this.totalCycleDuration = totalCycleDuration;
        this.timeCounter = 0;
        startCycle();
    }

    /**
     * Démarre le cycle jour/nuit en initialisant un compteur périodique.
     * Le compteur est incrémenté toutes les secondes.
     */
    @Override
    public void startCycle() {
        this.timeCounter = (int) (0.5 * totalCycleDuration); // Début au milieu d'un cycle
        cycleTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), _ -> timeCounter = (timeCounter + 1) % totalCycleDuration)
        );
        cycleTimeline.setCycleCount(Timeline.INDEFINITE);
        cycleTimeline.play();
    }

    /**
     * Retourne le temps actuel sous forme normalisée (entre 0 et 1).
     * @return Temps normalisé dans le cycle.
     */
    @Override
    public double getNormalizedTime() {
        return (double) timeCounter / totalCycleDuration;
    }

    /**
     * Détermine le cycle actuel (jour, nuit, crépuscule, etc.).
     * @return Le cycle correspondant à l'heure actuelle.
     */
    @Override
    public Cycle getCurrentCycle() {
        double normalizedTime = getNormalizedTime();

        if (0.25 <= normalizedTime && normalizedTime <= 0.45) { // 6h à 11h
            return Cycle.AURORE;
        }
        if (0.4 <= normalizedTime && normalizedTime <= 0.75) { // 11h à 18h
            return Cycle.JOUR;
        }
        if (0.75 <= normalizedTime && normalizedTime <= 0.9) { // 18h à 00h
            return Cycle.CREPUSCULE;
        }
        return Cycle.NUIT; // 00h à 6h
    }

    /**
     * Retourne la couleur d'éclairage en fonction de l'heure actuelle.
     * Les transitions de couleur sont interpolées pour une transition fluide.
     * @return Couleur d'éclairage.
     */
    @Override
    public Color getLightingColor() {
        double normalizedTime = getNormalizedTime();

        // Transition de l'aube (6h à 11h)
        if (0.25 <= normalizedTime && normalizedTime <= 0.45) {
            double progress = (normalizedTime - 0.25) / (0.45 - 0.25);
            return interpolateColor(Color.rgb(0, 0, 50, 0.6), Color.TRANSPARENT, progress);
        }

        // Plein jour (11h à 18h)
        if (0.4 <= normalizedTime && normalizedTime <= 0.75) {
            return Color.TRANSPARENT;
        }

        // Transition au crépuscule (18h à minuit)
        if (0.75 <= normalizedTime && normalizedTime <= 0.9) {
            if (0.75 <= normalizedTime && normalizedTime <= 0.85) {
                double progress = (normalizedTime - 0.75) / (0.85 - 0.75);
                return interpolateColor(Color.TRANSPARENT, Color.rgb(220, 130, 50, 0.2), progress);
            }
            if (0.85 <= normalizedTime && normalizedTime <= 0.9) {
                double progress = (normalizedTime - 0.85) / (0.9 - 0.85);
                return interpolateColor(Color.rgb(220, 130, 50, 0.2), Color.rgb(72, 41, 50, 0.4), progress);
            }
        }

        // Transition vers la nuit (minuit)
        if (0.9 <= normalizedTime && normalizedTime <= 1.0) {
            double progress = (normalizedTime - 0.9) / (1 - 0.9);
            return interpolateColor(Color.rgb(72, 41, 50, 0.4), Color.rgb(0, 0, 50, 0.6), progress);
        }

        // Pleine nuit (minuit à 6h)
        if (0 <= normalizedTime && normalizedTime <= 0.25) {
            return Color.rgb(0, 0, 50, 0.6);
        }

        return Color.TRANSPARENT; // Couleur par défaut
    }

    /**
     * Interpole deux couleurs en fonction d'un progrès donné.
     * @param start Couleur de départ.
     * @param end Couleur d'arrivée.
     * @param progress Progrès (entre 0 et 1).
     * @return Couleur interpolée.
     */
    private Color interpolateColor(Color start, Color end, double progress) {
        progress = Math.max(0, Math.min(1, progress)); // Limite progress entre 0 et 1
        return new Color(
                start.getRed() + (end.getRed() - start.getRed()) * progress,
                start.getGreen() + (end.getGreen() - start.getGreen()) * progress,
                start.getBlue() + (end.getBlue() - start.getBlue()) * progress,
                start.getOpacity() + (end.getOpacity() - start.getOpacity()) * progress
        );
    }

    /**
     * Retourne l'heure actuelle sous format "HH:mm".
     * @return Heure formatée.
     */
    @Override
    public String getFormattedTime() {
        int totalMin = (int) (getNormalizedTime() * 24 * 60);
        int adjustedMinutes = totalMin % (24 * 60); // Modulo pour éviter les dépassements
        int hours = adjustedMinutes / 60;
        int minutes = adjustedMinutes % 60;

        return String.format("%02d:%02d", hours, minutes);
    }
}
