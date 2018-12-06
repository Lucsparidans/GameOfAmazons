package com.dke.game.Controller.Player;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Views.GameView;

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
