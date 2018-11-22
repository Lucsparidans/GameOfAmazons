package com.dke.game.Models.DataStructs;

import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;

public class GameState {
    private Board2D board2D;
    private Amazon2D[] amazon2DArrayList;
    private ArrayList<Arrow2D> arrow2DArrayList;
    private Move move;

    public GameState(Board2D board2D, Amazon2D[] amazon2DArrayList, ArrayList<Arrow2D> arrow2DArrayList, Move move) {
        this.board2D = board2D;
        this.amazon2DArrayList = amazon2DArrayList;
        this.arrow2DArrayList = arrow2DArrayList;
        this.move = move;
    }

    public Board2D getBoard2D() {
        return board2D;
    }

    public Amazon2D[] getAmazon2DArrayList() {
        return amazon2DArrayList;
    }

    public ArrayList<Arrow2D> getArrow2DArrayList() {
        return arrow2DArrayList;
    }

    public Move getMove() {
        return move;
    }
}
