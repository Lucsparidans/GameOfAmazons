package com.dke.game.Models.DataStructs;




public abstract class Amazon extends Piece{
    private final char side;    //B for black & W for white.
    private static String idString = "Amazon: ";
    private static int ID = 0;
    private Integer idNumber;



    public Amazon(char side, Cell cell){
        super(cell);
        this.side = side;
        this.idNumber = ID++;


    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }




    protected void updateLocation(Coordinate c){
        this.location = c;
    }

}
