package com.dke.game.Controller.Player;

public abstract class Player {
    protected final char side;

    public Player(char side) {
        this.side = side;
    }

    public abstract void handleInput();

}
