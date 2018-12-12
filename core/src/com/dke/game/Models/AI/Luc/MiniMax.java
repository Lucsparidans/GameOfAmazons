package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;

public class MiniMax implements Algorithm {
    private MovesTree movesTree;
    private int depth = 1;


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
                        bestValue = chNodeVal;
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
                    if (bestValue.getValue() > chNodeVal.getValue()) {
                        bestValue = chNodeVal;
                    }
                }
                alpha = Math.min(beta, bestValue.getValue());
                if (beta <= alpha) break;
            }
            return bestValue;
        }
    }
}
