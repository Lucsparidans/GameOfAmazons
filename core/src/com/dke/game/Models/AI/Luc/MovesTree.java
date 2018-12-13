package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.AI.Luc.MyAlgo.State;
import com.dke.game.Models.AI.Luc.MyAlgo.TestBoard;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;


public class MovesTree {


    private MoveNode rootNode;
    private State initialState;
    private int maxDepth = 2;
    private AI player;
    private int nodeCheck=0;


    public MovesTree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, AI player) {
        initialState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);
        this.player = player;
        rootNode = new MoveNode(null, null);
        expandNode(rootNode);
    }

    public void expandNode(MoveNode current) {
        if(player.getAlgorithm() instanceof Greedy){
            maxDepth = 1;
        }
        if (current.getDEPTH() < maxDepth) {
            ArrayList<Move> possibleMoves = getPossibleMoves(current);
            for (Move m : possibleMoves) {
                nodeCheck++;
                System.out.println(nodeCheck);
                MoveNode child = new MoveNode(m, current);
                // child.setParent(current); //
                current.addChild(child);
            }
        }
        if (current.getDEPTH() == maxDepth || current.getChildren().size() == 0) {
            return;
        }
        /*for (MoveNode node : current.getChildren()) {
            //TODO execute move here
            expandNode(node);
        }*/


    }

    public ArrayList<Move> getPossibleMoves(MoveNode current) {
        ArrayList<Move> moves = new ArrayList<>();
        TestBoard testBoard = initialState.getTestBoard();
        createCurrentState(current, testBoard);
        if (current.getDEPTH() % 2 == 0) {
            generateMoves(testBoard, moves, player.getSide());
        } else {
            generateMoves(testBoard, moves, player.getOpposingSide());
        }
        testBoard.resetMoves();
        //testBoard.printBoard();
        return moves;
    }

    public void createCurrentState(MoveNode current, TestBoard testBoard) {
        current.createCurrentState(current,testBoard);
    }

    private void generateMoves(TestBoard testBoard, ArrayList<Move> moves, char side) {
        for (Amazon2D a : testBoard.getAmazons()) {
            if (a.getSide() == side) {
                a.possibleMoves(testBoard);
                ArrayList<Cell> possibleMoves = a.getPossibleMoves();
                for (Cell c : possibleMoves) {
                    a.move(c);
                    a.possibleMoves(testBoard);
                    ArrayList<Cell> shootMoves = a.getPossibleMoves();
                    for (Cell cs : shootMoves) {
                        moves.add(new Move(a, c, cs));
                        //testBoard.printBoard();
                    }
                    //testBoard.printBoard();
                    a.undoMove();
                    //testBoard.printBoard();
                }
            }
        }
    }

    public MoveNode getRootNode() {
        return rootNode;
    }

    public State getInitialState() {
        return initialState;
    }
}
