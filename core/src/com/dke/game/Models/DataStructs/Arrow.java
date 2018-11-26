package com.dke.game.Models.DataStructs;

public abstract class Arrow extends Piece{

    private static int ID = 0;
    private Integer idNumber;
    private static String idString = "Arrow: ";
    private boolean isAlive;

    public Arrow(Cell cell) {
        super(cell);
        idNumber = ID++;
        isAlive = true;
    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
    public void kill(){
        isAlive = false;
    }



    public boolean isAlive() {
        return isAlive;
    }
}
