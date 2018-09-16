package com.dke.game.Models.DataStructs;

public abstract class Amazon extends Piece{
    private final char side;    //B for black & W for white.
    private static String idString = "Amazon: ";
    private static int ID = 0;
    private Integer idNumber;
    private int posX;
    private int posY;

    public Amazon(char side, int Xpos, int Ypos){
        this.side = side;
        this.idNumber = ID++;
        this.posX = Xpos;
        this.posY = Ypos;

    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
    protected int getposX() {
        return posX;
    }
    protected int getposY() {
        return posY;
    }

    public void move(int newX, int newY, Cell[][] board){
        board[this.getposX()][this.getposY()].unOccupy();
        board[newX][newY].occupy(this);

    }
}
