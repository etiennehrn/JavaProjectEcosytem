package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class BearTest {

    @Test
    void gen_deplacement() {
        Bear bear = new Bear(5, 5);
        MapVivant mapVivant = new MapVivant(10, 10);
        MapEnvironnement mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");

        bear.gen_deplacement(mapVivant, mapEnvironnement, 5, 5);

        int row = bear.getRow();
        int col = bear.getCol();

        assertTrue(row >= 0 && row < 10);
        assertTrue(col >= 0 && col < 10);
    }
}