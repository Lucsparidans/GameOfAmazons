package com.dke.game.Models.DataStructs;

public abstract class Arrow extends Piece{
    /**
     * The arrow class with all its representational data
     */
    private static int ID = 0;
    private Integer idNumber;
    private static String idString = "Arrow: ";
    protected Cell cell;

    public Arrow(Cell cell) {
        super(cell);
        this.cell = cell;
        idNumber = ID++;
    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
}
