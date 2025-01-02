package com.etienne.ecosysteme.environment;

import com.etienne.ecosysteme.entities.EtreVivant;
import com.etienne.ecosysteme.entities.MapVivant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Random;

public class Pluie implements IPrecipitation {
    private boolean active = false;
    private static final int MIN_PLUIE = 3;
    private static final int MAX_PLUIE = 6;

    private static int gouttePluie = 5;
    // Charger l'image de pluie

    public static Image rainImage = new Image(Objects.requireNonNull(Pluie.class.getResourceAsStream("/ressources/textures/pluie/pluie.png")));


    @Override
    public void demarrer() {
        active = true;
        Random rand  = new Random();
        gouttePluie = MIN_PLUIE + rand.nextInt((MAX_PLUIE - MIN_PLUIE) + 1);
    }

    @Override
    public void arreter() {
        active = false;
    }

    @Override
    public void appliquerEffets(MapVivant mapVivant) {

        for (int row = 0; row < mapVivant.getRows(); row++) {
            for (int col = 0; col < mapVivant.getCols(); col++) {
                EtreVivant vivant = mapVivant.getEtreVivant(row, col);
                if (vivant != null) {
                    vivant.setVitesse(vivant.getVitesse() + (getGouttePluie() / 2 )); // Augmente la vitesse en fonction de la force de la pluie
                }
            }
        }
    }
    @Override
    public void stopperEffets(MapVivant mapVivant) {
        for (int row = 0; row < mapVivant.getRows(); row++) {
            for (int col = 0; col < mapVivant.getCols(); col++) {
                EtreVivant vivant = mapVivant.getEtreVivant(row, col);
                if (vivant != null) {
                    vivant.setVitesse((int) (vivant.getVitesse() - 1));
                }
            }
        }
    }
    public boolean isActive() {
        return active;
    }

    public int getGouttePluie() {
        return gouttePluie;
    }
    public void setGouttePluie(int gouttePluie) {
        Pluie.gouttePluie = gouttePluie;
    }

}