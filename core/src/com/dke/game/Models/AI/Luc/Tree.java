package com.dke.game.Models.AI.Luc;

import com.badlogic.gdx.Game;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Cell;
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
   private Player player;





    public Tree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, Player player) {
        if(player.getSide()=='W') {
            initialState = new GameState(amazon2DS, arrow2DS, null, false, player);
        }
        else{
            initialState = new GameState(amazon2DS, arrow2DS, null, false, player);
        }
        this.player = player;
        rootNode = new Node<>(initialState);
        expandNode(rootNode);

    }
    public void expandNode(Node<GameState> current){
        if(current.getDepth(rootNode) == maxDepth || current.getChildren() == null){
            return;
        }
        ArrayList<GameState> possibleMoves = getPossibleStates(current);
        for (GameState g:possibleMoves) {
            Node<GameState> child = new Node<>(g);
//            child.setParent(current);
            current.addChild(child);



        }
        for (Node<GameState> node:current.getChildren()) {
            if((current.getData()).isMaximizing()){
                node.getData().executeMove();
            expandNode(node);
            }
            else{
                node.getData().executeMove();
                expandNode(node);
            }
        }


    }
    public ArrayList<GameState> getPossibleStates(Node<GameState> current){
        ArrayList<GameState> possibleStates = new ArrayList<>();
        GameState currentState = current.getData();
        Amazon2D[] amazon2DS = currentState.getAmazon2DArrayList();
        ArrayList<Arrow2D> arrow2DS = currentState.getArrow2DArrayList();
        Amazon2D[] testQueens = amazon2DS.clone();
        ArrayList<Arrow2D> testArrows = (ArrayList<Arrow2D>) arrow2DS.clone();
        ArrayList<Move> moves = new ArrayList<>();
        Board2D testBoard = new Board2D();
        testBoard.placePieces(testQueens,testArrows);

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
        if((current.getData()).isMaximizing()) {
            for (Move m : moves) {
                possibleStates.add(new GameState(testQueens.clone(), (ArrayList<Arrow2D>) testArrows.clone(), m, false, player));
            }
        }
        else{
            for (Move m : moves) {
                possibleStates.add(new GameState(testQueens.clone(), (ArrayList<Arrow2D>) testArrows.clone(), m, true, player));
            }
        }

        return possibleStates;
    }

    //<editor-fold desc="Getters and setters">
    public Node<GameState> getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node<GameState> rootNode) {
        this.rootNode = rootNode;
    }

    public GameState getInitialState() {
        return initialState;
    }

    public void setInitialState(GameState initialState) {
        this.initialState = initialState;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public Board2D getBestEval() {
        return bestEval;
    }

    public void setBestEval(Board2D bestEval) {
        this.bestEval = bestEval;
    }

    public Board2D getWorstEval() {
        return worstEval;
    }

    public void setWorstEval(Board2D worstEval) {
        this.worstEval = worstEval;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    //</editor-fold>
}



