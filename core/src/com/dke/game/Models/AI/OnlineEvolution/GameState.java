package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Models.AI.MINMAX.TestBoard;

import java.util.Stack;

public class GameState {
    private Action parentAction;
    private TestBoard board;
    private TestBoard parentBoard;
    private boolean whiteMove;

    public GameState(Action action, GameState parent, TestBoard board, boolean whiteMove) {
        this.parentAction = action;
        this.parentBoard = parent.getBoard();
        this.board=board;
        this.whiteMove = whiteMove;
    }
    public TestBoard getBoard(){
        return this.board;
    }

    public Action getParentAction() {
        return parentAction;
    }

    public TestBoard getParentBoard() {
        return parentBoard;
    }

    public boolean isWhiteMove() {
        return whiteMove;
    }
}
