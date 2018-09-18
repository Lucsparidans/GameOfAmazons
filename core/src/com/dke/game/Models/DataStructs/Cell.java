package com.dke.game.Models.DataStructs;

public class Cell {
    private Piece content;
    public static final int CELL_SIZE = 30;
    private Coordinate bottomLeft;
    private Coordinate bottomRight;
    private Coordinate topLeft;
    private Coordinate topRight;
    private final String COORD;

    public Cell(Coordinate bottomLeft, Coordinate bottomRight, Coordinate topLeft, Coordinate topRight, String COORD) {
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.COORD = COORD;
    }

    public String getContentID(){
        if(content != null){
            return content.getID();
        }
        else{
            return "This cell is empty";
        }
    }

    public boolean isOccupied(){
        if(content != null){
            return true;
        }
        else{
            return false;
        }
    }

    public void occupy(Piece piece){
        this.content = piece;
    }
    public void unOccupy() { this.content = null; }
}
