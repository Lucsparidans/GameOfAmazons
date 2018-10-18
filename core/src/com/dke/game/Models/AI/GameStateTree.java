package com.dke.game.Models.AI;

import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;

public class GameStateTree {
    private TreeNode<Double> stateRoot;
    private Amazon queen;
    private ArrayList<Cell> possibleMoves;
    public GameStateTree(Amazon queen) {
        this.queen = queen; //initial queen
    }

    public void makeTree(Board2D board2d){
        //basically need the state of the board per queen's move
        //queens possible moves
        //each move individually stored in a node with other queen's locations
        //need to save which cell is occupied
        for (Amazon queen:stateRoot.ourQueens
             ) {
            queen.possibleMoves(board2d);
            possibleMoves = queen.getPossibleMoves();

        }


       for(Cell cell: possibleMoves){

           queen.move(cell);
           stateRoot.allQueens.add(queen);//add queen to the state
           //add position of others to the state

       }


    }
}
