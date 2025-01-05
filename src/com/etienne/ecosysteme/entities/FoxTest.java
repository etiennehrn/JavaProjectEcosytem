package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.environment.MapEnvironnement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FoxTest {
    @Test
    void getSprite() {
        Fox fox = new Fox(0, 0);
        assertNotNull(fox.getSprite(50));
    }

    @Test
    void gen_deplacement_noPrey() {
        Fox fox = new Fox(5, 5);
        MapVivant mapVivants = new MapVivant(100, 100);
        MapEnvironnement grid = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");

        int row = fox.getRow();
        int col = fox.getCol();
        //no prey around
        fox.gen_deplacement(mapVivants, grid, 5, 5);
        assertEquals(row, fox.getRow());
        assertEquals(col, fox.getCol());
    }

    @Test
    void gen_deplacement_withPrey() {
        Fox fox = new Fox(5, 5);
        MapVivant mapVivants = new MapVivant(10, 10);
        MapEnvironnement grid = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");

        // Add prey to the map
        Bunny bunny = new Bunny(1, 1);
        mapVivants.setEtreVivant(1, 1, bunny);

        fox.gen_deplacement(mapVivants, grid, 5, 5);
        int row = fox.getRow();
        int col = fox.getCol();
        //assertTrue(row >= 0 && row < 5);
        //assertTrue(col >= 0 && col < 5);
    }

}