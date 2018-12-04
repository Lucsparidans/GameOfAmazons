package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;

public class GameState {
    private Board2D board2D;
    private Amazon2D[] amazon2DArrayList;
    private ArrayList<Arrow2D> arrow2DArrayList;
    private Move move;
    private Player player;
    private double heuristicValue;
    private boolean maximizing;


    public GameState(Amazon2D[] amazon2DArrayList, ArrayList<Arrow2D> arrow2DArrayList, Move move, Boolean maximizing, Player player) {
        board2D = new Board2D();
        board2D.placePieces(amazon2DArrayList, arrow2DArrayList);
        this.amazon2DArrayList = amazon2DArrayList;
        this.arrow2DArrayList = arrow2DArrayList;
        this.move = move;
        this.player = player;
this.maximizing = maximizing;
        this.heuristicValue = 0;
    }

    public Board2D getBoard2D() {
        return board2D;
    }

    public Amazon2D[] getAmazon2DArrayList() {
        return amazon2DArrayList;
    }

    public ArrayList<Arrow2D> getArrow2DArrayList() {
        return arrow2DArrayList;
    }

    public boolean isMaximizing() {
        return maximizing;
    }

    public Move getMove() {
        return move;
    }

    public Board2D executeMove() {
        Amazon2D amazon2D = move.getQueen();
        Cell queenTo = move.getQueenTo();
        Cell arrowTo = move.getArrowTo();
        amazon2D.move(queenTo);
        amazon2D.shoot(arrowTo);
        return board2D;
    }

    public double evaluateState(){
        if(this.maximizing){
            return shotsHeuristics(player.getMyAmazons(),player.getEnemyAmazons())
                    + positioHheuristics(player.getMyAmazons(),player.getEnemyAmazons());
        }else{
            return shotsHeuristics(player.getEnemyAmazons(),player.getMyAmazons())
                    + positioHheuristics(player.getEnemyAmazons(),player.getMyAmazons());
        }
    }


    //<editor-fold desc="Heuristics">
    //calculating the score for the node
    private double positioHheuristics(Amazon2D[] ourQueens,Amazon2D[] enemyQueens) {
        double value = 0;

        for (Amazon queen : ourQueens) {

            //score: in range

            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
            //all possible shots questioned
            for (Cell arrowCell : possibleShots) {
                try {
                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement
                            if (i != arrowCell.getI() && j != arrowCell.getJ()) { //avoiding the cell in questioning
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
                int[][] territory = queen.countTerritory(this.board2D.getBoardCoordinates()); //basically how many cells are free to use from that position
                //WARNING: due to no coordinates, can't put the location of the queen in question.
                value = +territoryToInt(territory);
                //Score"territory" ends
            }

        }
        return value;

    }

    private int territoryToInt(int[][] territory) {
        int count = 0;
        for (int i = 0; i < territory.length; i++) {
            for (int j = 0; j < territory[i].length; j++) {
                if (territory[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private double shotsHeuristics(Amazon2D[] ourQueens,Amazon2D[] enemyQueens) {
        double value = 0;
        for (Amazon queen : ourQueens) {

            //score: in range

            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
            //all possible shots questioned
            for (Cell arrowCell : possibleShots) {
                try {
                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement

                            for (Amazon enemyQueen : enemyQueens) {
                                //need list of enemy queens

                                if (i == enemyQueen.getX() && j == enemyQueen.getY()) {
                                    value = +1;
                                }
                            }
                        }

                    }
                } catch (IndexOutOfBoundsException ex) {
                    continue;
                }
            }//Score "in range" ends


        }
        return value;

    }
    //</editor-fold>

}
