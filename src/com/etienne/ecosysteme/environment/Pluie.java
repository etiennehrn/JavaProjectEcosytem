package com.etienne.ecosysteme.environment;

import com.etienne.ecosysteme.entities.EtreVivant;
import com.etienne.ecosysteme.entities.MapVivant;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

/**
 * Classe représentant les précipitations de pluie dans l'écosystème.
 * La pluie a des effets dynamiques sur les entités vivantes, comme l'augmentation de leur vitesse.
 */
public class Pluie implements IPrecipitation {

    // Indique si la pluie est active
    private boolean active = false;

    // Nombre minimal et maximal de gouttes de pluie
    private static final int MIN_PLUIE = 3;
    private static final int MAX_PLUIE = 6;

    // Nombre de gouttes de pluie actuelles
    private static int gouttePluie = 5;

    // Image utilisée pour représenter visuellement la pluie
    public static final Image rainImage = new Image(Objects.requireNonNull(
            Pluie.class.getResourceAsStream("/ressources/textures/pluie/pluie.png"))
    );

    /**
     * Démarre les précipitations de pluie.
     * Définit la pluie comme active et génère aléatoirement le nombre de gouttes dans une plage définie.
     */
    @Override
    public void demarrer() {
        active = true;
        Random rand = new Random();
        gouttePluie = MIN_PLUIE + rand.nextInt((MAX_PLUIE - MIN_PLUIE) + 1);
    }

    /**
     * Arrête les précipitations de pluie.
     * Définit la pluie comme inactive.
     */
    @Override
    public void arreter() {
        active = false;
    }

    /**
     * Applique les effets de la pluie sur toutes les entités vivantes de la carte.
     * Augmente la vitesse des entités vivantes en fonction de la force de la pluie.
     *
     * @param mapVivant Carte contenant les entités vivantes.
     */
    @Override
    public void appliquerEffets(MapVivant mapVivant) {
        for (int row = 0; row < mapVivant.getRows(); row++) {
            for (int col = 0; col < mapVivant.getCols(); col++) {
                EtreVivant vivant = mapVivant.getEtreVivant(row, col);
                if (vivant != null) {
                    // Augmente la vitesse proportionnellement à la force de la pluie
                    vivant.setVitesse(vivant.getVitesse() + (getGouttePluie() / 2));
                }
            }
        }
    }

    /**
     * Arrête les effets de la pluie sur toutes les entités vivantes de la carte.
     * Réduit légèrement la vitesse des entités vivantes.
     *
     * @param mapVivant Carte contenant les entités vivantes.
     */
    @Override
    public void stopperEffets(MapVivant mapVivant) {
        for (int row = 0; row < mapVivant.getRows(); row++) {
            for (int col = 0; col < mapVivant.getCols(); col++) {
                EtreVivant vivant = mapVivant.getEtreVivant(row, col);
                if (vivant != null) {
                    // Réduit la vitesse pour compenser les effets de la pluie
                    vivant.setVitesse(Math.max(1, vivant.getVitesse() - 1));
                }
            }
        }
    }

    /**
     * Indique si la pluie est actuellement active.
     *
     * @return {@code true} si la pluie est active, {@code false} sinon.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Retourne le nombre actuel de gouttes de pluie.
     *
     * @return Nombre de gouttes de pluie.
     */
    public int getGouttePluie() {
        return gouttePluie;
    }

    /**
     * Définit le nombre de gouttes de pluie.
     *
     * @param gouttePluie Nombre de gouttes de pluie à définir.
     */
    public void setGouttePluie(int gouttePluie) {
        Pluie.gouttePluie = gouttePluie;
    }
}
