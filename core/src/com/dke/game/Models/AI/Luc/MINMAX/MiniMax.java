package com.dke.game.Models.AI.Luc.MINMAX;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.MINMAX.MoveNode;
import com.dke.game.Models.AI.Luc.MINMAX.MovesTree;
import com.dke.game.Models.AI.Luc.Move;

import java.util.ArrayList;

public class MiniMax implements Algorithm {
    private MovesTree movesTree;
    private int depth = 2;
    private int cutOff = 1000;
    private ArrayList<MoveNode> highestEvalFreq = new ArrayList<>();
    private ArrayList<MoveNode> lowestEvalFreq = new ArrayList<>();
    private int nodesEvaled = 0;

    /*
    Returns the move that was found to be the 'best' move using the minimax algorithm to search the tree.
     */
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
    /*
    Because the Heuristic evaluations are very similiar for many nodes, when we reach a the value of cutoff many the same evaluated nodes,
     we stop searching and choose on of the best found so far.
     */
    public MoveNode MiniMax(MoveNode aNode, int searchDepth, boolean maxPlayer, double alpha, double beta, AI player) {

        MoveNode chNodeVal;
        if (searchDepth == 0 || aNode.getChildren().isEmpty()) {

            aNode.evaluateNode(player,movesTree.getInitialState().getTestBoard());
            System.out.println("the number of nodes evaluated is: " + nodesEvaled++);
            return aNode;
        }
        //no
        if (maxPlayer) {
            MoveNode bestValue = null;


            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), searchDepth - 1, false, alpha, beta, player);

//                bestValue =Math.max( bestValue,chNodeVal);
                if (bestValue == null) {
                    bestValue = chNodeVal;
                } else {
                    if (bestValue.getValue() < chNodeVal.getValue()) {
                        highestEvalFreq = new ArrayList<>();
                        bestValue = chNodeVal;
                        highestEvalFreq.add(chNodeVal);
                    }
                    else if(bestValue.getValue() == chNodeVal.getValue()){
                        highestEvalFreq.add(chNodeVal);
                        if(highestEvalFreq.size()==cutOff){
                            double rnd = Math.random()*highestEvalFreq.size();
                            return highestEvalFreq.get((int)rnd);
                        }
                    }

                }
                alpha = Math.max(alpha, bestValue.getValue());
                if (beta <= alpha) break;
            }
            double rnd = Math.random()*highestEvalFreq.size();
            return highestEvalFreq.get(((int)rnd));
        } else {
            MoveNode bestValue = null;
            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), searchDepth - 1, true, alpha, beta, player);
//                bestValue =Math.min( bestValue, chNodeVal);
//                beta= Math.min(beta,bestValue);
                if (bestValue == null) {
                    bestValue = chNodeVal;
                } else {
                    if (bestValue.getValue() < chNodeVal.getValue()) {
                        lowestEvalFreq = new ArrayList<>();
                        bestValue = chNodeVal;
                        lowestEvalFreq.add(chNodeVal);
                    }
                    else if(bestValue.getValue() == chNodeVal.getValue()){
                        lowestEvalFreq.add(chNodeVal);
                        if(lowestEvalFreq.size()==cutOff){
                            double rnd = Math.random()*lowestEvalFreq.size();
                            return lowestEvalFreq.get((int)rnd);
                        }
                    }
                }
                alpha = Math.min(beta, bestValue.getValue());
                if (beta <= alpha) break;
            }
            double rnd = Math.random()*lowestEvalFreq.size();
            return lowestEvalFreq.get(((int)rnd));
        }
    }
}
