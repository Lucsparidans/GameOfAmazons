package com.dke.game.Models.AI.Luc.MINMAX;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MoveNode {

    private Move data;
    private MoveNode parent;
    private final int DEPTH;
    private List<MoveNode> children = new ArrayList<>();
    private double value;
    private static Random random = new Random(100);

    public MoveNode(Move data, MoveNode parent) {
        this.data = data;
        this.parent = parent;
        this.DEPTH = this.getDepth();
    }


    public MoveNode addChild(MoveNode child) {
        this.children.add(child);
        child.setParent(this);
        return child;
    }


    public void addChildren(List<MoveNode> children) {
        this.children.addAll(children);
        for (MoveNode child : children) {
            child.setParent(this);
        }
    }


    public List<MoveNode> getChildren() {
        return this.children;
    }


    public Move getData() {
        return this.data;
    }


    public void setData(Move data) {
        this.data = data;
    }

    private void setParent(MoveNode parent) {
        // Finish this method
        this.parent = parent;
    }

    public MoveNode getParent() {
        return this.parent;
    }

    public double getValue() {
        return this.value;
    }

    public int getDEPTH() {
        return this.DEPTH;
    }

    /*
    Calculates the depth of a node by creating a reference to the parent of the current node, this node,
     and then replacing the reference to the current parent to the parents parent.
    This is repeated while that is possible.
     */
    private int getDepth() {

        int counter = 0;
        MoveNode cur;

        if (this.getParent() != null) {
            cur = this.getParent();
            counter++;
            while (cur.getParent() != null) {
                counter++;
                cur = cur.parent;
            }
        }
        return counter;
    }
    /*
    Function which is called to calculate the heuristic value of the node.
     */
    public void evaluateNode(AI player, TestBoard testBoard) {
        this.createCurrentState(this,testBoard);
        //testBoard.printBoard();
        this.value = evaluateState(player, testBoard);
        this.data.setValue(this.value);
        testBoard.resetMoves();
    }

    /*
    sub-memthod to othe  evaluate node method, this returns the value of the heuristic evaluation.
     */
    private double evaluateState(AI playerAI, TestBoard testBoard) {
        if(this.data != null) {
//            if (this.getData().isPlayerMaximizing(playerAI)) {
//                double val = positioHheuristics(testBoard, playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
//                //System.out.println(val);
//                return val;
//            } else {
//                double val = positioHheuristics(testBoard, playerAI.getEnemyAmazons(), playerAI.getMyAmazons());
//                //System.out.println(val);
//                return val;
//            }
            return random.nextDouble();
        }
        return  0;
    }
    /*
    Because the node only contains a move object which describes the action that takes us from the state in the parent-node to the state in this node.
    Because only the state of the rootnode of the movetree is kept, we backtrack from our node to the root while putting the current node we are referencing into
    a stack. Once the rootnode is reached we can recreate the state we are currently in by executing all moves in the stack
     while the stack still contains a move object.

     */
    public void createCurrentState(MoveNode node, TestBoard testBoard) {
//
        
        MoveNode cur = node;
        Stack<MoveNode> path = new Stack<>();
        path.push(cur);
        while (cur.getParent() != null) {
            cur = cur.getParent();
            path.push(cur);
        }
        for (MoveNode n : path) {
            if (n.getData() != null) {
                testBoard.executeMove(n.getData());
                //testBoard.printBoard();
            }
        }
    }
/*
Heuristics
 */
    //<editor-fold desc="Heuristics">
    //calculating the score for the node
    private double positioHheuristics(TestBoard testBoard, Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
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
                Cell ourQueenCell = ourQueen.getCell();
                int i = ourQueenCell.getI();
                int j = ourQueenCell.getJ();
                int[][] territory = ourQueen.countTerritory(testBoard.getBoard()); //basically how many cells are free to use from that position
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

    private double shotsHeuristics(TestBoard testBoard, Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
        double value = 0;
        for (Amazon queen : ourQueens) {

            //score: in range
            queen.possibleMoves(testBoard);
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
