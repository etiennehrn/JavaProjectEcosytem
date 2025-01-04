package com.etienne.ecosysteme.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompteurDeplacementTest {

    @Test
    void incrementer() {
        CompteurDeplacement compteur = new CompteurDeplacement(5);
        compteur.incrementer();
        assertFalse(compteur.doitSeDeplacer());
        compteur.incrementer();
        compteur.incrementer();
        compteur.incrementer();
        compteur.incrementer();
        assertTrue(compteur.doitSeDeplacer());
    }

    @Test
    void reset() {
        CompteurDeplacement compteur = new CompteurDeplacement(5);
        compteur.incrementer();
        compteur.incrementer();
        compteur.reset();
        assertFalse(compteur.doitSeDeplacer());
    }

    @Test
    void doitSeDeplacer() {
        CompteurDeplacement compteur = new CompteurDeplacement(3);
        compteur.incrementer();
        compteur.incrementer();
        assertFalse(compteur.doitSeDeplacer());
        compteur.incrementer();
        assertTrue(compteur.doitSeDeplacer());
    }

    @Test
    void getVitesse() {
        CompteurDeplacement compteur = new CompteurDeplacement(4);
        assertEquals(4, compteur.getVitesse());
    }

    @Test
    void setVitesse() {
        CompteurDeplacement compteur = new CompteurDeplacement(4);
        compteur.setVitesse(6);
        assertEquals(6, compteur.getVitesse());
    }
}