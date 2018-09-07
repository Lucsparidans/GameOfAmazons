package com.dke.game.Controller;

import com.dke.game.Controller.States.State;
import java.util.Stack;

public class StateManager {
    private static Stack<State> stateStack = new Stack<>();
    public StateManager pop(){
        stateStack.peek().dispose();
        stateStack.pop();
        return this;
    }
    public void push(State state){
        stateStack.push(state);
    }
}
