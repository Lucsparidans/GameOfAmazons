package com.dke.game.Models.AI.Luc;



import com.dke.game.Models.DataStructs.Amazon;
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
   private Board2D bestEval;
   private Board2D worstEval;


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
        ArrayList<Arrow2D> arrow2DS = currentState.getArrow2DArrayList();
        Board2D board2D = currentState.getBoard2D();
        Board2D testBoard = new Board2D();
        Amazon2D[] testQueens = amazon2DS.clone();
        ArrayList<Arrow2D> testArrows = (ArrayList<Arrow2D>) arrow2DS.clone();
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

            }

        }
        return null;
    }
    public void positioHheuristics(Amazon2D[] amazon2DS) {
        Amazon[] ourQueens;
        Amazon[] enemyQueens;
        for (Amazon2D a:amazon2DS) {

        }
        for (Amazon queen : ourQueens) {

            //score: in range

            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
            //all possible shots questioned
            for (Cell arrowCell : possibleShots) {
                try {
                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement
                            if(i!=arrowCell.getI() && j!=arrowCell.getJ()){ //avoiding the cell in questioning
                                for (Amazon enemyQueen : enemyQueens) {
                                    //need list of enemy queens

                                    if (i == enemyQueen.getX() && j == enemyQueen.getY()) {
                                        value = +1;
                                    }
                                }
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    continue;
                }
            }//Score "in range" ends
            //
            for (Amazon ourQueen : ourQueens) {
                //score: territory
                Cell ourQueenCell = queen.getCell();
                int i = ourQueenCell.getI();
                int j = ourQueenCell.getJ();
                int[][] territory = queen.countTerritory(boardCoordinates); //basically how many cells are free to use from that position
                //WARNING: due to no coordinates, can't put the location of the queen in question.
                value = + territoryToInt(territory);
                //Score"territory" ends
            }

        }

    }


}
