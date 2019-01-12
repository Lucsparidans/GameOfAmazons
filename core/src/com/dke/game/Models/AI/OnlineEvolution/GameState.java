package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Models.AI.MINMAX.TestBoard;

import java.util.Stack;

public class GameState {
    private Stack<Action> actionPath;
    private Action parentAction;
    private TestBoard board;
    private TestBoard parentBoard;

    private TestBoard getBoard(){
        return this.board;
    }
    public GameState(Stack<Action> actionPath, Action action, GameState parent, TestBoard board) {
        this.actionPath = actionPath;
        this.parentAction = action;
        actionPath.push(action);
        this.parentBoard = parent.getBoard();
    }
}
