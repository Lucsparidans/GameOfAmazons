package com.dke.game.Models.AI;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Board2D;
import  com.dke.game.Controller.GameLoop.*;

import java.util.ArrayList;
import java.util.List;

public class GameStateTree {
    boolean onlyMove;
    private TreeNode<Double> stateRoot;
    private Amazon queen;
    private ArrayList<Cell> possibleMoves;

    //constructor
    public GameStateTree(Amazon queen) {

        this.queen = queen; //initial queen
    }

    public Cell[][] makeTree(Board2D board2d){
        //basically need the state of the board per queen's move
        //queens possible moves
        //each move individually stored in a node with other queen's locations
        //need to save which cell is occupied
        for (Amazon queen:stateRoot.getOurQueens()) {// for every queen
            queen.possibleMoves(board2d); //get possible moves
            possibleMoves = queen.getPossibleMoves();
            GameLoop gameLoop= new GameLoop();
            Board2D board2DD = gameLoop.getBoard2D();
            stateRoot.setBoard(board2DD.getBoard()); // save the board state into the stateroot node
            for (Cell child : possibleMoves) { //for every one of thoes potential cells to palce the queen


                TreeNode node = new TreeNode(child.getI(), child.getJ());// we make a node with the coordinates of the cell
                node.setRoot(stateRoot);// set that it came from that state
                node.getHeight(stateRoot);// we get height of the tree so far, should be 2

                node.setBoard(board2DD.getBoard()); //we save that board state in tat node
                double dataNode = node.positioHheuristics();
                node.setValue(dataNode);


            }
            double data = MinMax.MiniMax(stateRoot, 2, true,Integer.MIN_VALUE,Integer.MAX_VALUE);//calling the minmax to tell us which one is the best

           List<TreeNode<Double>> children = stateRoot.getChildren();
            for (TreeNode child : children) {//search for the node with this value
                    if(child.getValue()==data){
                        return child.getBoard();
                    }   }
        }


       for(Cell cell: possibleMoves){

           queen.move(cell);
           stateRoot.addQueen(queen);//add queen to the state
           //add position of others to the state

       }


        return new Cell[0][0]; //fails
    }
}
