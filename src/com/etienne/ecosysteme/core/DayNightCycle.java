package com.etienne.ecosysteme.core;// Classe qui gère les cycles jour/nuit

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class DayNightCycle {
    // Enumération pour les différents cycles possibles
    public enum Cycle {
        JOUR, CREPUSCULE, NUIT, AURORE
    }

    private final int totalCycleDuration; // Temps total un cycle
    private int timeCounter;              // Compteur pour le temps
    private Timeline cycleTimeline;       // Variable pour le temps

    // Constructeur qui lance les cycles
    public DayNightCycle(int totalCycleDuration) {
        this.totalCycleDuration = totalCycleDuration;
        this.timeCounter = 0;
        startCycle();
    }

    // Le démarrage
    private void startCycle() {
        cycleTimeline = new Timeline(new KeyFrame(Duration.seconds(1), _ -> timeCounter = (timeCounter + 1) % totalCycleDuration));
        cycleTimeline.setCycleCount(Timeline.INDEFINITE);
        cycleTimeline.play();
    }

    // On récupe l'heure
    public double getNormalizedTime() {
        return (double) timeCounter / totalCycleDuration;
    }

    // On récupère le cycle
    public Cycle getCurrentCycle() {
        double normalizedTime = getNormalizedTime();

        if (normalizedTime < 0.25) {
            return Cycle.JOUR;
        }
        if (normalizedTime < 0.50) {
            return Cycle.CREPUSCULE;
        }
        if (normalizedTime < 0.75) {
            return Cycle.NUIT;
        }
        return Cycle.AURORE;
    }

    // Obtenir la couleur du filtre en fonction de l'heure ATTENTION PEUT PRENDRE DU TEMPS A OPTI
    public Color getLightingColor() {
        double normalizedTime = getNormalizedTime();
        // JOUR
        if (normalizedTime < 0.25) {
            return Color.TRANSPARENT;
        }
        // CREPUSCULE
        if (normalizedTime < 0.50) {
            // -> [0;1]
            double progress = (normalizedTime - 0.25) / 0.25;
            return interpolateColor(Color.TRANSPARENT, Color.rgb(255, 100, 10, 0.4), progress);
        }
        // NUIT
        if (normalizedTime < 0.75) {
            double progress = (normalizedTime - 0.5) / 0.25;
            return interpolateColor(Color.rgb(255, 100, 10, 0.4), Color.rgb(0, 0, 50, 0.7), progress);
        }
        // AURORE
        else {
            double progress = (normalizedTime - 0.75) / 0.25;
            return interpolateColor(Color.rgb(0, 0, 50, 0.7), Color.rgb(255, 223, 186, 0.5), progress);
        }
    }

    // Méthode pour interpolation de couleur, celle qui peut prendre du temps, faudrait faire des tests
    private Color interpolateColor(Color start, Color end, double progress) {
        return new Color(start.getRed() + (end.getRed() - start.getRed())*progress,start.getGreen() + (end.getGreen() - start.getGreen()) * progress, start.getBlue() + (end.getBlue() - start.getBlue()) * progress,start.getOpacity() + (end.getOpacity() - start.getOpacity()) * progress);
    }

    // Obtenir l'heure sous la forme 00:OO
    public String getFormattedTime() {
        int totalMin = (int) (getNormalizedTime()*24*60);
        int adjustedMinutes = (totalMin + 10 * 60) % (24 * 60); // Ajout de 12h en minutes et modulo pour éviter dépassement de 24h
        int hours = adjustedMinutes / 60;
        int minutes = adjustedMinutes % 60;

        return String.format("%02d:%02d", hours, minutes);
    }
}







