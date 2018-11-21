package com.dke.game.Models.DataStructs;

import com.dke.game.Models.GraphicalModels.Amazon2D;

public class Move {
    private Cell queenTo;
    private Cell arrowTo;
    private Amazon2D queen;
    private double score;

    public Move(Cell newQueen, Cell arrowTo, Amazon2D queen, double score) {
        this.queenTo = newQueen;
        this.arrowTo = arrowTo;
        this.queen = queen;
        this.score = score;
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

    public double getScore() {
        return score;
    }
}
