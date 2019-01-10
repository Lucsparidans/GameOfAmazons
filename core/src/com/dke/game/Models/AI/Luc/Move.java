package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;

public class Move {
    private Cell queenTo;
    private Cell arrowTo;
    private Cell queenFrom;
    private Amazon2D queen;
    private double value;



    public Move(Amazon2D queen, Cell newQueen, Cell arrowTo) {
        this.queenTo = newQueen;
        this.arrowTo = arrowTo;
        this.queen = queen;
        this.queenFrom = queen.getCell();
        value = 0;
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

    public Cell getQueenFrom(){
        return queenFrom;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
