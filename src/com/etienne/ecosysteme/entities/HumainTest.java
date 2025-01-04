package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumainTest {

    @Test
    void gen_deplacement_NoMenace() {
        Humain humain = new Humain(0, 0);
        MapVivant mapVivant = new MapVivant(50, 50);
        MapEnvironnement map = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");

        humain.gen_deplacement(mapVivant, map, 0, 0);

        assertEquals(0, humain.getRow());
        assertEquals(0, humain.getCol());
    }


    @Test
    void getFacteurVitesseNuit() {
        Humain humain = new Humain(0, 0);
        assertEquals(1.5, humain.getFacteurVitesseNuit());
    }

    @Test
    void getFacteurVitesseJour() {
        Humain humain = new Humain(0, 0);
        assertEquals(1, humain.getFacteurVitesseJour());
    }
}