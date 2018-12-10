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

    public char getOpposingSide() {
        if (this.side == 'W') {
            return 'B';
        } else {
            return 'W';
        }
    }
}
