package com.dke.game.Models.DataStructs;

public class Coordinate {
    private int xPos;
    private int yPos;

    public Coordinate(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getX(){
        return this.xPos;
    }
    public int getY() {
        return yPos;
    }

    public void setCoords(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

}
