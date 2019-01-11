package com.dke.game.Models.AI.OnlineEvolution;

public class Action {
    public enum ActionType{
        SHOT,MOVE
    }

    public Action(ActionType type) throws InvalidActionTypeException {
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
