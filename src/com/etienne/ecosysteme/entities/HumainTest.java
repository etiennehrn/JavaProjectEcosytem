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

    }

    @Test
    void gen_deplacement_WithMenace() {

    }
}