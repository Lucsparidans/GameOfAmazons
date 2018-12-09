package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;

import java.util.ArrayList;

public class Move {
    private Cell queenTo;
    private Cell arrowTo;
    private Cell queenFrom;
    private Amazon2D queen;



    public Move(Amazon2D queen, Cell newQueen, Cell arrowTo) {
        this.queenTo = newQueen;
        this.arrowTo = arrowTo;
        this.queen = queen;
        this.queenFrom = queen.getCell();
    }

    public Cell getQueenTo() {
        return queenTo;
    }

    public Cell getArrowTo() {
        return arrowTo;
    }

    public Amazon2D getQueen() {
        return queen;
    }

    public boolean isPlayerMaximizing(Player p){
        if(p.getSide()==queen.getSide()){
            return true;
        }
        else{
            return false;
        }
    }

}
