package com.dke.game.Models.AI.MINMAX;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.AI.MINMAX.MyAlgo.State;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;
import java.util.Collections;

/*
The tree structure that uses bruteforce to generate all possible moves from a specific root node. The content of the nodes in the tree consist of
move objects.
 */
public class MovesTree {


    private MoveNode rootNode;
    private State initialState;
    static int maxDepth = 1;
    private AI player;
    private int nodeCheck = 0;
    private Move theMove;
    public static int pruneCount = 0;
    private ArrayList<MoveNode> levelOne = new ArrayList<>();
    private ArrayList<Integer> currentsID = new ArrayList<>();
    private boolean createList = false;
    private ArrayList<MoveNode> currents = new ArrayList<>();

    /*
    Constructor
     */
    public MovesTree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, AI player) {
        //SubTree subTree = new SubTree(amazon2DS,arrow2DS,player);
        //currents.add(rootNode);
        initialState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);
        this.player = player;
        rootNode = new MoveNode(null, null);

        double value = 0;

        value = evaluateRoot(rootNode, 0, -1000, 1000);
        System.out.println("final     " + value);

        // Loop for iterative deepening
        /*for(int i = 1; i<=maxMaxDepth; i++) {
            maxDepth = i;
            if(i == 1){
                createList = true;
            }
            value = evaluateRoot(rootNode, 0, -1000, 1000);
            System.out.println("depth");
            rootNode.setNodeID(0);
            Collections.sort(levelOne, new SortByValue());
            for(int j = 0; j<levelOne.size(); j++){
                System.out.println(levelOne.get(j).getValue());
            }*/

        rootNode.setValue(value);
        //System.out.println("Final   " + rootNode.getValue());
    }




    public double evaluateRoot(MoveNode root, int depth, double alpha, double beta){
        ArrayList<Move> possibleMoves = getPossibleMoves(root);
        if(possibleMoves.size() == 0 || root.getDEPTH() == maxDepth){
            //pruneCount++;
            root.evaluateNode(player, this.getInitialState().getTestBoard());
            //System.out.println("     " + root.getValue());
            if(createList) {
                levelOne.add(root);
            }
            currents.add(root);
            System.out.println(root.getValue());
            return root.getValue();
        }
        if(root.getDEPTH()%2 == 0 || root.getDEPTH() == 0){
            if(root.getDEPTH() == 1){
              //  root.setNodeID(root.getNodeID());
            }
            System.out.println("max");
            double bestVal = -1000;
            for (Move m : possibleMoves) {
                MoveNode child = new MoveNode(m, root);
                child.evaluateNode(player, this.getInitialState().getTestBoard());
                currents.add(child);
                Collections.sort(currents, new SortByValue());
                System.out.println(currents.size());
                double value = child.getValue();
                if(currents.indexOf(child) > 0.90*currents.size() || child.getDEPTH() == maxDepth) {
                    value = evaluateRoot(child, depth + 1, alpha, beta);
                }
                else{
                    System.out.println("below");
                }

                    if (value > bestVal && child.getDEPTH() == 1) {
                        theMove = m;

                    }
                    bestVal = Math.max(bestVal, value);
                //}
                alpha = Math.max(alpha, bestVal);
                if(beta <= alpha){
                    System.out.println("prune");
                    break;
                }
            }
            return bestVal;
        }
        else{
            System.out.println("min");
            double bestVal = 1000;
            for (Move m : possibleMoves) {
                MoveNode child = new MoveNode(m, root);
                child.evaluateNode(player, this.getInitialState().getTestBoard());
                currents.add(child);

                Collections.sort(currents, new SortByValue());
                System.out.println(currents.size());
                double value = child.getValue();
                if(currents.indexOf(child) > 0.90*currents.size() || child.getDEPTH() == maxDepth) {
                    value = evaluateRoot(child, depth + 1, alpha, beta);
                }
                else{
                    System.out.println("below");
                }

                    if (value < bestVal) {
                        //                   theMove = m;
                    }
                    bestVal = Math.min(bestVal, value);
                //}
                beta = Math.min(beta, bestVal);
                if(beta <= alpha){
                    System.out.println("prune  ");
                    break;
                }
            }

            return bestVal;
        }
//        double alpha = -1000;
//        double beta = 1000;
//        for (Move m : possibleMoves) {
//            nodes++;
//            double alpha1 = -1000;
//            double beta1 = 1000;
//            MoveNode child = new MoveNode(m, root);
//            ArrayList<Move> possibleMuevos = getPossibleMoves(child);
//            child.setValue(0);
//
//
//
//
//                for(Move n : possibleMuevos) {
//                    double goodValue = 0;
//                    MoveNode child2 = new MoveNode(n, child);
//                    child2.evaluateNode(player, this.getInitialState().getTestBoard());
//                    goodValue = child2.getValue();
//
//                    if (goodValue > child.getValue()) {
//                        child.setValue(goodValue);
//                    }
//                }
//
//            if(child.getValue() > root.getValue()){
//                root.setValue(child.getValue());
//            }
//            System.out.println(nodes + "   " + root.getValue());
//
//        }
    }


    /*
    Generate the children the node "current".
     */
    /*public void expandNode(MoveNode current) {
        if (player.getAlgorithm() instanceof Greedy) {
            maxDepth = 1;
        }
        //for(int i = 1; i<maxDepth+1; i++) {
            if (current.getDEPTH() < maxDepth) {
                ArrayList<Move> possibleMoves = getPossibleMoves(current);
                for (Move m : possibleMoves) {
                    nodeCheck++;
                    //System.out.println("this time");

                    System.out.println(nodeCheck + " Nodes created");
                    MoveNode child = new MoveNode(m, current);
                    // child.setParent(current); //
                    current.addChild(child);
                }
            }
            if (current.getDEPTH() == maxDepth || current.getChildren().size() == 0) {
                return;
            } else {

                    /*for (MoveNode n : current.getChildren()) {
                        //System.out.println("expanded");
                        n.evaluateNode(player, initialState.getTestBoard());
//                        currents.add(n);
//                        if(currents.indexOf(n.getValue()) > currents.size()*(90/100)) {
//                            System.out.println(n.getValue());
//
//                            expandNode(n);
//                        }
//                        for (int i = currents.size()*(90/100); i < currents.size() ; i++) {
//
//                            expandNode(currents.get(i));
//                        }
                    }
                Collections.sort(current.getChildren(), new SortByValue());
                for (MoveNode n :
                        current.getChildren()) {
                    System.out.println(current.getValue());
                }
                for (int i = current.getChildren().size()*(80/100); i < current.getChildren().size() ; i++) {

                            expandNode(current.getChildren().get(i));
                        }*/


//            }
 //       }
        /*for (MoveNode node : current.getChildren()) {
            //TODO execute move here
            expandNode(node);
        }*/


    //}
    /*
    Generate all possible moves in the current situation
     */
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
    /*
    Create the state of the current node, meaning executing the moves in reverse order found by backktracking to the rootnode by using the stack.
     */
    public void createCurrentState(MoveNode current, TestBoard testBoard) {
        current.createCurrentState(current,testBoard);
    }
    /*
    Creates all move objects that are possible in a current state
     */
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

    public Move getTheMove(){
        return this.theMove;
    }

    public MoveNode getRootNode() {
        return rootNode;
    }

    public State getInitialState() {
        return initialState;
    }
    /*public ArrayList<MoveNode> getCurrents(){
        return this.currents;
    }
    public void setCurrents(ArrayList<MoveNode> newCurrents){
        this.currents = newCurrents;*/

    public void setMaxDepth(int newDepth){
        this.maxDepth = newDepth;
    }
}
