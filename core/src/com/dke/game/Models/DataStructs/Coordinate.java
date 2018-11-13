package com.dke.game.Models.DataStructs;

/**
 * Container to group a x and y coordinate in one object
 */
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
        return this.yPos;
    }

    public void setCoords(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

}
