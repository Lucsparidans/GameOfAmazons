package com.dke.game.Models.DataStructs;

import com.dke.game.Models.GraphicalModels.Amazon2D;

public class Move {
    private Cell queenTo;
    private Cell arrowTo;
    private Amazon2D queen;


    public Move(Amazon2D queen, Cell newQueen, Cell arrowTo) {
        this.queenTo = newQueen;
        this.arrowTo = arrowTo;
        this.queen = queen;
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


}
