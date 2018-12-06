package com.dke.game.Controller.Player;

public abstract class Player {
    protected char side;


    public Player(char side) {
        this.side = side;
    }

    public abstract void performTurn();

    public char getSide() {
        return side;
    }
}
