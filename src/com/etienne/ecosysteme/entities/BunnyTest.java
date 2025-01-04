package com.etienne.ecosysteme.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.etienne.ecosysteme.environment.MapEnvironnement;

class BunnyTest {

    @Test
    void gen_deplacement() {
        Bunny bunny = new Bunny(5, 5);
        MapVivant mapVivant = new MapVivant(10, 10);
        MapEnvironnement mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");

        bunny.gen_deplacement(mapVivant, mapEnvironnement, 5, 5);

        int row = bunny.getRow();
        int col = bunny.getCol();

        assertTrue(row >= 0 && row < 10);
        assertTrue(col >= 0 && col < 10);
    }
}