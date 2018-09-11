package com.dke.game.Models.DataStructs;

public abstract class Arrow extends Piece{

    private static int ID = 0;
    private Integer idNumber;
    private static String idString = "Arrow: ";

    public Arrow() {
        this.idNumber = ID++;
    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
}
