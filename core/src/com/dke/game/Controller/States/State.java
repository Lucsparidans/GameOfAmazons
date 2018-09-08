package com.dke.game.Controller.States;


import com.dke.game.Controller.StateManager;
import com.dke.game.View.View;


public abstract class State extends View {
    protected StateManager stateManager;
    protected State(StateManager stateManager){
        this.stateManager = stateManager;
    }
}
