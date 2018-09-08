package com.dke.game.Models.DataStructs;

public class Cell {
    private Piece content;
    public static final int CELL_SIZE = 30;
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

    public void occupy(Piece piece){
        this.content = piece;
    }
}
