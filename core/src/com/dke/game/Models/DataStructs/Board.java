package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The board class with the array and dimensions
 */
public abstract class Board extends Actor {
    public static int height;
    public static int width;
    public BoardSize boardSize = BoardSize.TENxTEN;


    public Board() throws IllegalBoardDimensionsException {
        if(boardSize == BoardSize.FIVExSIX){
            height = 5;
            width = 6;
        }
        else if(boardSize == BoardSize.TENxTEN){
            height = 10;
            width = 10;
        }
        else{
            throw new IllegalBoardDimensionsException("Unsupported board dimensions");
        }
    }
    public class IllegalBoardDimensionsException extends Exception{
        public IllegalBoardDimensionsException(String message) {
            super(message);
        }
    }

    public enum BoardSize{
    FIVExSIX,TENxTEN,ERROR
}

}
