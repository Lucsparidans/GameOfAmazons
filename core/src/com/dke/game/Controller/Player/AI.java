package com.dke.game.Controller.Player;

public class AI extends Player {
    public AI(char side) {
        super(side);
    }

    @Override
    public void handleInput() {

    }
    public enum Phase{
        PHASE_ONE,PHASE_TWO,PHASE_THREE
    }
}
