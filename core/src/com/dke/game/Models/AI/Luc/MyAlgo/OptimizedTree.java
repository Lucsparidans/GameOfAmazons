//package com.dke.game.Models.AI.Luc.MyAlgo;
//
//import com.dke.game.Controller.Player.Player;
//import com.dke.game.Models.AI.Luc.Move;
//import com.dke.game.Models.AI.Luc.MyAlgo.Node;
//import com.dke.game.Models.DataStructs.Cell;
//import com.dke.game.Models.GraphicalModels.Amazon2D;
//import com.dke.game.Models.GraphicalModels.Arrow2D;
//import java.util.ArrayList;
//
//public class OptimizedTree {
//
//    private Node<State> rootNode;
//    private State initialState;
//    private final int maxDepth = 1;
//    private Player player;
//
//
//    public OptimizedTree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, Player player) {
//        initialState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);
//
//        this.player = player;
//        rootNode = new Node<>(initialState,null);
//        expandNode(rootNode);
//
//    }
//
//    public void expandNode(Node<State> current) {
//        if(current.getDEPTH()<maxDepth){
//        ArrayList<State> possibleMoves = getPossibleStates(current);
//        for (State g : possibleMoves) {
//            Node<State> child = new Node<>(g, current);
////            child.setParent(current);
//            current.addChild(child);
//        }
//        }
//        if (current.getDEPTH() == maxDepth || current.getChildren().size()==0) {
//            return;
//        }
//        for (Node<State> node : current.getChildren()) {
//            node.getData().executeMove();
//            expandNode(node);
//        }
//
//
//    }
//
//    public ArrayList<State> getPossibleStates(Node<State> current) {
//        ArrayList<State> possibleStates = new ArrayList<>();
//        State currentState = current.getData();
//        TestBoard testBoard = currentState.getTestBoard();
//        Amazon2D[] amazons = testBoard.getAmazons();
//        ArrayList<Arrow2D> arrows = testBoard.getArrows();
//        ArrayList<Move> moves = new ArrayList<>();
//
//
//        for (Amazon2D a : amazons) {
//            a.possibleMoves(testBoard);
//            ArrayList<Cell> possibleMoves = a.getPossibleMoves();
//            for (Cell c : possibleMoves) {
//                a.move(c);
//                a.possibleMoves(testBoard);
//                ArrayList<Cell> shootMoves = a.getPossibleMoves();
//                for (Cell cs : shootMoves) {
//                    moves.add(new Move(a, c, cs));
//
//                }
//                a.undoMove();
//            }
//        }
//
//        for (Move m : moves) {
//            possibleStates.add(new State(new TestBoard(amazons.clone(), (ArrayList<Arrow2D>) arrows.clone()), m, player));
//
//        }
//
//        return possibleStates;
//    }
//
//    public Node<State> getRootNode() {
//        return rootNode;
//    }
//}
