package com.dke.game.Models.DataStructs;

public class Cell {
    private Piece content;
    public static final int CELL_SIZE = 30;
    private Coordinate bottomLeft;
    private Coordinate bottomRight;
    private Coordinate topLeft;
    private Coordinate topRight;
    private String COORD;
    private boolean isAvailable = false;

    public Cell(Coordinate bottomLeft, Coordinate bottomRight, Coordinate topLeft, Coordinate topRight, String COORD) {
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.COORD = COORD;
    }

    public Cell() {
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

    public void setAvailable(String a){
        if(a == "true")
            this.isAvailable = true;

        else
            this.isAvailable = false;

    }


    public boolean isValidChoice(int phase, boolean colour, Cell[][] board, int xPos, int yPos){

        if(phase == 1){
            if(this.getContentID().contains("Amazon")) {
                if (colour == true && this.getContentID().contains("W")) {
                    if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                        return true;
                }
                else if(colour == false && this.getContentID().contains("B")){
                    if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                        return true;
                }
            }
        }
        if((phase == 2) || (phase == 3)){
            if(this.getContentID().contains("This cell is empty")){
                return true;
            }
        }
        return false;
    }

    public boolean getAvailable(){
        return isAvailable;
    }

    public void occupy(Piece piece){
        this.content = piece;
    }
    public void unOccupy() { this.content = null; }
}
