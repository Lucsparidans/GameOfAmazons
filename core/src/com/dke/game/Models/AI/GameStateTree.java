package com.dke.game.Models.AI;

import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;

import java.util.ArrayList;

public class GameStateTree {
    TreeNode<Double> stateRoot;
    Amazon queen;

    public GameStateTree(Amazon queen) {
        this.queen = queen; //initial queen
    }

    public void makeTree(){
        //basically need the state of the board per queen's move
        //queens possible moves
        //each move individually stored in a node with other queen's locations
        //need to save which cell is occupied
        for (Amazon queen:stateRoot.ourQueens
             ) {
            ArrayList<Cell> possibleMoves= queen.getPossibleMoves();

        }


       for(Cell cell: possibleMoves){

           queen.move(cell);
           stateRoot.allQueens.add(queen);//add queen to the state
           //add position of others to the state

       }


    }
}
