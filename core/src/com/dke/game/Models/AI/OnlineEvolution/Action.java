package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Models.DataStructs.Cell;

public class Action {
    private Cell destination;
    public enum ActionType{
        SHOT,MOVE
    }

    public Action(ActionType type, Cell destination) throws InvalidActionTypeException {
        if(type == type.MOVE){

        }else if(type == type.SHOT){

        }else{
            throw new InvalidActionTypeException("Invalid ActionType");
        }
    }
    public class InvalidActionTypeException extends Exception{
        public InvalidActionTypeException(String message) {
            super(message);
        }
    }
}
