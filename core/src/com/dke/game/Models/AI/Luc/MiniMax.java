package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;

import java.util.ArrayList;

public class MiniMax implements Algorithm {
    private MovesTree movesTree;
    private int depth = 1;
    private double secondHighest = Double.NEGATIVE_INFINITY;
    private double secondLowest = Double.POSITIVE_INFINITY;
    private int cutOff = 20;
    private ArrayList<MoveNode> highestEvalFreq = new ArrayList<>();
    private ArrayList<MoveNode> secondHighestEvalFreq = new ArrayList<>();
    private ArrayList<MoveNode> lowestEvalFreq = new ArrayList<>();
    private ArrayList<MoveNode> secondLowestEvalFreq = new ArrayList<>();


    @Override
    public Move getBestMove(AI player, MoveNode root) {
        this.movesTree = player.getTree();
        MoveNode bestEval = MiniMax(movesTree.getRootNode(), depth, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, player);
        while (bestEval.getParent().getParent() != null) {
            bestEval = bestEval.getParent();
        }
        return bestEval.getData();

    }

    //returns the best score of all possible Board Status
    //The cutoff rates reduce runtime, and since we get a lot of similar evals we cannot find global optimum anyways.
    public MoveNode MiniMax(MoveNode aNode, int depth, boolean maxPlayer, double alpha, double beta, AI player) {

        MoveNode chNodeVal;
        if (depth == 0 || aNode.getChildren().isEmpty()) {

            aNode.evaluateNode(player,movesTree.getInitialState().getTestBoard());
            return aNode;
        }
        //no
        if (maxPlayer) {
            MoveNode bestValue = null;


            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, false, alpha, beta, player);

//                bestValue =Math.max( bestValue,chNodeVal);
                if (bestValue == null) {
                    bestValue = chNodeVal;
                } else {
                    if (bestValue.getValue() < chNodeVal.getValue()) {
                        highestEvalFreq = new ArrayList<>();
                        secondHighestEvalFreq = new ArrayList<>();
                        bestValue = chNodeVal;
                        highestEvalFreq.add(chNodeVal);
                    }
                    else if(bestValue.getValue() == chNodeVal.getValue()){
                        highestEvalFreq.add(chNodeVal);
                        if(highestEvalFreq.size()==cutOff){
                            double rnd = Math.random()*cutOff;
                            return highestEvalFreq.get((int)rnd);
                        }
                    }
                    else{
                        if(secondHighest < chNodeVal.getValue()){
                            secondHighest = chNodeVal.getValue();
                        }
                        if(secondHighestEvalFreq.size()==cutOff){
                            double rnd = Math.random()*cutOff;
                            return highestEvalFreq.get((int)rnd);
                        }
                    }
                }
                alpha = Math.max(alpha, bestValue.getValue());
                if (beta <= alpha) break;
            }
            return bestValue;
        } else {
            MoveNode bestValue = null;
            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, true, alpha, beta, player);
//                bestValue =Math.min( bestValue, chNodeVal);
//                beta= Math.min(beta,bestValue);
                if (bestValue == null) {
                    bestValue = chNodeVal;
                } else {
                    if (bestValue.getValue() < chNodeVal.getValue()) {
                        lowestEvalFreq = new ArrayList<>();
                        secondLowestEvalFreq = new ArrayList<>();
                        bestValue = chNodeVal;
                        lowestEvalFreq.add(chNodeVal);
                    }
                    else if(bestValue.getValue() == chNodeVal.getValue()){
                        lowestEvalFreq.add(chNodeVal);
                        if(lowestEvalFreq.size()==cutOff){
                            double rnd = Math.random()*cutOff;
                            return lowestEvalFreq.get((int)rnd);
                        }
                    }
                    else{
                        if(chNodeVal.getValue()<secondLowest){
                            secondLowest = chNodeVal.getValue();
                        }
                        if(secondLowestEvalFreq.size()==cutOff){
                            double rnd = Math.random()*cutOff;
                            return lowestEvalFreq.get((int)rnd);
                        }
                    }
                }
                alpha = Math.min(beta, bestValue.getValue());
                if (beta <= alpha) break;
            }
            return bestValue;
        }
    }
}
