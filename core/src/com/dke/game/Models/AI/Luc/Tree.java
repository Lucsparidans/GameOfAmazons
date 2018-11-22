package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.GameState;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;


public class Tree {
   private Node<GameState> rootNode;
   private GameState initialState;
   private final int maxDepth = 5;
   private Board2D bestEval;
   private Board2D worstEval;



    public Tree(Board2D board2D, Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS) {
        initialState = new GameState(board2D,amazon2DS,arrow2DS);

        rootNode = new Node<>(initialState);
        expandNode(rootNode);
    }
    public void expandNode(Node<GameState> current){
        if(current.getDepth(rootNode) == 5 || current.getChildren() == null){
            return;
        }
        ArrayList<GameState> possibleMoves = getPossibleStates(current);
        for (GameState g:possibleMoves) {
            current.addChild(new Node<>(g));
        }
        for (Node<GameState> node:current.getChildren()) {
            expandNode(node);
        }


    }
    public ArrayList<GameState> getPossibleStates(Node<GameState> current){
        ArrayList<GameState> possibleStates = new ArrayList<>();
        GameState currentState = current.getData();
        Amazon2D[] amazon2DS = currentState.getAmazon2DArrayList();
        ArrayList<Arrow2D> arrow2DS = currentState.getArrow2DArrayList();
        Board2D board2D = currentState.getBoard2D();
        Amazon2D[] testQueens = amazon2DS.clone();
        ArrayList<Arrow2D> testArrows = (ArrayList<Arrow2D>) arrow2DS.clone();
        ArrayList<Move> moves = new ArrayList<>();
        Board2D testBoard = new Board2D();
        for (Amazon2D a:testQueens) {
            testBoard.occupy(a,a.getCell());
        }
        for (Arrow2D a:testArrows) {
            testBoard.occupy(a,a.getCell());
        }

        for (Amazon2D a:testQueens) {
            a.possibleMoves(testBoard);
            ArrayList<Cell> possibleMoves = a.getPossibleMoves();
            for (Cell c:possibleMoves) {
                a.move(c);
                a.possibleMoves(testBoard);
                ArrayList<Cell> shootMoves = a.getPossibleMoves();
                for (Cell cs:shootMoves) {
                    moves.add(new Move(a,c,cs));
                }
            }
        }



        return null;
    }

}



