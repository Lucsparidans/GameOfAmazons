package com.dke.game.Models.AI.Luc;



import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.GameState;
import com.dke.game.Models.DataStructs.Pieces;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;


public class Tree {
   private Node<GameState> rootNode;
   private GameState initialState;
   private final int maxDepth = 5;


    public Tree(Board2D board2D, Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS) {
        initialState = new GameState(board2D,amazon2DS,arrow2DS);
        rootNode = new Node<>(initialState);
        createTree(rootNode);
    }
    public void createTree(Node<GameState> current){
        if(current.getDepth(rootNode) == 5 || current.getChildren() == null){
            return;
        }
        ArrayList<GameState> possibleMoves = getPossibleStates(current);
        for (GameState g:possibleMoves) {
            current.addChild(new Node<>(g));
        }
        for (Node<GameState> node:current.getChildren()) {
            createTree(node);
        }


    }
    public ArrayList<GameState> getPossibleStates(Node<GameState> current){
        ArrayList<GameState> possibleStates = new ArrayList<>();
        GameState currentState = current.getData();
        Amazon2D[] amazon2DS = currentState.getAmazon2DArrayList();
        Board2D board2D = currentState.getBoard2D();

        //ArrayList<Arrow2D> arrow2DS = currentState.getArrow2DArrayList();
        for (Amazon2D a:amazon2DS) {
            a.possibleMoves(board2D);
            ArrayList<Cell> possibleMoves = a.getPossibleMoves();
            for (Cell c:possibleMoves) {


            }

        }
    }


}
