package com.etienne.ecosysteme.entities;

import com.etienne.ecosysteme.core.DayNightCycleImpl;
import com.etienne.ecosysteme.environment.MapEnvironnement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapVivantTest {

    private MapVivant mapVivant;
    private MapEnvironnement mapEnvironnement;


    @Test
    void populate() throws IOException {
        mapVivant = new MapVivant(100, 100);
        mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/nnew_map1_test.txt");
        mapVivant.populate("src/ressources/map/map_vivant/map_vivant.txt", 5, 5, 5, mapEnvironnement);
        int rows= mapEnvironnement.getRows();
        int cols= mapEnvironnement.getCols();
        //on sait que la map_vivant contient déjà 4 humains, 3 zombies et 6 animaux
        int hmn=0;
        int zmb=0;
        int anim=0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mapVivant.getEtreVivant(i, j) instanceof Humain) {
                    hmn++;
                }
                if (mapVivant.getEtreVivant(i, j) instanceof Zombie) {
                    zmb++;
                }
                if (mapVivant.getEtreVivant(i, j) instanceof Animaux) {
                    anim++;
                }
            }
        }
        assertEquals(9, hmn);
        assertEquals(8, zmb);
        //des animaux peuvent mourir
        assertTrue(anim <= 11);
    }

    /*
    @Test
    void update() {

        DayNightCycleImpl dayNightCycle = new DayNightCycleImpl(240);
        mapVivant.update(mapEnvironnement, dayNightCycle);
        assertNull(mapVivant.getEtreVivant(0, 0));
    }
    */

    @Test
    void isWithinBounds() {
        mapVivant = new MapVivant(10, 10);
        mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/mapcase10x10_vide.txt");
        assertTrue(mapVivant.isWithinBounds(5, 5));
        assertFalse(mapVivant.isWithinBounds(15, 15));
    }

    @Test
    void getEtreVivant() {
        mapVivant = new MapVivant(10, 10);
        mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/mapcase10x10_vide.txt");
        assertNull(mapVivant.getEtreVivant(0, 0));
        EtreVivant etreVivant = new Fox(5, 5);
        mapVivant.setEtreVivant(5, 5, etreVivant);
        assertEquals(etreVivant, mapVivant.getEtreVivant(5, 5));
    }

    @Test
    void setEtreVivant() {
        mapVivant = new MapVivant(10, 10);
        mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/mapcase10x10_vide.txt");
        EtreVivant wolf = new Wolf(0, 0);
        mapVivant.setEtreVivant(9, 9, wolf);
        assertEquals(wolf, mapVivant.getEtreVivant(9, 9));
    }


    @Test
    void getRows() {
        mapVivant = new MapVivant(10, 10);
        mapEnvironnement = new MapEnvironnement("src/ressources/map/map_case/mapcase10x10_vide.txt");
        assertEquals(10, mapVivant.getRows());
    }

    @Test
    void setRows() {
        mapVivant = new MapVivant(10, 10);
        mapVivant.setRows(20);
        assertEquals(20, mapVivant.getRows());
    }

    @Test
    void getCols() {
        mapVivant = new MapVivant(10, 10);
        assertEquals(10, mapVivant.getCols());
    }

    @Test
    void setCols() {
        mapVivant = new MapVivant(10, 10);
        mapVivant.setCols(20);
        assertEquals(20, mapVivant.getCols());
    }
}