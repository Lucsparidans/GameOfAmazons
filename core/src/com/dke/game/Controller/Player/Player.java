package com.dke.game.Controller.Player;

import com.dke.game.Views.GameView;

public abstract class Player {
    protected char side;

    public Player(char side) {
        this.side = side;
    }
    public abstract void performTurn();

}
