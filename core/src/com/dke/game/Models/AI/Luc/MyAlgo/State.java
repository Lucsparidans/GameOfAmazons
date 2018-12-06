package com.dke.game.Models.AI.Luc.MyAlgo;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;

import java.util.ArrayList;

public class State {
    private TestBoard testBoard;
    private Move move;
    private Player player;

    public State(TestBoard testBoard, Move move, Player player) {
        this.player = player;
        this.testBoard = testBoard;
        this.move = move;
    }

    public TestBoard getTestBoard() {
        return testBoard;
    }

    public Move getMove() {
        return move;
    }

    public Player getPlayer() {
        return player;
    }
    public TestBoard executeMove() {
        Amazon2D amazon2D = move.getQueen();
        Cell queenTo = move.getQueenTo();
        Cell arrowTo = move.getArrowTo();
        amazon2D.move(queenTo);
        amazon2D.shoot(arrowTo);
        return testBoard;
    }

    public double evaluateState() {
        if (player instanceof AI) {
            AI playerAI = (AI) player;
            if (move.isPlayerMaximizing(playerAI)) {
                return shotsHeuristics(playerAI.getMyAmazons(), playerAI.getEnemyAmazons())
                        + positioHheuristics(playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
            } else {
                return shotsHeuristics(playerAI.getEnemyAmazons(), playerAI.getMyAmazons())
                        + positioHheuristics(playerAI.getEnemyAmazons(), playerAI.getMyAmazons());
            }
        }
        return 0;
    }


    //<editor-fold desc="Heuristics">
    //calculating the score for the node
    private double positioHheuristics(Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
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
                int[][] territory = queen.countTerritory(this.testBoard.getBoard()); //basically how many cells are free to use from that position
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

    private double shotsHeuristics(Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
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
