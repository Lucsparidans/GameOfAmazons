package com.dke.game.Models.DataStructs;

public abstract class Arrow extends Piece{
    /**
     * The arrow class with all its representational data
     */
    private static int ID = 0;
    private Integer idNumber;
    private String idString = "A";
    protected Cell cell;
    private boolean isAlive;

    public Arrow(Cell cell) {
        super(cell);
        this.cell = cell;
        idNumber = ID++;
        isAlive=true;
    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
    public void kill(){
        this.isAlive=false;
    }

    @Override
    protected String getType() {
        return idString;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void updateCell(Cell cell){
        this.cell = cell;
    }
}
