package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Models.AI.MINMAX.TestBoard;

import java.util.Stack;

public class GameState {

    private TestBoard board;
    private TestBoard parentBoard;
    private boolean whiteMove;
    private GameState parent;

    public GameState(GameState parent, TestBoard board, boolean whiteMove) {

        this.parent = parent;
        if(parent!=null) {
            this.parentBoard = parent.getBoard();
        }
        this.board=board;
        this.whiteMove = whiteMove;
    }
    public TestBoard getBoard(){
        return this.board;
    }



    public TestBoard getParentBoard() {
        return parentBoard;
    }

    public boolean isWhiteMove() {
        return whiteMove;
    }

    public GameState getParent() {
        return parent;
    }
}
