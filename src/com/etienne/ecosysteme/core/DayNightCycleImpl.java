package com.etienne.ecosysteme.core;// Classe qui gère les cycles jour/nuit

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class DayNightCycleImpl implements DayNightCycleInterface{
    // Enumération pour les différents cycles possibles
    public enum Cycle {
        JOUR, CREPUSCULE, NUIT, AURORE
    }

    private final int totalCycleDuration; // Temps total un cycle
    private int timeCounter;              // Compteur pour le temps
    private Timeline cycleTimeline;       // Variable pour le temps

    // Constructeur qui lance les cycles
    public DayNightCycleImpl(int totalCycleDuration) {
        this.totalCycleDuration = totalCycleDuration;
        this.timeCounter = 0;
        startCycle();
    }

    // Le démarrage
    @Override
    public void startCycle() {
        this.timeCounter = (int) (0.5 * totalCycleDuration);
        cycleTimeline = new Timeline(new KeyFrame(Duration.seconds(1), _ -> timeCounter = (timeCounter + 1) % totalCycleDuration));
        cycleTimeline.setCycleCount(Timeline.INDEFINITE);
        cycleTimeline.play();
    }

    // On récupe l'heure
    @Override
    public double getNormalizedTime() {
        return (double) timeCounter / totalCycleDuration;
    }

    // On récupère le cycle
    @Override
    public Cycle getCurrentCycle() {
        double normalizedTime = getNormalizedTime();

        if (normalizedTime < 0.25) { // 6h à 11h
            return Cycle.AURORE;
        }
        if (normalizedTime < 0.5) { // 11h à 18h
            return Cycle.JOUR;
        }
        if (normalizedTime < 0.75) { // 18h à 00h
            return Cycle.CREPUSCULE;
        }
        return Cycle.NUIT; // 00h à 6h
    }

    // Obtenir la couleur du filtre en fonction de l'heure ATTENTION PEUT PRENDRE DU TEMPS A OPTI
    @Override
    public Color getLightingColor() {
        double normalizedTime = getNormalizedTime();

        // Transition de 6h à 11h (aube)
        if (0.25 <= normalizedTime && normalizedTime <= 0.45) {
            double progress = (normalizedTime - 0.25) / (0.45 - 0.25);
            return interpolateColor(Color.rgb(0, 0, 50, 0.6), Color.TRANSPARENT, progress);
        }

        // Plein jour de 11h à 18h
        if (0.4 <= normalizedTime && normalizedTime <= 0.75) {
            return Color.TRANSPARENT;
        }

        // Transition fluide de 18h à 00h (crépuscule)
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
        // Juste avant la nuit
        if (0.9 <= normalizedTime && normalizedTime <= 1.0) {

            double progress = (normalizedTime - 0.9) / (1 - 0.9);
            return interpolateColor(Color.rgb(72, 41, 50, 0.4), Color.rgb(0, 0, 50, 0.6), progress);
        }
        // Pleine nuit
        if(0 <= normalizedTime && normalizedTime <= 0.25) {
            return Color.rgb(0, 0, 50, 0.6);
        }
        return Color.TRANSPARENT;
    }


    // Méthode pour interpolation de couleur, celle qui peut prendre du temps, il faudrait faire des tests
    private Color interpolateColor(Color start, Color end, double progress) {
        progress = Math.max(0, Math.min(1, progress)); // Limite le progress entre 0 et 1
        return new Color(
                start.getRed() + (end.getRed() - start.getRed()) * progress,
                start.getGreen() + (end.getGreen() - start.getGreen()) * progress,
                start.getBlue() + (end.getBlue() - start.getBlue()) * progress,
                start.getOpacity() + (end.getOpacity() - start.getOpacity()) * progress
        );
    }

    // Obtenir l'heure sous la forme 00:OO
    @Override
    public String getFormattedTime() {
        int totalMin = (int) (getNormalizedTime()*24*60);
        int adjustedMinutes = (totalMin) % (24 * 60); // Ajout de 12h en minutes et modulo pour éviter dépassement de 24h
        int hours = adjustedMinutes / 60;
        int minutes = adjustedMinutes % 60;

        return String.format("%02d:%02d", hours, minutes);
    }
}







