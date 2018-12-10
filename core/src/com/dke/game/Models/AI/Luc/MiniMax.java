package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;

public class MiniMax implements Algorithm {
    private MovesTree movesTree;

    @Override
    public Move getBestMove(Player player, MoveNode root) {
        MoveNode bestEval = MiniMax(movesTree.getRootNode(), 2, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        while (bestEval.getParent() != null) {
            bestEval = bestEval.getParent();
        }
        return bestEval.getData();
    }

    public MoveNode node;
    boolean maxPlayer = true;

    //returns the best score of all possible Board Status
    public static MoveNode MiniMax(MoveNode aNode, int depth, boolean maxPlayer, double alpha, double beta) {

        MoveNode chNodeVal;
        if (depth == 0 || aNode.getChildren().isEmpty()) {
            return aNode;
        }
        //no
        if (maxPlayer) {
            MoveNode bestValue = null;


            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, false, alpha, beta);
//                bestValue =Math.max( bestValue,chNodeVal);
                if (bestValue == null) {
                    bestValue = aNode;
                } else {
                    if (bestValue.getValue() < aNode.getValue()) {
                        bestValue = aNode;
                    }
                }
                alpha = Math.max(alpha, bestValue.getValue());
                if (beta <= alpha) break;

            }
            return bestValue;
        } else {
            MoveNode bestValue = null;
            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, true, alpha, beta);
//                bestValue =Math.min( bestValue, chNodeVal);
//                beta= Math.min(beta,bestValue);
                if (bestValue == null) {
                    bestValue = aNode;
                } else {
                    if (bestValue.getValue() > aNode.getValue()) {
                        bestValue = aNode;
                    }
                }
                alpha = Math.min(beta, bestValue.getValue());
                if (beta <= alpha) break;


            }
            return bestValue;

        }

    }
}
