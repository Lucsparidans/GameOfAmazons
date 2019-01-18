package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;

public class Action {
    private Cell destination;
    private Amazon2D amazon;
    private ActionType actionType;
    public enum ActionType{
        SHOT,MOVE
    }


    public Action(ActionType type, Cell destination, Amazon2D amazon2D) throws InvalidActionTypeException {
        if(type == type.MOVE){
            actionType = ActionType.MOVE;
            this.amazon = amazon2D;
            this.destination = destination;
        }else if(type == type.SHOT){
            actionType = ActionType.SHOT;
            this.destination = destination;
        }else{
            throw new InvalidActionTypeException("Invalid ActionType");
        }
    }
    public class InvalidActionTypeException extends Exception{
        public InvalidActionTypeException(String message) {
            super(message);
        }
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Cell getDestination() {
        return destination;
    }

    public Amazon2D getAmazon() {
        return amazon;
    }
}
