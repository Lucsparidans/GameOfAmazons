package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.MyAlgo.State;
import com.dke.game.Models.AI.Luc.MyAlgo.TestBoard;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;
import java.util.Stack;


public class MovesTree {


    private MoveNode rootNode;
    private State initialState;
    private final int maxDepth = 1;
    private AI player;


    public MovesTree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, AI player) {
        initialState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);
        this.player = player;
        rootNode = new MoveNode(null, null);
        long b = System.nanoTime();
        expandNode(rootNode);
        long e = System.nanoTime();
        System.out.println("The elapsed time is: " + (e - b) * 1e-9);
    }

    public void expandNode(MoveNode current) {
        if (current.getDEPTH() < maxDepth) {
            ArrayList<Move> possibleMoves = getPossibleMoves(current);
            for (Move m : possibleMoves) {
                MoveNode child = new MoveNode(m, current);
                /* child.setParent(current); */
                current.addChild(child);
            }
        }
        if (current.getDEPTH() == maxDepth || current.getChildren().size() == 0) {
            return;
        }
        for (MoveNode node : current.getChildren()) {
            //TODO execute move here
            expandNode(node);
        }


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
        return moves;
    }

    public void createCurrentState(MoveNode current, TestBoard testBoard) {
        MoveNode cur = current;
        Stack<MoveNode> path = new Stack<>();
        path.push(cur);
        while (cur.getParent() != null) {
            cur = cur.getParent();
            path.push(cur);
        }
        for (MoveNode n : path) {
            if (n.getData() != null) {
                testBoard.executeMove(n.getData());
            }
        }
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
                    testBoard.printBoard();
                    a.undoMove();
                    //testBoard.printBoard();
                }
            }
        }
    }

    public MoveNode getRootNode() {
        return rootNode;
    }
}
