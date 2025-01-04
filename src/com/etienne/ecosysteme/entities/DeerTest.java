package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeerTest {
    @Test
    void gen_deplacement() {
        Deer deer = new Deer(0, 0);
        MapVivant mapVivants = new MapVivant(50, 50);
        MapEnvironnement grid = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");

        deer.gen_deplacement(mapVivants, grid, 0, 0);

    }

    @Test
    void isMenace() {
        Deer deer = new Deer(0, 0);
        Humain humain = new Humain(1, 1);

        assertFalse(deer.isMenace(humain));
    }
}