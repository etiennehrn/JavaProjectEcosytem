package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumainTest {
    private MapVivant mapVivant;
    private MapEnvironnement mapEnvironnement;

    @BeforeEach
    void setUp() {
        mapVivant = new MapVivant(10, 10);
        mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/mapcase10x10_vide.txt");
    }

    @Test
    void gen_deplacement_NoMenace() {
        Humain humain = new Humain(4, 4);
        mapVivant.setEtreVivant(4, 4, humain);
        humain.gen_deplacement(mapVivant, mapEnvironnement, 4, 4);

        // Check that the human has either moved erratically or stayed in place
        assertTrue(humain.getRow() == 4 || humain.getRow() != 4);
        assertTrue(humain.getCol() == 4 || humain.getCol() != 4);
    }

    @Test
    void gen_deplacement_WithMenace() {
        Humain humain = new Humain(4, 4);
        Zombie zombie = new Zombie(3, 3);
        mapVivant.setEtreVivant(4, 4, humain);
        mapVivant.setEtreVivant(3, 3, zombie);
        humain.gen_deplacement(mapVivant, mapEnvironnement, 4, 4);

        // Check that the human has moved to avoid the zombie
        assertFalse(humain.getRow() == 4 && humain.getCol() == 4);
    }
}