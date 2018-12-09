package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.MyAlgo.State;
import com.dke.game.Models.AI.Luc.MyAlgo.TestBoard;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;
import java.util.Stack;


public class MovesTree {


    private Node<Move> rootNode;
    private State initialState;
    private final int maxDepth = 1;
    private Player player;


    public MovesTree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, Player player) {
        initialState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);
        this.player = player;
        rootNode = new Node<>(null, null);
        long b = System.nanoTime();
        expandNode(rootNode);
        long e = System.nanoTime();
        System.out.println("The elapsed time is: " + (e-b)*1e-9);
    }

    public void expandNode(Node<Move> current) {
        if (current.getDEPTH() < maxDepth) {
            ArrayList<Move> possibleMoves = getPossibleMoves(current);
            for (Move m : possibleMoves) {
                Node<Move> child = new Node<>(m, current);
                /* child.setParent(current); */
                current.addChild(child);
            }
        }
        if (current.getDEPTH() == maxDepth || current.getChildren().size() == 0) {
            return;
        }
        for (Node<Move> node : current.getChildren()) {
            //TODO execute move here
            expandNode(node);
        }


    }

    public ArrayList<Move> getPossibleMoves(Node<Move> current) {
        Stack<Node> path = new Stack<>();
        ArrayList<Move> moves = new ArrayList<>();
        TestBoard testBoard = initialState.getTestBoard();
        Node<Move> cur = current;
        path.push(cur);
        while (cur.getParent() != null) {
            cur = cur.getParent();
            path.push(cur);
        }
        for (Node<Move> n : path) {
            testBoard.executeMove(n.getData());
        }
        generateMoves(testBoard,moves);
        testBoard.resetMoves();
        return moves;
    }

    private void generateMoves(TestBoard testBoard, ArrayList<Move> moves) {
        for (Amazon2D a : testBoard.getAmazons()) {
            a.possibleMoves(testBoard);
            ArrayList<Cell> possibleMoves = a.getPossibleMoves();
            for (Cell c : possibleMoves) {
                a.move(c);
                a.possibleMoves(testBoard);
                ArrayList<Cell> shootMoves = a.getPossibleMoves();
                for (Cell cs : shootMoves) {
                    moves.add(new Move(a, c, cs));

                }
                a.undoMove();
            }
        }
    }

    public Node<Move> getRootNode() {
        return rootNode;
    }
}
