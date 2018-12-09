package com.dke.game.Models.DataStructs;

/**
 * The cell class of which the array in the board class exists, this is basically a container for the datastructures we might need
 */
public class Cell {
    private Piece content;
    private boolean isAvailable = false;
    public static int CELL_SIZE = 50;
    private Coordinate topLeft, topRight, bottomRight, bottomLeft;
    private int i,j;



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

//    @Override
//    public String toString() {
//        return "bottomLeft : " + "(" + bottomLeft.getX() + "," + bottomLeft.getY() + ")" + "\n"
//                + "topLeft : " + "(" + topLeft.getX() + "," + topLeft.getY() + ")" + "\n"
//                + "topRight : " + "(" + topRight.getX() + "," + topRight.getY() + ")" + "\n"
//                + "bottomRight : " + "(" + bottomRight.getX() + "," + bottomRight.getY() + ")" + "\n";
//    }

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

    public void setAvailable(String a){
        if(a == "true")
            this.isAvailable = true;

        else
            this.isAvailable = false;
    }

    public boolean getAvailable(){
        return isAvailable;
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

    public Piece getContent() {
        return content;
    }
}
//<editor-fold desc="Old code">
/*
public boolean isValidChoice(int phase, boolean colour, Cell[][] board, int xPos, int yPos) {

        if (phase == 1) {
            if (this.getContentID().contains("Amazon")) {
                if (xPos == 0 && yPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()))
                            return true;
                    }
                }
                if (xPos == 0 && yPos == board.length) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                            return true;

                    }
                }
                if (xPos == board.length && yPos == board.length) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()))
                            return true;

                    }
                }
                if (xPos == board.length && yPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()))
                            return true;

                    }
                }
                if (xPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()))
                            return true;

                    }

                }
                if (xPos == board.length) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()))
                            return true;
                    }
                }
                if (yPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos].isOccupied()))
                            return true;
                    }
                }
                if (yPos == board.length) {

                }
                if (colour == true && this.getContentID().contains("W")) {

                    if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                        return true;
                } else if (colour == false && this.getContentID().contains("B")) {
                    if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                        return true;

                }
            }
        }
            if ((phase == 2) || (phase == 3)) {
                if (this.getContentID().contains("This cell is empty")) {
                    return true;
                }
            }
            return false;

    }
 */
//</editor-fold>
