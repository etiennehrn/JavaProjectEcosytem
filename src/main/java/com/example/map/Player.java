package com.example.map;

public class Player {
    public int x;
    public int y;
    public int visionRange;
    public int vitesse;

    public Player() {
        this.x = 50; //position Initiale en x
        this.y = 50;
        this.visionRange = 20;
        this.vitesse = 3;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVitesse() {
        return vitesse;
    }
}
