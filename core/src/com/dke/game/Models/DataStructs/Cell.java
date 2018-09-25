package com.dke.game.Models.DataStructs;

public class Cell {
    private Piece content;
    public static final int CELL_SIZE = 60;
    private Coordinate topLeft, topRight, bottomRight, bottomLeft;
    private int i,j;

    public Cell(Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft) {
        this(topLeft,topRight,bottomRight,bottomLeft,-1,-1);
    }

    public Cell(Piece content, Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft, int i, int j) {
        this.content = content;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
        this.i = i;
        this.j = j;
    }

    public Cell(Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft, int i, int j) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
        this.i = i;
        this.j = j;
    }

    public Coordinate getTopLeft() {
        return topLeft;
    }

    public Coordinate getTopRight() {
        return topRight;
    }

    public Coordinate getBottomRight() {
        return bottomRight;
    }

    public Coordinate getBottomLeft() {
        return bottomLeft;
    }

    @Override
    public String toString() {
        return "bottomLeft : " + "(" + bottomLeft.getX() + "," + bottomLeft.getY() + ")" + "\n"
                + "topLeft : " + "(" + topLeft.getX() + "," + topLeft.getY() + ")" + "\n"
                + "topRight : " + "(" + topRight.getX() + "," + topRight.getY() + ")" + "\n"
                + "bottomRight : " + "(" + bottomRight.getX() + "," + bottomRight.getY() + ")" + "\n";
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

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
